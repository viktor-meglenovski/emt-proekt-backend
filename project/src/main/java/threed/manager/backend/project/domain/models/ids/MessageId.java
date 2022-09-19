package threed.manager.backend.project.domain.models.ids;

import org.springframework.lang.NonNull;
import threed.manager.backend.sharedkernel.domain.base.DomainObjectId;

public class MessageId extends DomainObjectId {
    private MessageId() {
        super(MessageId.randomId(MessageId.class).getId());
    }

    public MessageId(@NonNull String uuid) {
        super(uuid);
    }

    public static MessageId of(String uuid) {
        MessageId p = new MessageId(uuid);
        return p;
    }
}
