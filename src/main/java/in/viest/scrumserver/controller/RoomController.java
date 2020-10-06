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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    RoomService roomService;

    @Autowired
    PlayerService playerService;

    @GetMapping(path = "/list")
    public List<Room> listRooms() {
        log.info("RoomController is listing rooms");
        return roomService.listRooms();
    }

    @GetMapping(path = "/{room}/players")
    public List<Player> listPlayersInRooms(@PathVariable String room) {
        log.info("RoomController is listing players in room " + room);
        return roomService.listPlayersInRoom(room);
    }

    @PostMapping(path = "/create/{room}")
    public Room createRoom(@PathVariable String room, @RequestParam(name = "secret", defaultValue = "") String password ) {
        log.info("Client requested to create a room called " + room);
        Room r = null;
        try {
            if (password.equals("")) {
                r = roomService.create(room);
            } else {
                r = roomService.create(room, password);
            }
        } catch (Exception e) {
            log.error("Error creating room: " + e.getMessage());
        }
        return r;
    }

    @Transactional
    @PostMapping("/{room}/join/{player}")
    public String join(@PathVariable String room, @PathVariable String player) {
        log.info("Player [" + player + "] wants to join room " + room);
        Optional<Player> p = playerService.get(player);
        if (p.isPresent()) {
            log.info("RoomController found the player " + p.get().getName() + " (id:" + p.get().getId() + ")");
            Optional<Room> r = roomService.get(room);
            if (r.isPresent()) {
                p.get().setRoom(r.get().getId());
                log.info("Player [" + p.get().getName() + "] joined room " + r.get().getName() + " (id:" + r.get().getId() + ")");
            }
            return "OK";
        }
        return "FAIL";
    }

}
