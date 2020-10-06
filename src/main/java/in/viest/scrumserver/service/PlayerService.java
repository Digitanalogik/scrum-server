package in.viest.scrumserver.service;

import in.viest.scrumserver.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    Player create(String name);
    Player create(String name, String photo);

    Optional<Player> get(String name);

    List<Player> listPlayers();
    List<Player> listPlayersInRoom(String room);

}
