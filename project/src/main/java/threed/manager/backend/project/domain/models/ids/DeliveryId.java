package threed.manager.backend.project.domain.models.ids;

import org.springframework.lang.NonNull;
import threed.manager.backend.sharedkernel.domain.base.DomainObjectId;

public class DeliveryId extends DomainObjectId {
    private DeliveryId() {
        super(DeliveryId.randomId(DeliveryId.class).getId());
    }

    public DeliveryId(@NonNull String uuid) {
        super(uuid);
    }

    public static DeliveryId of(String uuid) {
        DeliveryId p = new DeliveryId(uuid);
        return p;
    }
}
