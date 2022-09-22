package threed.manager.backend.project.domain.value_objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private final String name;
    private final String surname;
    @Enumerated(value = EnumType.STRING)
    private final Role role;


    public AppUser(Role role){
        this.email = "";
        this.role=role;
        this.name="";
        this.surname="";
    }
    public AppUser(){
        this.email = "";
        this.role=null;
        this.name="";
        this.surname="";
    }
    @JsonCreator
    public AppUser(@JsonProperty("email") String email,
                   @JsonProperty("role") Role role,
                   @JsonProperty("name") String name,
                   @JsonProperty("surname") String surname) {
        this.email=email;
        this.role=role;
        this.name=name;
        this.surname=surname;
    }

}
