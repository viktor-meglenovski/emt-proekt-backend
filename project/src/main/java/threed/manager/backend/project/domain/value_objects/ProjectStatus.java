package threed.manager.backend.project.domain.value_objects;

import lombok.Getter;
import threed.manager.backend.project.domain.enumerations.ProjectStatusEnumeration;
import threed.manager.backend.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
public class ProjectStatus implements ValueObject {
    @Enumerated(value = EnumType.STRING)
    private final ProjectStatusEnumeration projectStatusEnumeration;

    public ProjectStatus(){
        this.projectStatusEnumeration = ProjectStatusEnumeration.PROPOSED;
    }
    public ProjectStatus(ProjectStatusEnumeration projectStatusEnumeration){
        this.projectStatusEnumeration = projectStatusEnumeration;
    }
    public ProjectStatus changeStatus(ProjectStatusEnumeration projectStatusEnumeration){
        return new ProjectStatus(projectStatusEnumeration);
    }
}
