package com.ahnora.identity.application.service;

import com.ahnora.common.core.context.TenantContextHolder;
import com.ahnora.common.core.exception.ConflictException;
import com.ahnora.common.core.exception.EntityNotFoundException;
import com.ahnora.common.core.exception.ErrorCodes;
import com.ahnora.common.core.exception.UnauthorizedException;
import com.ahnora.common.core.security.SecurityConstants;
import com.ahnora.identity.application.dto.auth.AuthResponse;
import com.ahnora.identity.application.dto.auth.LoginRequest;
import com.ahnora.identity.application.dto.auth.RegisterRequest;
import com.ahnora.identity.application.dto.auth.SwitchTenantRequest;
import com.ahnora.identity.domain.model.*;
import com.ahnora.identity.infrastructure.persistence.entity.MembershipEntity;
import com.ahnora.identity.infrastructure.persistence.entity.OrganizationEntity;
import com.ahnora.identity.infrastructure.persistence.entity.UserEntity;
import com.ahnora.identity.infrastructure.persistence.repository.MembershipRepository;
import com.ahnora.identity.infrastructure.persistence.repository.OrganizationRepository;
import com.ahnora.identity.infrastructure.persistence.repository.UserRepository;
import com.ahnora.identity.infrastructure.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final MembershipRepository membershipRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository,
                       OrganizationRepository organizationRepository,
                       MembershipRepository membershipRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.membershipRepository = membershipRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ConflictException(String.format("User with email %s already exists.", request.email()));
        }

        String slug;
        if (request.organizationSlug() != null && !request.organizationSlug().isBlank()) {
            slug = Organization.slugify(request.organizationSlug());
            if (organizationRepository.existsBySlug(slug)) {
                throw new ConflictException(String.format("Organization with slug %s already exists.", slug));
            }
        } else {
            String baseSlug = Organization.slugify(request.organizationName());
            slug = baseSlug;
            if (organizationRepository.existsBySlug(slug)) {
                slug = String.format("%s-%s", baseSlug, UUID.randomUUID().toString().substring(0, 6));
            }
        }

        Organization organization = Organization.create(request.organizationName(), slug, "system");
        User user = User.register(request.email(), passwordEncoder.encode(request.password()), request.email());
        Membership membership = Membership.create(organization.getId(), user.getId(), Set.of(Role.ORG_ADMIN), "system");

        organizationRepository.save(OrganizationEntity.fromDomain(organization));
        userRepository.save(UserEntity.fromDomain(user));
        membershipRepository.save(MembershipEntity.fromDomain(membership));

        Set<String> roles = Set.of(Role.ORG_ADMIN.name());
        String accessToken = jwtTokenProvider.generateToken(
                user.getId(),
                user.getEmail(),
                membership.getTenantId(),
                roles,
                Role.ORG_ADMIN.getPermissions().stream().map(Permission::getValue).collect(Collectors.toSet()));

        return new AuthResponse(accessToken, user.getId(), membership.getTenantId().value(), user.getEmail(), roles);
    }

    public AuthResponse login(LoginRequest request) {
        User user = (userRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new UnauthorizedException(ErrorCodes.UNAUTHORIZED, "Invalid credentials")))
                .toDomain();

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new UnauthorizedException(ErrorCodes.UNAUTHORIZED, "Invalid credentials");
        }

        String accessToken = jwtTokenProvider.generateToken(
                user.getId(),
                user.getEmail(),
                null,
                SecurityConstants.UNSCOPED_USER_ROLES,
                SecurityConstants.UNSCOPED_USER_PERMISSIONS
        );

        return new AuthResponse(
                accessToken,
                user.getId(),
                null,
                user.getEmail(),
                SecurityConstants.UNSCOPED_USER_ROLES
        );
    }

    // TODO(TASK-003): implement tenant switching. The lookup below is the only implemented
    // part; validate target membership + issue a scoped JWT here (mirror login()).
    public AuthResponse switchTenant(SwitchTenantRequest request) {
        MembershipEntity membershipEntity = membershipRepository.findByOrganizationIdAndUserId(
                request.targetTenantId(), UUID.fromString(TenantContextHolder.getRequiredContext().userId())
        ).orElseThrow(() -> new EntityNotFoundException("Membership was not found for the target tenant."));

        throw new UnsupportedOperationException("Tenant switching is not implemented yet (TASK-003).");
    }
}
