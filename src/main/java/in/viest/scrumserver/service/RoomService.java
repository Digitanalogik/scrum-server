package in.viest.scrumserver.service;

import in.viest.scrumserver.model.Player;
import in.viest.scrumserver.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    Room create(String name);
    Room create(String name, String password);

    Optional<Room> get(Integer id);
    Optional<Room> get(String name);

    List<Room> listRooms();
    List<Player> listPlayersInRoom(Integer room);
    List<Player> listPlayersInRoom(String room);

    Boolean allPlayersVoted(String room);
    int totalPlayersInRoom(String room);
    int votedPlayersInRoom(String room);
}
