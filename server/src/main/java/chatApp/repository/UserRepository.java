package chatApp.repository;

import chatApp.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository

public interface UserRepository extends JpaRepository<User, Integer> {
        Optional<User> findByEmail(String email);
        Optional<User> getUserById(int id);
        List<User> findByFirstNameStartingWith(String firstName);
        List<User> findByLastNameStartingWith(String lastName);
        Optional<User> findByNikeName(String nikeName);

}
