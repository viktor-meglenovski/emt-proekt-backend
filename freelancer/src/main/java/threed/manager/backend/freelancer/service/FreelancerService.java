package threed.manager.backend.freelancer.service;

import threed.manager.backend.freelancer.domain.models.Freelancer;

import java.util.List;

public interface FreelancerService {
    List<Freelancer> findAll();
    Freelancer findByEmail(String email);
    Freelancer rateByEmail(String email, int grade);
    Freelancer addExternalLinkToFreelancer(String email, String externalLinkName, String link);
    Freelancer removeExternalLinkFromFreelancer(String email, String externalLinkName);
    Freelancer addNewFreelancer(String email, String name, String surname);
    Freelancer editFreelancer(String email, String name, String surname);
}
