package common;

public class AccessPolicy {

    private AccessPolicy() {
    }

    public static boolean canAccessUserManagement(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.GROUP_ADMIN;
    }

    public static boolean canRegisterGroup(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.GROUP_ADMIN;
    }

    public static boolean canViewGroup(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.GROUP_ADMIN
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canAccessLabManagement(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.LAB_MANAGER
                || role == Role.LAB_SAFETY_MANAGER
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canRegisterLab(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.LAB_SAFETY_MANAGER
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canViewLab(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.LAB_MANAGER
                || role == Role.LAB_SAFETY_MANAGER
                || role == Role.RESEARCH_WORKER
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canAccessChemicalManagement(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.LAB_MANAGER
                || role == Role.LAB_SAFETY_MANAGER
                || role == Role.RESEARCH_WORKER
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canRegisterChemical(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.LAB_SAFETY_MANAGER
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canViewChemical(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.LAB_MANAGER
                || role == Role.LAB_SAFETY_MANAGER
                || role == Role.RESEARCH_WORKER
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canAccessWasteManagement(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.LAB_MANAGER
                || role == Role.LAB_SAFETY_MANAGER
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canRegisterWaste(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.LAB_MANAGER
                || role == Role.LAB_SAFETY_MANAGER
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canViewWaste(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.LAB_MANAGER
                || role == Role.LAB_SAFETY_MANAGER
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canAccessInspectionManagement(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.LAB_SAFETY_MANAGER
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canRegisterInspection(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canViewInspection(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.LAB_MANAGER
                || role == Role.LAB_SAFETY_MANAGER
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canAccessEducationManagement(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.LAB_MANAGER
                || role == Role.LAB_SAFETY_MANAGER
                || role == Role.RESEARCH_WORKER
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canRegisterEducation(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.LAB_SAFETY_MANAGER
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canViewEducation(Role role) {
        return role == Role.SYSTEM_ADMIN
                || role == Role.LAB_MANAGER
                || role == Role.LAB_SAFETY_MANAGER
                || role == Role.RESEARCH_WORKER
                || role == Role.SAFETY_TEAM_MANAGER;
    }

    public static boolean canUseExternalIntegration(Role role) {
        return role == Role.EXTERNAL_SYSTEM
                || role == Role.SYSTEM_ADMIN
                || role == Role.SAFETY_TEAM_MANAGER;
    }
}