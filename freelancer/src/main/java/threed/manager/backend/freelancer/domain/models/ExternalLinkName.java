package threed.manager.backend.freelancer.domain.models;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class ExternalLinkName {
    @Id
    private String name;
    private String imageLocation;

    public ExternalLinkName(String name, String imageLocation) {
        this.name = name;
        this.imageLocation = imageLocation;
    }

    public ExternalLinkName() {
    }
}
