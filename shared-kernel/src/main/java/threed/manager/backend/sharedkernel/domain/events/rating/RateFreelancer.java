package threed.manager.backend.sharedkernel.domain.events.rating;

import lombok.Getter;
import threed.manager.backend.sharedkernel.domain.config.TopicHolder;
import threed.manager.backend.sharedkernel.domain.events.DomainEvent;

@Getter
public class RateFreelancer extends DomainEvent {
    private String email;
    private int rating;
    public RateFreelancer(){
        super(TopicHolder.TOPIC_RATE_FREELANCER);
    }
    public RateFreelancer(String email, int rating){
        super(TopicHolder.TOPIC_RATE_FREELANCER);
        this.email=email;
        this.rating=rating;
    }
}
