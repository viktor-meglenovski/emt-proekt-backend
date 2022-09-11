package threed.manager.backend.project.domain.models;

import lombok.Getter;
import threed.manager.backend.project.domain.enumerations.ProjectStatusEnumeration;
import threed.manager.backend.project.domain.models.ids.ProjectId;
import threed.manager.backend.project.domain.value_objects.AppUser;
import threed.manager.backend.project.domain.value_objects.ProjectStatus;
import threed.manager.backend.sharedkernel.domain.base.AbstractEntity;
import threed.manager.backend.sharedkernel.domain.enumerations.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="projects")
@Getter
public class Project extends AbstractEntity<ProjectId> {
    private String name;
    private String description;
    private LocalDateTime dueDateTime;
    @Enumerated(value = EnumType.STRING)
    @Column(name="projectStatus")
    private ProjectStatus status;

    @AttributeOverrides({
            @AttributeOverride(name="email", column = @Column(name="client_email")),
            @AttributeOverride(name="role", column = @Column(name="client_role"))
    })
    private AppUser client;
    @AttributeOverrides({
            @AttributeOverride(name="email", column = @Column(name="freelancer_email")),
            @AttributeOverride(name="role", column = @Column(name="freelancer_role"))
    })
    private AppUser freelancer;
    @OneToMany(mappedBy="project", fetch=FetchType.EAGER)
    private List<Task> projectTasks;
    public Project(){
        super(ProjectId.randomId(ProjectId.class));
    }
    public static Project build(String name, String description, LocalDateTime dueDateTime, String clientEmail,String freelancerEmail){
        Project p=new Project();
        p.name=name;
        p.description=description;
        p.dueDateTime=dueDateTime;
        p.status=new ProjectStatus(ProjectStatusEnumeration.PROPOSED);
        p.client=new AppUser(clientEmail, Role.CLIENT);
        p.freelancer=new AppUser(freelancerEmail, Role.FREELANCER);
        p.projectTasks=new ArrayList<>();
        return p;
    }
    public void changeStatus(ProjectStatusEnumeration newStatus){
        this.status.changeStatus(newStatus);
    }

}
