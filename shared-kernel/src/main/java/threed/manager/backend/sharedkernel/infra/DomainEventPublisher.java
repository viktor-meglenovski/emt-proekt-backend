package threed.manager.backend.sharedkernel.infra;

import threed.manager.backend.sharedkernel.domain.events.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
