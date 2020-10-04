package in.viest.scrumserver.controller;

import in.viest.scrumserver.model.Vote;
import in.viest.scrumserver.repository.VoteRepository;
import in.viest.scrumserver.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
public class VoteController {

    private static final Logger log = LoggerFactory.getLogger(VoteController.class);

    private final VoteRepository voteRepository;
    private final VoteService voteService;

    public VoteController(VoteRepository voteRepository, VoteService voteService) {
        this.voteRepository = voteRepository;
        this.voteService = voteService;
    }

    @GetMapping("/list/{room]")
    @ResponseBody
    public String listRoom(@PathVariable String room) {
        Vote v = voteRepository.findByRoom(room).orElse(null);
        log.info("Listing room " + room);
        log.info("Found: " + v.toString());
        if (v == null) {
            return "TYHJÄ HUONE";
        } else {
            return "JOTAIN LÖYTYI";
        }
    }


    @GetMapping("/vote")
    @ResponseBody
    public String voteParams(@RequestParam(value = "room", defaultValue = "lounge") String room,
                             @RequestParam(value = "id", defaultValue = "nobody") String id,
                             @RequestParam(value = "value", defaultValue = "1") String value) {
        if(!save(room, id, value)) {
            return "ERROR";
        }
        return "OK: " + room + " - " + id + " - " + value.toString();
    }

    @GetMapping("/{room}/{id}/{value}")
    @ResponseBody
    public String votePath(@PathVariable String room,
                         @PathVariable String id,
                         @PathVariable String value) {
        if(!save(room, id, value)) {
            return "ERROR";
        }
        return "OK: " + room + " - " + id + " - " + value.toString();
    }

    private Boolean save(String room, String  id, String value) {
        String message = MessageFormat.format("ROOM {0} - {1} voted {2}", room, id, value);
        log.info(message);
        try {
            Integer points = Integer.valueOf(value);
            voteService.save(room, id, points);
        } catch (Exception e) {
            log.error("ERROR: " + e.getMessage());
            return false;
        }
        return true;
    }

}