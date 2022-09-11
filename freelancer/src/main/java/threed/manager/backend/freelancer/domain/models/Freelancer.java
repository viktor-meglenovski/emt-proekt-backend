package threed.manager.backend.freelancer.domain.models;

import lombok.Getter;
import threed.manager.backend.sharedkernel.value_objects.Rating;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="freelancers")
@Getter
public class Freelancer {

    @Id
    private String email;
    private String name;
    private String surname;
    @OneToMany(mappedBy = "freelancer", fetch=FetchType.EAGER)
    private List<ExternalLink> externalLinks;
    private Rating rating;

    public Freelancer(String email, String name, String surname) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.externalLinks=new ArrayList<>();
        this.rating=new Rating();
    }
    public Freelancer(){
        this.externalLinks=new ArrayList<>();
        this.rating=new Rating();
    }
    public ExternalLink checkIfExternalLinkExists(ExternalLinkName externalLinkName){
        return this.getExternalLinks().stream().filter(x->x.getName().equals(externalLinkName.getName())).findFirst().orElse(null);
    }
    public void addExternalLink(ExternalLinkName externalLinkName, String link){
        if(checkIfExternalLinkExists(externalLinkName)==null){
            this.externalLinks.add(new ExternalLink(externalLinkName,link));
        }

    }
    public void removeExternalLink(ExternalLinkName externalLinkName){
        if(checkIfExternalLinkExists(externalLinkName)!=null){
            this.externalLinks.removeIf(x->x.getName().equals(externalLinkName.getName()));
        }
    }
    public void rateFreelancer(Integer newGrade){
        List<Integer> existingGrades=this.rating.getGrades();
        existingGrades.add(newGrade);
        this.rating=new Rating(existingGrades);
    }
    public void editNameAndSurname(String name, String surname){
        this.name=name;
        this.surname=surname;
    }
}
