package threed.manager.backend.client.domain.models;

import lombok.Getter;
import threed.manager.backend.sharedkernel.domain.enumerations.Role;
import threed.manager.backend.sharedkernel.value_objects.Rating;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Client {
    @Id
    private String email;
    private String name;
    private String surname;
    private String company;
    private Rating rating;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public Client(String email, String name, String surname) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.rating=new Rating();
        this.role=Role.CLIENT;
    }
    public Client(){
        this.rating=new Rating();
        this.role=Role.CLIENT;
    }
    public void rateClient(Integer newGrade){
        this.rating=this.rating.addRating(newGrade);
    }
    public void editNameAndSurname(String name, String surname){
        this.name=name;
        this.surname=surname;
    }
    public void updateCompanyName(String company){
        this.company=company;
    }
}
