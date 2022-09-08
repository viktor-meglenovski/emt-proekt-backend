package threed.manager.backend.client.xport.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import threed.manager.backend.client.service.ClientService;
import threed.manager.backend.sharedkernel.domain.config.TopicHolder;
import threed.manager.backend.sharedkernel.domain.events.DomainEvent;
import threed.manager.backend.sharedkernel.domain.events.account.ClientNewAccountCreated;

@Service
@AllArgsConstructor
public class AccountEventsListeners {
    private final ClientService clientService;

    @KafkaListener(topics= TopicHolder.TOPIC_CLIENT_NEW_ACCOUNT_CREATED,groupId = "threeDManager")
    public void consumeClientNewAccountCreatedEvent(String jsonMessage){
        try{
            ClientNewAccountCreated event= DomainEvent.fromJson(jsonMessage, ClientNewAccountCreated.class);
            clientService.addNewClient(event.getEmail(),event.getName(),event.getSurname());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics= TopicHolder.TOPIC_CLIENT_ACCOUNT_EDITED,groupId = "threeDManager")
    public void consumeClientAccountEditedEvent(String jsonMessage){
        try{
            ClientNewAccountCreated event= DomainEvent.fromJson(jsonMessage, ClientNewAccountCreated.class);
            clientService.editClient(event.getEmail(),event.getName(),event.getSurname());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
