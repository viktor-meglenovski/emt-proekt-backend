package threed.manager.backend.security.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import threed.manager.backend.security.domain.AppUser;
import threed.manager.backend.security.domain.exceptions.InvalidUsernameOrPasswordException;
import threed.manager.backend.security.domain.exceptions.PasswordsDoNotMatchException;
import threed.manager.backend.security.domain.exceptions.UserDoesNotExistException;
import threed.manager.backend.security.domain.exceptions.UsernameAlreadyExistsException;
import threed.manager.backend.security.domain.repository.UserRepository;
import threed.manager.backend.security.service.UserService;
import threed.manager.backend.sharedkernel.domain.enumerations.Role;
import threed.manager.backend.sharedkernel.domain.events.account.ClientAccountEdited;
import threed.manager.backend.sharedkernel.domain.events.account.ClientNewAccountCreated;
import threed.manager.backend.sharedkernel.domain.events.account.FreelancerAccountEdited;
import threed.manager.backend.sharedkernel.domain.events.account.FreelancerNewAccountCreated;
import threed.manager.backend.sharedkernel.infra.DomainEventPublisher;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DomainEventPublisher domainEventPublisher;
    @Override
    public AppUser registerNewUser(String email, String name, String surname, String password, String repeatPassword, String role) throws UsernameAlreadyExistsException {
        if (email==null || email.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByEmail(email).isPresent())
            throw new UsernameAlreadyExistsException(email);

        AppUser user = new AppUser(email,name,surname, passwordEncoder.encode(password), Role.valueOf(role));
        userRepository.save(user);

        if(role.equals("CLIENT")){
            domainEventPublisher.publish(new ClientNewAccountCreated(email,name,surname));
        }else if(role.equals("FREELANCER")){
            domainEventPublisher.publish(new FreelancerNewAccountCreated(email,name,surname));
        }

        return user;
    }

    @Override
    public AppUser editAccount(String email, String name, String surname) {
        AppUser accountToUpdate=findByEmail(email);
        accountToUpdate.updateNameAndSurname(name,surname);
        userRepository.save(accountToUpdate);

        if(accountToUpdate.getRole().name().equals("CLIENT")){
            domainEventPublisher.publish(new ClientAccountEdited(email,name,surname));
        }else if(accountToUpdate.getRole().name().equals("FREELANCER")){
            domainEventPublisher.publish(new FreelancerAccountEdited(email,name,surname));
        }

        return accountToUpdate;
    }

    @Override
    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserDoesNotExistException::new);
    }
}
