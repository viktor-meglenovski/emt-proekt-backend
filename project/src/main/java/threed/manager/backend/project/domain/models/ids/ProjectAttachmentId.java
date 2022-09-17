package threed.manager.backend.project.domain.models.ids;

import org.springframework.lang.NonNull;
import threed.manager.backend.sharedkernel.domain.base.DomainObjectId;

public class ProjectAttachmentId extends DomainObjectId {
    private ProjectAttachmentId() {
        super(ProjectAttachmentId.randomId(ProjectAttachmentId.class).getId());
    }

    public ProjectAttachmentId(@NonNull String uuid) {
        super(uuid);
    }

    public static ProjectAttachmentId of(String uuid) {
        ProjectAttachmentId p = new ProjectAttachmentId(uuid);
        return p;
    }
}
