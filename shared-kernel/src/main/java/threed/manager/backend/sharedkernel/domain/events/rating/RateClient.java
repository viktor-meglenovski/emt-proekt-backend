package threed.manager.backend.sharedkernel.domain.events.rating;

import lombok.Getter;
import threed.manager.backend.sharedkernel.domain.config.TopicHolder;
import threed.manager.backend.sharedkernel.domain.events.DomainEvent;

@Getter
public class RateClient extends DomainEvent {
    private String email;
    private int rating;
    public RateClient(){
        super(TopicHolder.TOPIC_RATE_CLIENT);
    }
    public RateClient(String email, int rating){
        super(TopicHolder.TOPIC_RATE_CLIENT);
        this.email=email;
        this.rating=rating;
    }
}

