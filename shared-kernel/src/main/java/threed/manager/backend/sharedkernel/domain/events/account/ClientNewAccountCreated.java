package threed.manager.backend.sharedkernel.domain.events.account;

import lombok.Getter;
import threed.manager.backend.sharedkernel.domain.config.TopicHolder;
import threed.manager.backend.sharedkernel.domain.events.DomainEvent;

@Getter
public class ClientNewAccountCreated extends DomainEvent {
    private String email;
    private String name;
    private String surname;
    public ClientNewAccountCreated(){
        super(TopicHolder.TOPIC_CLIENT_NEW_ACCOUNT_CREATED);
    }
    public ClientNewAccountCreated(String email, String name, String surname){
        super(TopicHolder.TOPIC_CLIENT_NEW_ACCOUNT_CREATED);
        this.name=name;
        this.email=email;
        this.surname=surname;
    }
}
