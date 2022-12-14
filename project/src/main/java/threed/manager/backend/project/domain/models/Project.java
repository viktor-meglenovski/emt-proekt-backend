package threed.manager.backend.project.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import threed.manager.backend.project.domain.enumerations.ProjectStatusEnumeration;
import threed.manager.backend.project.domain.models.ids.ProjectId;
import threed.manager.backend.project.domain.value_objects.AppUser;
import threed.manager.backend.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="projects")
@Getter
public class Project extends AbstractEntity<ProjectId> {
    private String name;
    private String description;
    private Date dueDate;
    @Enumerated(value = EnumType.STRING)
    @Column(name="projectStatus")
    private ProjectStatusEnumeration status;
    private String folderLocation;
    private Integer clientRating;
    private Integer freelancerRating;

    @AttributeOverrides({
            @AttributeOverride(name="email", column = @Column(name="client_email")),
            @AttributeOverride(name="role", column = @Column(name="client_role")),
            @AttributeOverride(name="name", column = @Column(name="client_name")),
            @AttributeOverride(name="surname", column = @Column(name="client_surname"))
    })
    private AppUser client;
    @AttributeOverrides({
            @AttributeOverride(name="email", column = @Column(name="freelancer_email")),
            @AttributeOverride(name="role", column = @Column(name="freelancer_role")),
            @AttributeOverride(name="name", column = @Column(name="freelancer_name")),
            @AttributeOverride(name="surname", column = @Column(name="freelancer_surname"))
    })
    private AppUser freelancer;
    @JsonIgnoreProperties(value = {"project"})
    @OneToMany(mappedBy="project", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> projectTasks;

    @JsonIgnoreProperties(value = {"project"})
    @OneToMany(mappedBy="project",fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectAttachment> attachments;
    public Project(){
        super(ProjectId.randomId(ProjectId.class));
    }
    public static Project build(String name, String description, Date dueDate, AppUser client, AppUser freelancer){
        Project p=new Project();
        p.name=name;
        p.description=description;
        p.dueDate=dueDate;
        p.status=ProjectStatusEnumeration.PROPOSED;
        p.client=client;
        p.freelancer=freelancer;
        p.projectTasks=new HashSet<>();
        p.attachments=new HashSet<>();
        p.clientRating=null;
        p.freelancerRating=null;
        return p;
    }
    public void changeStatus(ProjectStatusEnumeration newStatus){
        this.status=newStatus;
    }
    public void updateFolderLocation(String folderLocation){
        this.folderLocation=folderLocation;
    }
    public void addAttachment(ProjectAttachment projectAttachment){
        this.attachments.add(projectAttachment);
    }
    public boolean isFreelancerForProject(String freelancerEmail){
        return this.freelancer.getEmail().equals(freelancerEmail);
    }
    public boolean isClientForProject(String clientEmail){
        return this.client.getEmail().equals(clientEmail);
    }
    public Task createNewTask(String taskTitle){
        Task task=Task.build(taskTitle,this);
        this.projectTasks.add(task);
        return task;
    }
    public void changeClientRating(Integer rating){
        this.clientRating=rating;
    }
    public void changeFreelancerRating(Integer rating){
        this.freelancerRating=rating;
    }
}
