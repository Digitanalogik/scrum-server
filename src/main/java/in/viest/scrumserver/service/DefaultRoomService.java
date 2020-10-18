package in.viest.scrumserver.service;

import in.viest.scrumserver.model.Player;
import in.viest.scrumserver.model.Room;
import in.viest.scrumserver.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultRoomService implements RoomService {

    private static final Logger log = LoggerFactory.getLogger(DefaultRoomService.class);

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final PlayerService playerService;

    public DefaultRoomService(RoomRepository roomRepository, PlayerService playerService, SimpMessagingTemplate simpMessagingTemplate) {
        this.roomRepository = roomRepository;
        this.playerService = playerService;
        this.simpMessagingTemplate = simpMessagingTemplate;
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
        log.info("RoomService was scheduled to list players in the room " + room);
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

    @Override
    public Boolean allPlayersVoted(String room) {
        log.info("RoomService is evaluating if all players voted in the room " + room);
        Integer totalPlayers = this.totalPlayersInRoom(room);
        log.info("Total players in room: " + totalPlayers.toString());
        if (totalPlayers > 0) {
            Integer votedPlayers = this.votedPlayersInRoom(room);
            log.info("Voted players in room: " + votedPlayers.toString());
            if (votedPlayers == totalPlayers) {
                return true;
            }
        }
        return false;
    }

    /*
    @Scheduled(cron = "0 * * * * *")
    public void listPlayersInRoomCasino() {
        log.info("RoomService was scheduled to list players in the room casino ");
        Optional<Room> r = roomRepository.findByName("casino");
        if (r.isPresent()) {
            log.info("RoomService is listing players in the room casino");
            List<Player> players = playerService.listPlayers()
                    .stream()
                    .filter(p -> p.getRoom() == r.get().getId())
                    .collect(Collectors.toList());
            this.simpMessagingTemplate.convertAndSend("/room/listen", players);
            log.info("Sent player list to message queue /room/listen");
        } else {
            log.info("RoomService was not able to find a room casino ");
        }
    }
    */

    @Override
    public int totalPlayersInRoom(String room) {
        return playerService.listPlayersInRoom(room).size();
    }

    @Override
    public int votedPlayersInRoom(String room) {
        return (int) playerService.listPlayersInRoom(room)
                .stream()
                .filter(p -> p.getVote() != null)
                .count();
    }

    /*
    @Scheduled(cron = "0 * * * * *")
    public void hello() {
        Instant now = Instant.now();
        log.info("Scheduled task performed at {} (ISO 8601 date and time format)", now);
        this.simpMessagingTemplate.convertAndSend("/queue/now", now);
    }
    */
}
