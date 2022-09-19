package threed.manager.backend.project.domain.models;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import threed.manager.backend.project.domain.models.ids.DeliveryAttachmentId;
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
@Table(name="delivery_attachments")
public class DeliveryAttachment extends AbstractEntity<DeliveryAttachmentId> {

    @ManyToOne
    private Delivery delivery;
    private String attachmentPath;
    private String attachmentName;
    protected DeliveryAttachment(){
        super(DeliveryAttachmentId.randomId(DeliveryAttachmentId.class));
    }
    protected DeliveryAttachment(String  attachmentPath, String attachmentName, Delivery delivery){
        super(DeliveryAttachmentId.randomId(DeliveryAttachmentId.class));
        this.attachmentPath=attachmentPath;
        this.attachmentName=attachmentName;
        this.delivery=delivery;
    }

    public static DeliveryAttachment saveAttachment(MultipartFile file, String path,Delivery delivery) throws IOException {
        byte[] bytes = new byte[0];
        bytes = file.getBytes();
        Path p = Paths.get(path);
        Files.write(p, bytes);
        return new DeliveryAttachment(path,file.getOriginalFilename(),delivery);

    }
}
