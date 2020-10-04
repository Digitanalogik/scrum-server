package in.viest.scrumserver.service;

import in.viest.scrumserver.model.Vote;
import in.viest.scrumserver.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DefaultVoteService implements VoteService {

    private static final Logger log = LoggerFactory.getLogger(DefaultVoteService.class);

    @Autowired
    private  final VoteRepository voteRepository;

    public DefaultVoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    @Transactional
    public void save(String room, String id, Integer value) {
        log.info("Counting votes in room " + room + " - " + id + " voted " + value);

        Vote v = voteRepository.findByRoomAndId(room, id)
                .orElse(new Vote(room, id, value));
        v.setValue(value);
        voteRepository.save(v);
    }

    @Override
    @Transactional
    public void save(Vote vote) {
        log.info("Counting votes in room " + vote.getRoom() + " - " + vote.getId() + " voted " + vote.getValue());

        Vote v = voteRepository.findByRoomAndId(vote.getRoom(), vote.getId())
                .orElse(vote);
        v.setValue(vote.getValue());
        voteRepository.save(v);
    }

}
