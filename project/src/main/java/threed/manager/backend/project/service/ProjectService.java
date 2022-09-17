package threed.manager.backend.project.service;

import org.springframework.web.multipart.MultipartFile;
import threed.manager.backend.project.domain.enumerations.ProjectStatusEnumeration;
import threed.manager.backend.project.domain.models.Project;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ProjectService {
    List<Project> findAllByClientEmail(String email);
    List<Project> findAllByFreelancerEmail(String email);
    List<Project> findAllByClientEmailAndProjectStatus(String email, String projectStatus);
    List<Project> findAllByFreelancerEmailAndProjectStatus(String email,String projectStatus);
    Project findByProjectId(String id);
    boolean isFreelancer(Project p, String email);
    boolean isClient(Project p, String email);
    Project createNewProject(String name, String description, Date dueDate, MultipartFile[] attachments, String clientEmail, String freelancerEmail);
    Project changeProjectStatus(Project p, String freelancerEmail, ProjectStatusEnumeration newStatus);
}
