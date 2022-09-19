package threed.manager.backend.project.domain.models.ids;

import org.springframework.lang.NonNull;
import threed.manager.backend.sharedkernel.domain.base.DomainObjectId;

public class DeliveryAttachmentId extends DomainObjectId {
    private DeliveryAttachmentId() {
        super(DeliveryAttachmentId.randomId(DeliveryAttachmentId.class).getId());
    }

    public DeliveryAttachmentId(@NonNull String uuid) {
        super(uuid);
    }

    public static DeliveryAttachmentId of(String uuid) {
        DeliveryAttachmentId p = new DeliveryAttachmentId(uuid);
        return p;
    }
}
