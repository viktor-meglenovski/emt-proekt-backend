package threed.manager.backend.project.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import threed.manager.backend.project.domain.enumerations.ProjectStatusEnumeration;
import threed.manager.backend.project.domain.models.Project;
import threed.manager.backend.project.domain.models.ids.ProjectId;
import threed.manager.backend.project.domain.repository.ProjectRepository;
import threed.manager.backend.project.service.ProjectService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
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
    public Project createNewProject(String name, String description, LocalDateTime dueDateTime, String clientEmail, String freelancerEmail) {
        Project p=Project.build(name,description,dueDateTime,clientEmail,freelancerEmail);
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
