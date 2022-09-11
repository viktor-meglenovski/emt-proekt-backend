package threed.manager.backend.project.domain.models.ids;

import org.springframework.lang.NonNull;
import threed.manager.backend.sharedkernel.domain.base.DomainObjectId;

public class TaskId extends DomainObjectId {
    private TaskId() {
        super(TaskId.randomId(TaskId.class).getId());
    }

    public TaskId(@NonNull String uuid) {
        super(uuid);
    }

    public static TaskId of(String uuid) {
        TaskId p = new TaskId(uuid);
        return p;
    }
}
