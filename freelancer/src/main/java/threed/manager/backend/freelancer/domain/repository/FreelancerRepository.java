package threed.manager.backend.freelancer.domain.repository;

import org.hibernate.event.spi.FlushEntityEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import threed.manager.backend.freelancer.domain.models.Freelancer;

import java.util.List;

public interface FreelancerRepository extends JpaRepository<Freelancer,String> {
    List<Freelancer> getAllByRatingGreaterThanEqual(Double rating);
    Freelancer findByEmail(String email);
}
