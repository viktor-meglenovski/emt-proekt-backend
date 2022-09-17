package threed.manager.backend.project.domain.models;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import threed.manager.backend.project.domain.models.ids.ProjectAttachmentId;
import threed.manager.backend.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Entity
@Getter
public class ProjectAttachment extends AbstractEntity<ProjectAttachmentId> {

    @ManyToOne
    private Project project;
    private String attachmentPath;
    private String attachmentName;
    protected ProjectAttachment(){
        super(ProjectAttachmentId.randomId(ProjectAttachmentId.class));
    }
    protected ProjectAttachment(String  attachmentPath,String attachmentName,Project project){
        super(ProjectAttachmentId.randomId(ProjectAttachmentId.class));
        this.attachmentPath=attachmentPath;
        this.attachmentName=attachmentName;
        this.project=project;
    }

    public static ProjectAttachment saveAttachment(MultipartFile file, String path, String location,Project project) throws IOException {
        byte[] bytes = new byte[0];
        bytes = file.getBytes();
        Path p = Paths.get(path);
        Files.write(p, bytes);
        return new ProjectAttachment(location,file.getOriginalFilename(),project);

    }
}
