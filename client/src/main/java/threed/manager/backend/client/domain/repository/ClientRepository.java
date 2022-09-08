package threed.manager.backend.client.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import threed.manager.backend.client.domain.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,String> {
    Client findByEmail(String email);
}
