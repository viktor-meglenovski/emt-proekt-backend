package threed.manager.backend.project.domain.models;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import threed.manager.backend.project.domain.models.ids.MessageAttachmentId;
import threed.manager.backend.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Entity
@Getter
@Table(name="message_attachments")
public class MessageAttachment extends AbstractEntity<MessageAttachmentId> {
    @ManyToOne
    private Message message;
    private String attachmentPath;
    private String attachmentName;
    protected MessageAttachment(){
        super(MessageAttachmentId.randomId(MessageAttachmentId.class));
    }
    protected MessageAttachment(String  attachmentPath, String attachmentName, Message message){
        super(MessageAttachmentId.randomId(MessageAttachmentId.class));
        this.attachmentPath=attachmentPath;
        this.attachmentName=attachmentName;
        this.message=message;
    }

    public static MessageAttachment saveAttachment(MultipartFile file, String path, Message message) throws IOException {
        byte[] bytes = new byte[0];
        bytes = file.getBytes();
        Path p = Paths.get(path);
        Files.write(p, bytes);
        return new MessageAttachment(path,file.getOriginalFilename(),message);

    }
}
