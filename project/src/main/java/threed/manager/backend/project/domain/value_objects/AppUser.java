package threed.manager.backend.project.domain.value_objects;

import lombok.Getter;
import threed.manager.backend.sharedkernel.domain.base.ValueObject;
import threed.manager.backend.sharedkernel.domain.enumerations.Role;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
public class AppUser implements ValueObject {
    private final String email;
    @Enumerated(value = EnumType.STRING)
    private final Role role;


    public AppUser(String email,Role role) {
        this.email = email;
        this.role=role;
    }
    public AppUser(){
        this.email = "";
        this.role=Role.CLIENT;
    }
}
