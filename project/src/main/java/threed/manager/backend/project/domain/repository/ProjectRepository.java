package threed.manager.backend.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import threed.manager.backend.project.domain.enumerations.ProjectStatusEnumeration;
import threed.manager.backend.project.domain.models.Project;
import threed.manager.backend.project.domain.models.ids.ProjectId;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, ProjectId> {
    List<Project> findAllByClient_Email(String email);
    List<Project> findAllByFreelancer_Email(String email);
    List<Project> findAllByClient_EmailAndStatus(String email, ProjectStatusEnumeration enumeration);
    List<Project> findAllByFreelancer_EmailAndStatus(String email, ProjectStatusEnumeration enumeration);
}
