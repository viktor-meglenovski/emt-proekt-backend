package threed.manager.backend.freelancer.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import threed.manager.backend.freelancer.domain.models.ExternalLink;
import threed.manager.backend.freelancer.domain.models.ExternalLinkName;
import threed.manager.backend.freelancer.domain.models.Freelancer;
import threed.manager.backend.freelancer.domain.repository.ExternalLinkNameRepository;
import threed.manager.backend.freelancer.domain.repository.FreelancerRepository;
import threed.manager.backend.freelancer.service.FreelancerService;

import java.util.List;

@Service
@AllArgsConstructor
public class FreelancerServiceImpl implements FreelancerService {
    private final FreelancerRepository freelancerRepository;
    private final ExternalLinkNameRepository externalLinkNameRepository;
    @Override
    public List<Freelancer> findAll() {
        return freelancerRepository.findAll();
    }

    @Override
    public Freelancer findByEmail(String email) {
        return freelancerRepository.findByEmail(email);
    }

    @Override
    public Freelancer rateByEmail(String email, int grade) {
        Freelancer freelancer=findByEmail(email);
        freelancer.rateFreelancer(grade);
        return freelancer;
    }

    @Override
    public Freelancer addExternalLinkToFreelancer(String email, String externalLinkName, String link) {
        Freelancer freelancer=findByEmail(email);
        ExternalLinkName extLinkName=externalLinkNameRepository.findById(externalLinkName).get();
        freelancer.addExternalLink(extLinkName,link);
        return freelancer;
    }
    @Override
    public Freelancer removeExternalLinkFromFreelancer(String email, String externalLinkName){
        Freelancer freelancer=findByEmail(email);
        ExternalLinkName extLinkName=externalLinkNameRepository.findById(externalLinkName).get();
        freelancer.removeExternalLink(extLinkName);
        return freelancer;
    }

    @Override
    public Freelancer addNewFreelancer(String email, String name, String surname) {
        Freelancer newFreelancer=new Freelancer(email,name,surname);
        freelancerRepository.save(newFreelancer);
        return newFreelancer;
    }

    @Override
    public Freelancer editFreelancer(String email, String name, String surname) {
        Freelancer freelancer=findByEmail(email);
        freelancer.editNameAndSurname(name,surname);
        freelancerRepository.save(freelancer);
        return freelancer;
    }


}
