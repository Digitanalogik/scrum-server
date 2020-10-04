package in.viest.scrumserver.repository;

import in.viest.scrumserver.model.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends CrudRepository<Vote, String> {
    Optional<Vote> findById(String id);
    Optional<Vote> findByRoom(String room);
    Optional<Vote> findByRoomAndId(String room, String id);
}
