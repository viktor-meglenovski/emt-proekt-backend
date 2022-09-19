package threed.manager.backend.project.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import threed.manager.backend.project.domain.enumerations.MessageMetadata;
import threed.manager.backend.project.domain.models.ids.MessageId;
import threed.manager.backend.project.domain.value_objects.AppUser;
import threed.manager.backend.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "messages")
@Getter
public class Message extends AbstractEntity<MessageId> {
    @ManyToOne
    private Task task;
    private AppUser entryPoster;
    private String content;
    private LocalDateTime postingTime;
    @Enumerated(value = EnumType.STRING)
    private MessageMetadata metadata;

    @JsonIgnoreProperties(value = {"message"})
    @OneToMany(mappedBy = "message", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MessageAttachment> messageAttachments;

    public Message() {
        super(MessageId.randomId(MessageId.class));
    }

    public static Message build(Task task, AppUser entryPoster, String content) {
        Message message = new Message();
        message.task=task;
        message.entryPoster=entryPoster;
        message.content=content;
        message.messageAttachments=new HashSet<>();
        message.postingTime=LocalDateTime.now();
        message.metadata=MessageMetadata.NONE;
        return message;
    }
    public static Message build(Task task, AppUser entryPoster, String content,MessageMetadata metadata) {
        Message message = new Message();
        message.task=task;
        message.entryPoster=entryPoster;
        message.content=content;
        message.messageAttachments=new HashSet<>();
        message.postingTime=LocalDateTime.now();
        message.metadata=metadata;
        return message;
    }
    public void addAttachment(MessageAttachment messageAttachment){
        this.messageAttachments.add(messageAttachment);
    }
}
