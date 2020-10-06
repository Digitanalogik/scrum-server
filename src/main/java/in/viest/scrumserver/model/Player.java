package in.viest.scrumserver.model;

import javax.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POKER_PLAYER_ID")
    @SequenceGenerator(name = "POKER_PLAYER_ID", sequenceName = "POKER_PLAYER_ID", allocationSize = 1)
    private Integer id;
    private String name;
    private String photo;
    private Integer points;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, String photo) {
        this.name = name;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
