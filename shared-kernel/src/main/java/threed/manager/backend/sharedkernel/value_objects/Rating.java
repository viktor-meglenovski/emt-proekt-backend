package threed.manager.backend.sharedkernel.value_objects;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
public class Rating {
    private final int rating;
    public Rating(){
        this.rating=0;
    }
    public Rating(int rating){
        this.rating=rating;
    }
    public Rating addRating(int newRating){
        return new Rating(this.rating+newRating);
    }
}
