package repository;

import entity.Group;
import util.JsonFileUtil;

import java.util.ArrayList;
import java.util.List;

public class GroupRepository {

    private static final String GROUP_FILE = "group_info.json";

    public int insertGroup(Group group) {
        List<Group> groupList = JsonFileUtil.readList(GROUP_FILE, Group.class);

        groupList.add(group);

        JsonFileUtil.writeList(GROUP_FILE, groupList);

        return 1;
    }

    public List<Group> findAllGroups() {
        return JsonFileUtil.readList(GROUP_FILE, Group.class);
    }

    public Group findGroupById(String groupId) {
        List<Group> groupList = JsonFileUtil.readList(GROUP_FILE, Group.class);

        for (Group group : groupList) {
            if (group.getGroupId().equals(groupId)) {
                return group;
            }
        }

        return null;
    }

    public List<Group> findGroupsByCondition(String condition) {
        List<Group> groupList = JsonFileUtil.readList(GROUP_FILE, Group.class);
        List<Group> resultList = new ArrayList<>();

        if (condition == null || condition.trim().isEmpty()) {
            return groupList;
        }

        String keyword = condition.trim().toLowerCase();

        for (Group group : groupList) {
            String groupId = group.getGroupId() == null ? "" : group.getGroupId().toLowerCase();
            String groupName = group.getGroupName() == null ? "" : group.getGroupName().toLowerCase();
            String managerId = group.getManagerId() == null ? "" : group.getManagerId().toLowerCase();

            if (groupId.contains(keyword)
                    || groupName.contains(keyword)
                    || managerId.contains(keyword)) {
                resultList.add(group);
            }
        }

        return resultList;
    }

    public boolean existsGroupById(String groupId) {
        return findGroupById(groupId) != null;
    }
}
