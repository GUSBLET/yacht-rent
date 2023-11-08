package com.yachtrent.main.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.yachtrent.main.role.Authority.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Set<Role> getAnonymousRights() {
        if (roleExists(ANONYMOUS.name())) {
            Set<Role> anonymous = new HashSet<>();
            anonymous.add(getRole(ANONYMOUS.name()));
            return anonymous;
        }
        return null;
    }

    public Set<Role> getUserRights() {
        if (roleExists(USER.name())) {
            Set<Role> userRights = new HashSet<>();
            userRights.add(getRole(USER.name()));
            return userRights;
        }
        return null;
    }

    public Set<Role> getAdminRights() {
        if (roleExists(ADMIN.name())) {
            Set<Role> adminRights = new HashSet<>();
            adminRights.add(getRole(USER.name()));
            adminRights.add(getRole(ADMIN.name()));
            return adminRights;
        }
        return null;
    }

    public Set<Role> getYachtOwnerRights() {
        if (roleExists(YACHT_OWNER.name())) {
            Set<Role> yachtOwnerRights = new HashSet<>();
            yachtOwnerRights.add(getRole(USER.name()));
            yachtOwnerRights.add(getRole(YACHT_OWNER.name()));
            return yachtOwnerRights;
        }
        return null;
    }

    public Set<Role> getModeratorRights() {
        if (roleExists(MODERATOR.name())) {
            Set<Role> moderatorRights = new HashSet<>();
            moderatorRights.add(getRole(USER.name()));
            moderatorRights.add(getRole(ADMIN.name()));
            moderatorRights.add(getRole(MODERATOR.name()));
            return moderatorRights;
        }
        return null;
    }

    private boolean roleExists(String role) {
        return roleRepository.findByRole(role).isPresent();
    }

    private Role getRole(String role) {
        return roleRepository.findByRole(role)
                .orElseThrow(() -> new IllegalArgumentException("no such role exists"));
    }
}
