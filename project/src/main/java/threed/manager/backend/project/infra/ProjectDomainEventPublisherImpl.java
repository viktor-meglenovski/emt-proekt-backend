package threed.manager.backend.project.infra;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import threed.manager.backend.sharedkernel.domain.events.DomainEvent;
import threed.manager.backend.sharedkernel.infra.DomainEventPublisher;

@Service
@AllArgsConstructor
public class ProjectDomainEventPublisherImpl implements DomainEventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(DomainEvent event) {
        this.kafkaTemplate.send(event.topic(),event.toJson());
    }
}
