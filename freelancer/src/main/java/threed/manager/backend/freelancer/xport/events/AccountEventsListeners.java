package threed.manager.backend.freelancer.xport.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import threed.manager.backend.freelancer.service.FreelancerService;
import threed.manager.backend.sharedkernel.domain.config.TopicHolder;
import threed.manager.backend.sharedkernel.domain.events.DomainEvent;
import threed.manager.backend.sharedkernel.domain.events.account.FreelancerNewAccountCreated;

@Service
@AllArgsConstructor
public class AccountEventsListeners {
    private final FreelancerService freelancerService;

    @KafkaListener(topics= TopicHolder.TOPIC_FREELANCER_NEW_ACCOUNT_CREATED,groupId = "threeDManager")
    public void consumeFreelancerNewAccountCreatedEvent(String jsonMessage){
        try{
            FreelancerNewAccountCreated event= DomainEvent.fromJson(jsonMessage, FreelancerNewAccountCreated.class);
            freelancerService.addNewFreelancer(event.getEmail(),event.getName(),event.getSurname());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics= TopicHolder.TOPIC_FREELANCER_ACCOUNT_EDITED,groupId = "threeDManager")
    public void consumeFreelancerAccountEditedEvent(String jsonMessage){
        try{
            FreelancerNewAccountCreated event= DomainEvent.fromJson(jsonMessage, FreelancerNewAccountCreated.class);
            freelancerService.editFreelancer(event.getEmail(),event.getName(),event.getSurname());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
