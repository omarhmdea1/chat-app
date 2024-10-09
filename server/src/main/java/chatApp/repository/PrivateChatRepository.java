package chatApp.repository;

import chatApp.Entities.CompositeKeyPrivateChat;
import chatApp.Entities.PrivateChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrivateChatRepository extends JpaRepository<PrivateChat, CompositeKeyPrivateChat> {

    List<PrivateChat> findBySenderUserAndReceiverUser(int senderUser, int receiverUser);

    @Query(value = "SELECT distinct p.sender_user  FROM chatapp.private_chat p where p.sender_user != :id And p.receiver_user= :id" +
            " UNION " +
            "SELECT distinct p.receiver_user  FROM chatapp.private_chat p where p.receiver_user != :id And p.sender_user= :id",
            nativeQuery = true
    )
    List<Integer> findPrivateChats(@Param("id") int id);
}
//
//    SELECT m.content, m.date_time FROM chatapp.message as m ,chatapp.private_chat
//        WHERE chatapp.private_chat.sender_user = 112
//        AND chatapp.private_chat.receiver_user = 120
//        OR chatapp.private_chat.sender_user = 120
//        AND chatapp.private_chat.receiver_user = 112
//        ORDER BY m.date_time ASC;
