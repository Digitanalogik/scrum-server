package in.viest.scrumserver.service;

import in.viest.scrumserver.model.Room;
import in.viest.scrumserver.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class DefaultRoomService implements RoomService {

    private static final Logger log = LoggerFactory.getLogger(DefaultVoteService.class);

    @Autowired
    private final RoomRepository roomRepository;

    public DefaultRoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public Room create(String name) {
        log.info("Creating new room: " + name);

        Room r = roomRepository.findByName(name)
                .orElse(new Room(name));
        roomRepository.save(r);

        return r;
    }

    @Override
    @Transactional
    public Room create(String name, String password) {
        log.info("Creating new room: " + name + " - secured by password: " + password);

        Room r = roomRepository.findByName(name)
                .orElse(new Room(name, password));
        roomRepository.save(r);

        return r;
    }

    @Override
    public List<Room> listRooms() {
        return (List<Room>) roomRepository.findAll();
    }
}
