package threed.manager.backend.project.domain.models;

import lombok.Getter;
import threed.manager.backend.project.domain.models.ids.DiscussionEntryId;
import threed.manager.backend.project.domain.value_objects.AppUser;
import threed.manager.backend.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="discussion_entries")
@Getter
public class DiscussionEntry extends AbstractEntity<DiscussionEntryId> {
    @ManyToOne
    private Task task;
    private AppUser entryPoster;
    private String content;
}
