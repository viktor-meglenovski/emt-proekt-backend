package threed.manager.backend.project.domain.models;

import lombok.Getter;
import threed.manager.backend.project.domain.enumerations.TaskStatusEnumeration;
import threed.manager.backend.project.domain.models.ids.ProjectId;
import threed.manager.backend.project.domain.models.ids.TaskId;
import threed.manager.backend.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="tasks")
@Getter
public class Task extends AbstractEntity<TaskId> {
    private String title;
    @Enumerated(value = EnumType.STRING)
    private TaskStatusEnumeration taskStatus;
    @ManyToOne
    private Project project;
    @OneToMany(mappedBy="task", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiscussionEntry> discussionEntries;
}
