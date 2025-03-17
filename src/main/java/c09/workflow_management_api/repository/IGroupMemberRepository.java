package c09.workflow_management_api.repository;

import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.model.GroupMember;
import c09.workflow_management_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IGroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findById_Group_Id(Long groupId);
    List<GroupMember> findById_User_Id(Long userId);
    boolean existsById_GroupAndId_User(Group group, User user);
    Optional<GroupMember> findById_GroupAndId_User(Group group, User user);
}

