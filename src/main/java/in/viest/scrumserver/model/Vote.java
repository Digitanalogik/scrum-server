package in.viest.scrumserver.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "poker")
public class Vote {

    @Id
    private String id;
    private String room;
    private Integer value;

    public Vote() {
        this.id = "Anonymous";
        this.room  = "Lounge";
        this.value = 0;
    }

    public Vote(String room, String id, Integer value) {
        this.room = room;
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return  "Vote.toString() ROOM: " + room +  " - " + id + " voted " + value.toString();
    }
}
