package threed.manager.backend.client.xport.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import threed.manager.backend.client.service.ClientService;
import threed.manager.backend.sharedkernel.domain.config.TopicHolder;
import threed.manager.backend.sharedkernel.domain.events.DomainEvent;
import threed.manager.backend.sharedkernel.domain.events.account.ClientAccountEdited;
import threed.manager.backend.sharedkernel.domain.events.account.ClientNewAccountCreated;
import threed.manager.backend.sharedkernel.domain.events.rating.RateClient;
import threed.manager.backend.sharedkernel.domain.events.rating.RateFreelancer;

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
            ClientAccountEdited event= DomainEvent.fromJson(jsonMessage, ClientAccountEdited.class);
            clientService.editClient(event.getEmail(),event.getName(),event.getSurname());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics= TopicHolder.TOPIC_RATE_CLIENT,groupId = "threeDManager")
    public void consumeRateClientEvent(String jsonMessage){
        try{
            RateClient event= DomainEvent.fromJson(jsonMessage, RateClient.class);
            clientService.rateByEmail(event.getEmail(),event.getRating());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
