package in.viest.scrumserver.model;

public class VoteDto {

    private String room;
    private String player;
    private Integer vote;

    public VoteDto() {
    }

    public VoteDto(String room, String player, Integer vote) {
        this.room = room;
        this.player = player;
        this.vote = vote;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "{" +
                "\"player\":\"" + player + "\"" +
                ", \"room\":\"'" + room + "\"" +
                ", \"vote\":" + vote +
                "}";
    }
}
