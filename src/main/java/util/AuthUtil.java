package util;

import common.Role;

public class AuthUtil {

    public static boolean hasRole(String userRole, Role... allowedRoles) {
        if (userRole == null) {
            return false;
        }

        for (Role role : allowedRoles) {
            if (userRole.equals(role.name())) {
                return true;
            }
        }

        return false;
    }
}