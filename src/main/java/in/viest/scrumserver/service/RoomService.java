package in.viest.scrumserver.service;

import in.viest.scrumserver.model.Room;

import java.util.List;

public interface RoomService {

    Room create(String name);
    Room create(String name, String password);

    List<Room> listRooms();
}
