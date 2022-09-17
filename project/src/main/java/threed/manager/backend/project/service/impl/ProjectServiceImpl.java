package threed.manager.backend.project.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import threed.manager.backend.project.domain.enumerations.ProjectStatusEnumeration;
import threed.manager.backend.project.domain.models.Project;
import threed.manager.backend.project.domain.models.ProjectAttachment;
import threed.manager.backend.project.domain.models.ids.ProjectId;
import threed.manager.backend.project.domain.repository.ProjectRepository;
import threed.manager.backend.project.domain.value_objects.AppUser;
import threed.manager.backend.project.service.ProjectService;
import threed.manager.backend.project.xport.rest.ClientRestClient;
import threed.manager.backend.project.xport.rest.FreelancerRestClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    public static final String userRootPath ="project//src//main//resources//static//projects//";
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
        return projectRepository.findAllByClient_EmailAndStatus_ProjectStatusEnumeration(email, ProjectStatusEnumeration.valueOf(projectStatus));
    }

    @Override
    public List<Project> findAllByFreelancerEmailAndProjectStatus(String email, String projectStatus) {
        return projectRepository.findAllByFreelancer_EmailAndStatus_ProjectStatusEnumeration(email, ProjectStatusEnumeration.valueOf(projectStatus));
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
        if(attachments.length>0){
            Arrays.stream(attachments).forEach(attachment->{
                ProjectAttachment projectAttachment= null;
                try {
                    projectAttachment = ProjectAttachment.saveAttachment(attachment,
                            userRootPath+p.getId().getId()+"_"+p.getName().replace(" ","_")+"/"+attachment.getOriginalFilename(),
                            userRootPath+p.getId().getId()+"_"+p.getName().replace(" ","_")+"/"+attachment.getOriginalFilename(),p);
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
    public Project changeProjectStatus(Project p, String freelancerEmail, ProjectStatusEnumeration newStatus) {
        if(isFreelancer(p,freelancerEmail)){
            p.changeStatus(newStatus);
            projectRepository.save(p);
        }
        return p;
    }
}
