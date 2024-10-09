package chatApp.repository;

import chatApp.Entities.PublicGroups;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<PublicGroups, Integer> {

    Optional<PublicGroups> findByGroupName(String groupName);
}

