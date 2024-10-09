package chatApp.repository;

import chatApp.Entities.CompositeKeyGroupChats;
import chatApp.Entities.GroupChats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GroupChatsRepository extends JpaRepository<GroupChats, CompositeKeyGroupChats> {

    GroupChats findByGroupId(int groupId);
    List<GroupChats> findBySenderUserAndGroupId(int senderUser, int groupId);
}

