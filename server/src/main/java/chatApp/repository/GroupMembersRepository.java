package chatApp.repository;

import chatApp.Entities.CompositeKeyGroupMembers;
import chatApp.Entities.GroupMembers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMembersRepository extends JpaRepository<GroupMembers, CompositeKeyGroupMembers> {

    List<GroupMembers> findByGroupId(int groupId);
}
