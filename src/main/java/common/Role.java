package common;

public enum Role {

    SYSTEM_ADMIN("시스템관리자"),
    GROUP_ADMIN("그룹관리자"),
    LAB_MANAGER("연구실책임자"),
    LAB_SAFETY_MANAGER("연구실안전관리담당자"),
    RESEARCH_WORKER("연구활동종사자"),
    SAFETY_TEAM_MANAGER("안전관리팀 담당자"),
    EXTERNAL_SYSTEM("외부연계시스템");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Role fromName(String roleName) {
        if (roleName == null) {
            return null;
        }

        for (Role role : Role.values()) {
            if (role.name().equals(roleName)) {
                return role;
            }
        }

        return null;
    }
}