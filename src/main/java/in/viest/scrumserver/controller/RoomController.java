package in.viest.scrumserver.controller;

import in.viest.scrumserver.model.Room;
import in.viest.scrumserver.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    RoomService roomService;

    @GetMapping(path = "/list")
    public List<Room> listRooms() {
        return roomService.listRooms();
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

}
