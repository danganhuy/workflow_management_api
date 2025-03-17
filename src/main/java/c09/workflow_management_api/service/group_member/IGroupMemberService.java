package c09.workflow_management_api.service.group_member;

import c09.workflow_management_api.model.GroupMember;
import c09.workflow_management_api.service.IGenericService;

import java.util.List;

public interface IGroupMemberService extends IGenericService<GroupMember> {
    List<GroupMember> findAllByGroupId(Long groupId);
}
