package in.viest.scrumserver.service;

import in.viest.scrumserver.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    Player create(String name);
    Player create(String name, Integer room);
    Player create(String name, String photo);
    Player create(String name, String photo, Integer room);

    Optional<Player> get(String name);
    Optional<Player> get(Integer id);

    List<Player> listPlayers();
    List<Player> listPlayersInRoom(Integer room);
    List<Player> listPlayersInRoom(String room);

}
