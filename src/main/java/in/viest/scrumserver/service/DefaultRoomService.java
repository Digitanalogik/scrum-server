package in.viest.scrumserver.service;

import in.viest.scrumserver.model.Player;
import in.viest.scrumserver.model.Room;
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
public class DefaultRoomService implements RoomService {

    private static final Logger log = LoggerFactory.getLogger(DefaultRoomService.class);

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final PlayerService playerService;

    public DefaultRoomService(RoomRepository roomRepository, PlayerService playerService) {
        this.roomRepository = roomRepository;
        this.playerService = playerService;
    }

    @Override
    @Transactional
    public Room create(String name) {
        log.info("Creating new room: " + name);

        /*
        Room r = roomRepository.findByName(name)
                .orElse(new Room(name));*/
        Room r = new Room(name);
        roomRepository.save(r);

        return r;
    }

    @Override
    @Transactional
    public Room create(String name, String password) {
        log.info("Creating new room: " + name + " - secured by password: " + password);

        /* Update...
        Room r = roomRepository.findByName(name)
                .orElse(new Room(name, password));

         */
        Room r = new Room(name, password);
        roomRepository.save(r);

        return r;
    }

    @Override
    public Optional<Room> get(Integer id) {
        return roomRepository.findById(id);
    }

    @Override
    public Optional<Room> get(String name) {
        return roomRepository.findByName(name);
    }

    @Override
    public List<Room> listRooms() {
        return (List<Room>) roomRepository.findAll();
    }

    @Override
    public List<Player> listPlayersInRoom(Integer room) {
        log.info("PlayerService was requested to list players in the room id " + room);
        return playerService.listPlayersInRoom(room);
    }

    @Override
    public List<Player> listPlayersInRoom(String room) {
        log.info("RoomService was requested to list players in the room " + room);
        Optional<Room> r = roomRepository.findByName(room);
        if (r.isPresent()) {
            log.info("RoomService is listing players in the room " + room);
            List<Player> players = playerService.listPlayers()
                    .stream()
                    .filter(p -> p.getRoom() == r.get().getId())
                    .collect(Collectors.toList());
            return players;
        }
        log.info("RoomService was not able to find a room " + room);
        return null;
    }
}
