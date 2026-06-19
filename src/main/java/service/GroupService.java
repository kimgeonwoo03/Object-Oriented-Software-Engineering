package service;

import entity.Group;
import exception.DuplicateDataException;
import exception.EmptyFieldException;
import repository.GroupRepository;
import util.ValidationUtil;

import java.util.List;

public class GroupService {

    private GroupRepository groupRepository = new GroupRepository();

    public boolean createGroup(Group group) {
        validateGroup(group);
        checkDuplicateGroup(group.getGroupId());

        int result = groupRepository.insertGroup(group);

        return result > 0;
    }

    public List<Group> readGroupList(String condition) {
        return groupRepository.findGroupsByCondition(condition);
    }

    public void validateGroup(Group group) {
        if (group == null) {
            throw new EmptyFieldException("그룹 정보가 없습니다.");
        }

        ValidationUtil.require(group.getGroupId(), "그룹 ID");
        ValidationUtil.require(group.getGroupName(), "그룹명");
        ValidationUtil.require(group.getManagerId(), "관리자 ID");
    }

    public void checkDuplicateGroup(String groupId) {
        if (groupRepository.existsGroupById(groupId)) {
            throw new DuplicateDataException("이미 존재하는 그룹 ID입니다.");
        }
    }
}
