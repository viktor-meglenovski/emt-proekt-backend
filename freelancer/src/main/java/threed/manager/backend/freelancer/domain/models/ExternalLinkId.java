package threed.manager.backend.freelancer.domain.models;

import lombok.NonNull;
import threed.manager.backend.sharedkernel.domain.base.DomainObjectId;

public class ExternalLinkId extends DomainObjectId {
    private ExternalLinkId() {
        super(ExternalLinkId.randomId(ExternalLinkId.class).getId());
    }

    public ExternalLinkId(@NonNull String uuid) {
        super(uuid);
    }

}
