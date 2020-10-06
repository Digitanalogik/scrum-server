package in.viest.scrumserver.repository;

import in.viest.scrumserver.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    List<Player> findAll();
    Optional<Player> findById(Integer id);
    Optional<Player> findByName(String name);
    Optional<Player> findByRoom(Integer room);
}
