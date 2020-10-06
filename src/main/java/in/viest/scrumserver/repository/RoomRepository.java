package in.viest.scrumserver.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import in.viest.scrumserver.model.Room;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
    Optional<Room> findById(Integer id);
    Optional<Room> findByName(String room);
}
