package threed.manager.backend.client.domain.models;

import lombok.Getter;
import threed.manager.backend.sharedkernel.value_objects.Rating;

import javax.persistence.Entity;
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

    public Client(String email, String name, String surname) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.rating=new Rating();
    }
    public Client(){
        this.rating=new Rating();
    }
    public void rateClient(Integer newGrade){
        List<Integer> existingGrades=this.rating.getGrades();
        existingGrades.add(newGrade);
        this.rating=new Rating(existingGrades);
    }
    public void editNameAndSurname(String name, String surname){
        this.name=name;
        this.surname=surname;
    }
    public void updateCompanyName(String company){
        this.company=company;
    }
}
