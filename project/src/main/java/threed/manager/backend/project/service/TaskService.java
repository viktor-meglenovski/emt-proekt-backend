package threed.manager.backend.project.service;

import org.springframework.web.multipart.MultipartFile;
import threed.manager.backend.project.domain.models.Project;
import threed.manager.backend.project.domain.models.Task;

public interface TaskService {
    Task addNewTaskToProject(Project p, String taskTitle);
    Task addMessageToTask(Task task, String email, String role, String content, MultipartFile[] messageAttachments);
}
