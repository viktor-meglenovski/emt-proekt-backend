package threed.manager.backend.sharedkernel.value_objects;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
public class Rating {
    @Transient
    private final List<Integer> grades;
    private final double rating;
    public Rating(){
        this.grades=new ArrayList<>();
        this.rating=0;
    }
    public Rating(List<Integer> grades) {
        this.grades = grades;
        this.rating=this.calculateRating();
    }

    public double calculateRating(){
        return this.grades.stream().mapToDouble(x->x).sum()/grades.size();
    }
}
