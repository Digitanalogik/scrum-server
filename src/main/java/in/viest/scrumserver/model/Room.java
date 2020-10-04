package in.viest.scrumserver.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POKER_ROOM_ID")
    @SequenceGenerator(name = "POKER_ROOM_ID", sequenceName = "POKER_ROOM_ID", allocationSize = 1)
    private Integer id;
    private String name;
    private String password;

    public Room() {
    }

    public Room(String name) {
        this.name = name;
    }

    public Room(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
