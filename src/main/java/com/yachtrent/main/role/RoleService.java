package com.yachtrent.main.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.yachtrent.main.role.Authority.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Set<Role> getChooseRole(String role) {
        switch (role.toUpperCase()) {
            case "USER" -> {
                return getUserRights();
            }
            case "YACHT OWNER" -> {
                return getYachtOwnerRights();
            }
            case "MANAGER" -> {
                return getManagerRights();
            }
            case "ADMIN" -> {
                return getAdminRights();
            }
            case "MODERATOR" -> {
                return getModeratorRights();
            }
            default -> {
                return Set.of(getRole(ANONYMOUS.name()));
            }
        }
    }

    private Set<Role> getUserRights() {
        return Set.of(getRole(USER.name()));
    }

    private Set<Role> getYachtOwnerRights() {
        return Set.of(getRole(USER.name()), getRole(YACHT_OWNER.name()));
    }

    private Set<Role> getManagerRights() {
        return Set.of(getRole(USER.name()), getRole(MANAGER.name()));
    }

    private Set<Role> getAdminRights() {
        return Set.of(getRole(USER.name()), getRole(MANAGER.name()), getRole(ADMIN.name()));
    }

    private Set<Role> getModeratorRights() {
        return Set.of(getRole(USER.name()), getRole(MANAGER.name()), getRole(ADMIN.name()), getRole(MODERATOR.name()));
    }

    private Role getRole(String role) {
        return roleRepository.findByRole(role).orElse(null);
    }
}
