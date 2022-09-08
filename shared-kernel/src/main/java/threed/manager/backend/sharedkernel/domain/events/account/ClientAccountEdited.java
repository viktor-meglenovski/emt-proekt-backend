package threed.manager.backend.sharedkernel.domain.events.account;

import lombok.Getter;
import threed.manager.backend.sharedkernel.domain.config.TopicHolder;
import threed.manager.backend.sharedkernel.domain.events.DomainEvent;

@Getter
public class ClientAccountEdited extends DomainEvent {
    private String email;
    private String name;
    private String surname;
    public ClientAccountEdited(){
        super(TopicHolder.TOPIC_CLIENT_ACCOUNT_EDITED);
    }
    public ClientAccountEdited(String email, String name, String surname){
        super(TopicHolder.TOPIC_CLIENT_ACCOUNT_EDITED);
        this.name=name;
        this.email=email;
        this.surname=surname;
    }
}
