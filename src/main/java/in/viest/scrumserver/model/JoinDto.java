package in.viest.scrumserver.model;

public class JoinDto {
    private Room room;
    private Player player;

    public JoinDto() {
    }

    public JoinDto(Room room, Player player) {
        this.room = room;
        this.player = player;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "JoinDto{" +
                "room=[" + this.getRoom().getId() + ":" + this.getRoom().getName() + "]" +
                ", player=[" + this.getPlayer().getId() + ":" + this.getPlayer().getName() + "]" +
                '}';
    }
}
