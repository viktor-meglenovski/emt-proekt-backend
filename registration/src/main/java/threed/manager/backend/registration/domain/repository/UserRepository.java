package threed.manager.backend.registration.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import threed.manager.backend.registration.domain.AppUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser,String> {
    Optional<AppUser> findByEmail(String email);
}
