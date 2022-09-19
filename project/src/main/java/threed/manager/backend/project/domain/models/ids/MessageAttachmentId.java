package threed.manager.backend.project.domain.models.ids;

import org.springframework.lang.NonNull;
import threed.manager.backend.sharedkernel.domain.base.DomainObjectId;

public class MessageAttachmentId extends DomainObjectId {
    private MessageAttachmentId() {
        super(MessageAttachmentId.randomId(MessageAttachmentId.class).getId());
    }

    public MessageAttachmentId(@NonNull String uuid) {
        super(uuid);
    }

    public static MessageAttachmentId of(String uuid) {
        MessageAttachmentId p = new MessageAttachmentId(uuid);
        return p;
    }
}
