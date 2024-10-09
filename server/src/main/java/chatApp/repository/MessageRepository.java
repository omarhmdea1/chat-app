package chatApp.repository;

import chatApp.Entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    Message findById(int id);
    Optional<LocalDateTime> getDateById(int id);

}




