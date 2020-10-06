package in.viest.scrumserver.service;

import in.viest.scrumserver.model.Player;
import in.viest.scrumserver.model.Room;
import in.viest.scrumserver.repository.PlayerRepository;
import in.viest.scrumserver.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultPlayerService implements PlayerService {

    private static final Logger log = LoggerFactory.getLogger(DefaultPlayerService.class);

    @Autowired
    private final PlayerRepository playerRepository;

    @Autowired
    private final RoomRepository roomRepository;

    public DefaultPlayerService(PlayerRepository playerRepository, RoomRepository roomRepository) {
        this.playerRepository = playerRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public Player create(String name) {

        log.info("Creating new player: " + name);

        Player p = new Player(name);
        playerRepository.save(p);

        return p;
    }

    @Override
    @Transactional
    public Player create(String name, String photo) {

        log.info("Creating new player: " + name + " with photo from URL: " + photo);

        Player p = new Player(name, photo);
        playerRepository.save(p);

        return p;
    }

    @Override
    public Optional<Player> get(String name) {
        return playerRepository.findByName(name);
    }

    @Override
    public List<Player> listPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public List<Player> listPlayersInRoom(String room) {
        log.info("PlayerService was requested to list players in the room " + room);
        Optional<Room> r = roomRepository.findByName(room);
        if (r.isPresent()) {
            log.info("PlayerService is listing players in the room " + room);
            List<Player> players = playerRepository.findAll()
                    .stream()
                    .filter(p -> p.getRoom() == r.get())
                    .collect(Collectors.toList());
            return players;
        }
        log.info("Client requested to list unknown room " + room);
        return null;
    }
}
