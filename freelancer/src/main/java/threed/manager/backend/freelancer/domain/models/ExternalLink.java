package threed.manager.backend.freelancer.domain.models;

import lombok.Getter;
import threed.manager.backend.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Entity
public class ExternalLink extends AbstractEntity<ExternalLinkId> {

    @ManyToOne
    private Freelancer freelancer;
    @ManyToOne
    private ExternalLinkName name;
    private String link;

    protected ExternalLink(){
        super(ExternalLinkId.randomId(ExternalLinkId.class));
    }
    protected ExternalLink(ExternalLinkName name, String link){
        super(ExternalLinkId.randomId(ExternalLinkId.class));
        this.name=name;
        this.link=link;
    }
}
