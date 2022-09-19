package threed.manager.backend.project.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import threed.manager.backend.project.domain.enumerations.ProjectStatusEnumeration;
import threed.manager.backend.project.domain.models.Project;
import threed.manager.backend.project.domain.models.ProjectAttachment;
import threed.manager.backend.project.domain.models.Task;
import threed.manager.backend.project.domain.models.ids.ProjectId;
import threed.manager.backend.project.domain.repository.ProjectRepository;
import threed.manager.backend.project.domain.value_objects.AppUser;
import threed.manager.backend.project.service.ProjectService;
import threed.manager.backend.project.service.TaskService;
import threed.manager.backend.project.xport.rest.ClientRestClient;
import threed.manager.backend.project.xport.rest.FreelancerRestClient;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static threed.manager.backend.project.config.Constants.userRootPath;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {


    private final TaskService taskService;
    private final ProjectRepository projectRepository;
    private final FreelancerRestClient freelancerRestClient;
    private final ClientRestClient clientRestClient;
    @Override
    public List<Project> findAllByClientEmail(String email) {
        return projectRepository.findAllByClient_Email(email);
    }

    @Override
    public List<Project> findAllByFreelancerEmail(String email) {
        return projectRepository.findAllByFreelancer_Email(email);
    }

    @Override
    public List<Project> findAllByClientEmailAndProjectStatus(String email, String projectStatus) {
        return projectRepository.findAllByClient_EmailAndStatus(email, ProjectStatusEnumeration.valueOf(projectStatus));
    }

    @Override
    public List<Project> findAllByFreelancerEmailAndProjectStatus(String email, String projectStatus) {
        return projectRepository.findAllByFreelancer_EmailAndStatus(email, ProjectStatusEnumeration.valueOf(projectStatus));
    }

    @Override
    public Project findByProjectId(String id) {
        return projectRepository.findById(ProjectId.of(id)).orElse(null);
    }

    @Override
    public boolean isFreelancer(Project p, String email) {
        return p.getFreelancer().getEmail().equals(email);
    }

    @Override
    public boolean isClient(Project p, String email) {
        return p.getClient().getEmail().equals(email);
    }

    @Override
    public Project createNewProject(String name, String description, Date dueDate, MultipartFile[] attachments,String clientEmail, String freelancerEmail) {
        AppUser freelancer=freelancerRestClient.findFreelancer(freelancerEmail);
        AppUser client=clientRestClient.findClient(clientEmail);

        Project p=Project.build(name,description,dueDate,client,freelancer);

        //create a new folder for the project
        File f=new File(userRootPath +p.getId().getId()+"_"+p.getName().replace(" ","_"));
        f.mkdir();
        p.updateFolderLocation(userRootPath +p.getId().getId()+"_"+p.getName().replace(" ","_"));

        //save the attachments
        if(attachments!=null){
            Arrays.stream(attachments).forEach(attachment->{
                ProjectAttachment projectAttachment= null;
                try {
                    projectAttachment = ProjectAttachment.saveAttachment(attachment, userRootPath+p.getId().getId()+"_"+p.getName().replace(" ","_")+"/"+attachment.getOriginalFilename(), p);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                p.addAttachment(projectAttachment);
            });

        }
        projectRepository.save(p);
        return p;
    }


    @Override
    public Project changeStatus(Project p, ProjectStatusEnumeration status) {
        p.changeStatus(status);
        projectRepository.save(p);
        return p;
    }

    @Override
    public Project addNewTask(Project p, String taskTitle) {
        taskService.addNewTaskToProject(p, taskTitle);
        projectRepository.save(p);
        return p;
    }

    @Override
    public Task addNewMessageToTask(Project p, String taskId, String email, String role, String content, MultipartFile[] messageAttachments) {
        Task task=p.getProjectTasks().stream().filter(x->x.getId().getId().equals(taskId)).findFirst().get();
        taskService.addMessageToTask(task, email, role, content, messageAttachments);
        projectRepository.save(p);
        return task;
    }

}
