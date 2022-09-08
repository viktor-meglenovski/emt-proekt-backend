package threed.manager.backend.sharedkernel.domain.events.account;

import lombok.Getter;
import threed.manager.backend.sharedkernel.domain.config.TopicHolder;
import threed.manager.backend.sharedkernel.domain.events.DomainEvent;

@Getter
public class FreelancerAccountEdited extends DomainEvent {
    private String email;
    private String name;
    private String surname;
    public FreelancerAccountEdited(){
        super(TopicHolder.TOPIC_FREELANCER_ACCOUNT_EDITED);
    }
    public FreelancerAccountEdited(String email, String name, String surname){
        super(TopicHolder.TOPIC_FREELANCER_ACCOUNT_EDITED);
        this.name=name;
        this.email=email;
        this.surname=surname;
    }
}
