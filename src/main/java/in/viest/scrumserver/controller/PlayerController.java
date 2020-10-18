package in.viest.scrumserver.controller;

import in.viest.scrumserver.model.Player;
import in.viest.scrumserver.model.Room;
import in.viest.scrumserver.service.PlayerService;
import in.viest.scrumserver.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private static final Logger log = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    private PlayerService playerService;

    @Autowired
    private RoomService roomService;

    public PlayerController(PlayerService playerService, RoomService roomService) {
        this.playerService = playerService;
        this.roomService = roomService;
    }

    @Transactional
    @PostMapping(path = "/create/{name}")
    public Player createPlayer(@PathVariable String name,
                               @RequestParam(name = "room", defaultValue = "") String room,
                               @RequestParam(name = "rid", defaultValue = "") String roomId,
                               @RequestParam(name = "photo", defaultValue = "") String photo) {
        // log.info("Client requested to create a player called " + name);
        Player player = null;
        try {
            if (photo.equals("")) {
                player = playerService.create(name);
            } else {
                player = playerService.create(name, photo);
            }
            log.info("Created player " + player.getName());
            if (!roomId.equals("") && Integer.valueOf(roomId) > 0) {
                log.info("Processing optional parameter room id: " + roomId);
                player.setRoom(Integer.valueOf(roomId));
            }
            if (!room.equals("")) {
                log.info("Processing optional parameter room: " + room);
                Optional<Room> r = roomService.get(room);
                if (r.isPresent()) {
                    log.info("Room " + room + " was found. Joining player " + name);
                    player.setRoom(r.get().getId());
                }
            }
        } catch (Exception e) {
            log.error("Error creating player: " + e.getMessage());
        }
        // log.info("Player created!");
        return player;
    }
}
