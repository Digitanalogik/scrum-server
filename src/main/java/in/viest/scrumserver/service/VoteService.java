package in.viest.scrumserver.service;

import in.viest.scrumserver.model.Vote;

public interface VoteService {

    void save(String room, String id, Integer value);
    void save(Vote vote);
}
