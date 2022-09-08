package threed.manager.backend.freelancer.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import threed.manager.backend.freelancer.domain.models.ExternalLinkName;

@Repository
public interface ExternalLinkNameRepository extends JpaRepository<ExternalLinkName,String > {
}
