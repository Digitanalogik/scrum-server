package in.viest.scrumserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.viest.scrumserver.model.*;
import in.viest.scrumserver.service.PlayerService;
import in.viest.scrumserver.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    RoomService roomService;

    @Autowired
    PlayerService playerService;

    public RoomController(SimpMessagingTemplate simpMessagingTemplate, RoomService roomService, PlayerService playerService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.roomService = roomService;
        this.playerService = playerService;
    }

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
        // log.info("Client requested to create a room called " + room);
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
                JoinDto dto = new JoinDto(r.get(), p.get());
                this.simpMessagingTemplate.convertAndSend("/room/listen", dto);
                log.info("Notified websocket /room/listen, dto=", dto);
            }
            return "OK";
        }
        return "FAIL";
    }

    @Transactional
    @GetMapping("/{room}/vote/{player}")
    public Response vote(@PathVariable String room,
                       @PathVariable String player,
                       @RequestParam(defaultValue = "0") String points) {
        log.info("Player [" + player + "] in room [" + room + "] wants to vote " + points);
        Optional<Player> p = playerService.get(player);
        if (p.isPresent()) {
            log.info("RoomController found the player [" + p.get().getName() + "] [id:" + p.get().getId() + "]");
            Optional<Room> r = roomService.get(room);
            if (r.isPresent()) {
                p.get().setRoom(r.get().getId());
                p.get().setVote(Integer.valueOf(points));
                log.info("Vote accepted! Player [" + p.get().getName() + "] in room " + r.get().getName() + " [id:" + r.get().getId() + "] voted " + points);
            }
            return new Response(200, "OK");
        }
        log.error("Something odd happened, shouldn't be here...");
        return new Response(401, "No-Go");
    }

    /*
    @MessageMapping("/join")
    public void play(JoinDto request) throws  Exception {

        log.info("WebSocket Player [" + request.getPlayer() + "] wants to join Room: " + request.getRoom());
        Optional<Player> p = playerService.get(request.getPlayer());
        if (p.isPresent()) {
            log.info("RoomController found the player " + p.get().getName() + " (id:" + p.get().getId() + ")");
            Optional<Room> r = roomService.get(request.getRoom());
            if (r.isPresent()) {
                p.get().setRoom(r.get().getId());
                log.info("Player [" + p.get().getName() + "] joined room " + r.get().getName() + " (id:" + r.get().getId() + ")");
            }
            String text = "Hello, Room!";
            this.simpMessagingTemplate.convertAndSend("/room/listen", text);
        }
        this.simpMessagingTemplate.convertAndSend("/room/listen", "Join.");
    }

     */

    @MessageMapping("/vote")
    @Transactional
    public void vote(String text) throws  Exception {
        log.info("WebSocket vote: " + text);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            VoteDto voteDto = objectMapper.readValue(text, VoteDto.class);
            vote(voteDto.getRoom(), voteDto.getPlayer(), voteDto.getVote());
        } catch (IOException e) {
            log.error("JSON error while processing vote: ", e);
        }
        // this.simpMessagingTemplate.convertAndSend("/room/listen", text);
    }

    @Transactional
    public void vote(String room, String player, Integer points) {
        log.info("Player [" + player + "] in room [" + room + "] wants to vote " + points);
        Optional<Player> p = playerService.get(player);
        if (p.isPresent()) {
            log.info("RoomController found the player [" + p.get().getName() + "] [id:" + p.get().getId() + "]");
            Optional<Room> r = roomService.get(room);
            if (r.isPresent()) {
                p.get().setRoom(r.get().getId());
                p.get().setVote(points);
                log.info("Vote accepted! Player [" + p.get().getName() + "] in room " + r.get().getName() + " [id:" + r.get().getId() + "] voted " + points);
                VoteDto vote = new VoteDto(r.get().getName(), p.get().getName(), points);
                this.simpMessagingTemplate.convertAndSend("/room/vote", vote);
                if (this.roomService.allPlayersVoted(room)) {
                    this.simpMessagingTemplate.convertAndSend("/room/done", this.roomService.listPlayersInRoom(room));
                }
            }
        } else {
            log.error("Something odd happened, shouldn't be here... [room=" + room + "],[player=" + player + "],[vote=" + points + "]");
        }
    }

}
