package threed.manager.backend.project.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import threed.manager.backend.project.domain.enumerations.ProjectStatusEnumeration;
import threed.manager.backend.project.domain.enumerations.TaskStatusEnumeration;
import threed.manager.backend.project.domain.models.Project;
import threed.manager.backend.project.domain.models.ProjectAttachment;
import threed.manager.backend.project.domain.models.Task;
import threed.manager.backend.project.domain.models.ids.ProjectId;
import threed.manager.backend.project.domain.repository.ProjectRepository;
import threed.manager.backend.project.domain.value_objects.AppUser;
import threed.manager.backend.project.infra.ProjectDomainEventPublisherImpl;
import threed.manager.backend.project.service.ProjectService;
import threed.manager.backend.project.service.TaskService;
import threed.manager.backend.project.xport.rest.ClientRestClient;
import threed.manager.backend.project.xport.rest.FreelancerRestClient;
import threed.manager.backend.sharedkernel.domain.events.account.ClientNewAccountCreated;
import threed.manager.backend.sharedkernel.domain.events.account.FreelancerNewAccountCreated;
import threed.manager.backend.sharedkernel.domain.events.rating.RateClient;
import threed.manager.backend.sharedkernel.domain.events.rating.RateFreelancer;

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
    private final ProjectDomainEventPublisherImpl domainEventPublisher;
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
    @Override
    public Task addNewDeliveryToTask(Project p, String taskId, String email, String role, String content, MultipartFile[] deliveryAttachments) {
        Task task=p.getProjectTasks().stream().filter(x->x.getId().getId().equals(taskId)).findFirst().get();
        taskService.addDeliveryToTask(task, email, role, content, deliveryAttachments);
        task.changeStatus(TaskStatusEnumeration.DELIVERED);
        projectRepository.save(p);
        return task;
    }

    @Override
    public Task provideFeedbackForDelivery(Project p, String taskId, String deliveryId, String email, String role, String feedback, boolean accepted) {
        Task task=p.getProjectTasks().stream().filter(x->x.getId().getId().equals(taskId)).findFirst().get();
        taskService.provideFeedbackForDelivery(task,deliveryId,email,role,feedback,accepted);
        task.changeStatus(accepted?TaskStatusEnumeration.ACCEPTED:TaskStatusEnumeration.IN_REVISION);
        projectRepository.save(p);
        return task;
    }

    @Override
    public Project addRatingFromClient(Project p, Integer rating) {
        p.changeClientRating(rating);
        checkIfBothHasRated(p);
        projectRepository.save(p);
        //send event
        domainEventPublisher.publish(new RateFreelancer(p.getFreelancer().getEmail(),rating));
        return p;
    }

    @Override
    public Project addRatingFromFreelancer(Project p, Integer rating) {
        p.changeFreelancerRating(rating);
        checkIfBothHasRated(p);
        projectRepository.save(p);
        domainEventPublisher.publish(new RateClient(p.getClient().getEmail(),rating));
        return p;
    }

    private void checkIfBothHasRated(Project p){
        if(p.getClientRating()!=null && p.getFreelancerRating()!=null)
            p.changeStatus(ProjectStatusEnumeration.RATED);
    }

}
