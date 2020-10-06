package in.viest.scrumserver.service;

import in.viest.scrumserver.model.Player;
import in.viest.scrumserver.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    Room create(String name);
    Room create(String name, String password);

    Optional<Room> get(String name);

    List<Room> listRooms();
    List<Player> listPlayersInRoom(String room);
}
