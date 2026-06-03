package util;

import common.Role;
import entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AuthUtil {

    public static User getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        return (User) session.getAttribute("loginUser");
    }

    public static Role getLoginRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        String roleName = (String) session.getAttribute("userRole");

        return Role.fromName(roleName);
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        return getLoginUser(request) != null;
    }

    public static boolean hasRole(HttpServletRequest request, Role... allowedRoles) {
        Role loginRole = getLoginRole(request);

        if (loginRole == null) {
            return false;
        }

        for (Role role : allowedRoles) {
            if (loginRole == role) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasRole(String userRole, Role... allowedRoles) {
        Role loginRole = Role.fromName(userRole);

        if (loginRole == null) {
            return false;
        }

        for (Role role : allowedRoles) {
            if (loginRole == role) {
                return true;
            }
        }

        return false;
    }
}