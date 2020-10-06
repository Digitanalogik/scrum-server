package in.viest.scrumserver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;

    //@OneToMany(mappedBy = "room")
    //private List<Player> players;

    public Room() {
        //this.players = new ArrayList<>();
    }

    public Room(String name) {
        this.name = name;
        //this.players = new ArrayList<>();
    }

    public Room(String name, String password) {
        this.name = name;
        this.password = password;
        //this.players = new ArrayList<>();
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

    /*
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
     */
}
