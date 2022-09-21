package threed.manager.backend.freelancer.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import threed.manager.backend.sharedkernel.domain.enumerations.Role;
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
    @JsonIgnoreProperties(value = {"freelancer"})
    @OneToMany(mappedBy="freelancer",fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExternalLink> externalLinks;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private Rating rating;

    public Freelancer(String email, String name, String surname) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.externalLinks=new ArrayList<>();
        this.rating=new Rating();
        this.role=Role.FREELANCER;
    }
    public Freelancer(){
        this.externalLinks=new ArrayList<>();
        this.rating=new Rating();
        this.role=Role.FREELANCER;
    }
    public ExternalLink checkIfExternalLinkExists(ExternalLinkName externalLinkName){
        return this.getExternalLinks().stream().filter(x->x.getName().getName().equals(externalLinkName.getName())).findFirst().orElse(null);
    }
    public void addExternalLink(ExternalLinkName externalLinkName, String link){
        if(checkIfExternalLinkExists(externalLinkName)==null){
            this.externalLinks.add(new ExternalLink(externalLinkName,link,this));
        }

    }
    public void removeExternalLink(ExternalLinkName externalLinkName){
        if(checkIfExternalLinkExists(externalLinkName)!=null){
            this.externalLinks.removeIf(x->x.getName().getName().equals(externalLinkName.getName()));
        }
    }
    public void rateFreelancer(Integer newGrade){
        this.rating=this.rating.addRating(newGrade);
    }
    public void editNameAndSurname(String name, String surname){
        this.name=name;
        this.surname=surname;
    }
}
