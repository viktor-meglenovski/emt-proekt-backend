package threed.manager.backend.project.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import threed.manager.backend.project.domain.enumerations.TaskStatusEnumeration;
import threed.manager.backend.project.domain.models.ids.TaskId;
import threed.manager.backend.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="tasks")
@Getter
public class Task extends AbstractEntity<TaskId> {
    private String title;
    @Enumerated(value = EnumType.STRING)
    private TaskStatusEnumeration taskStatus;
    @ManyToOne
    private Project project;

    private LocalDateTime timestamp;
    @JsonIgnoreProperties(value = {"task"})
    @OneToMany(mappedBy="task", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> messages;

    @JsonIgnoreProperties(value = {"task"})
    @OneToMany(mappedBy="task", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Delivery> deliveries;

    private String folderLocation;
    public Task(){
        super(TaskId.randomId(TaskId.class));
        this.taskStatus=TaskStatusEnumeration.STARTED;
    }
    public static Task build(String title, Project project){
        Task task=new Task();
        task.title=title;
        task.project=project;
        task.messages=new HashSet<>();
        task.deliveries=new HashSet<>();
        task.timestamp=LocalDateTime.now();
        return task;
    }
    public void updateFolderLocation(String folderLocation){
        this.folderLocation=folderLocation;
    }
    public void addMessage(Message message){
        this.messages.add(message);
    }
    public void addDelivery(Delivery delivery){
        this.deliveries.add(delivery);
    }
    public void changeStatus(TaskStatusEnumeration taskStatus){
        this.taskStatus=taskStatus;
    }

}
