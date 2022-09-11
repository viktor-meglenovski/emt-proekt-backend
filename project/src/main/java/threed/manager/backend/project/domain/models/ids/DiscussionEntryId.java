package threed.manager.backend.project.domain.models.ids;

import org.springframework.lang.NonNull;
import threed.manager.backend.sharedkernel.domain.base.DomainObjectId;

public class DiscussionEntryId extends DomainObjectId {
    private DiscussionEntryId() {
        super(DiscussionEntryId.randomId(DiscussionEntryId.class).getId());
    }

    public DiscussionEntryId(@NonNull String uuid) {
        super(uuid);
    }

    public static DiscussionEntryId of(String uuid) {
        DiscussionEntryId p = new DiscussionEntryId(uuid);
        return p;
    }
}
