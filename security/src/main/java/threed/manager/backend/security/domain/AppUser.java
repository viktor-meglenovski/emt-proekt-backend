package threed.manager.backend.security.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "app_users")
@Getter
public class AppUser {
    @Id
    private String username;
    private String name;
    private String surname;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public AppUser(){

    }

    public AppUser(String username, String name, String surname, String password, Role role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role=role;
    }
}
