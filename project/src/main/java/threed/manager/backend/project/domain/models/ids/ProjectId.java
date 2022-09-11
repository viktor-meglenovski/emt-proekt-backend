package threed.manager.backend.project.domain.models.ids;

import org.springframework.lang.NonNull;
import threed.manager.backend.sharedkernel.domain.base.DomainObjectId;

public class ProjectId extends DomainObjectId {
    private ProjectId() {
        super(ProjectId.randomId(ProjectId.class).getId());
    }

    public ProjectId(@NonNull String uuid) {
        super(uuid);
    }

    public static ProjectId of(String uuid) {
        ProjectId p = new ProjectId(uuid);
        return p;
    }

}
