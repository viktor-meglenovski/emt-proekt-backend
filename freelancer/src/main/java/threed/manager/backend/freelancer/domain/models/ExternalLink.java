package threed.manager.backend.freelancer.domain.models;

import lombok.Getter;
import threed.manager.backend.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Entity
public class ExternalLink extends AbstractEntity<ExternalLinkId> {

    @ManyToOne
    private ExternalLinkName name;
    private String link;
    @ManyToOne
    private Freelancer freelancer;

    protected ExternalLink(){
        super(ExternalLinkId.randomId(ExternalLinkId.class));
    }
    protected ExternalLink(ExternalLinkName name, String link, Freelancer freelancer){
        super(ExternalLinkId.randomId(ExternalLinkId.class));
        this.name=name;
        this.link=link;
        this.freelancer=freelancer;
    }
}
