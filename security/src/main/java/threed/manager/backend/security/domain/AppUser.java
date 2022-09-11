package threed.manager.backend.security.domain;

import lombok.Getter;
import threed.manager.backend.sharedkernel.domain.enumerations.Role;

import javax.persistence.*;

@Entity
@Table(name="app_users")
@Getter
public class AppUser {
    @Id
    private String email;
    private String name;
    private String surname;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public AppUser(){

    }

    public AppUser(String email, String name, String surname, String password, Role role) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role=role;
    }
    public void updateNameAndSurname(String name, String surname){
        this.name=name;
        this.surname=surname;
    }
}
