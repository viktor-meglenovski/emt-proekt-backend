package threed.manager.backend.project.xport.rest;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import threed.manager.backend.project.domain.value_objects.AppUser;
import threed.manager.backend.sharedkernel.domain.enumerations.Role;

import java.util.HashMap;
import java.util.Map;

@Service
public class FreelancerRestClient {
    private final RestTemplate restTemplate;
    private final String serverUrl;

    public FreelancerRestClient() {
        this.serverUrl = "http://localhost:8002";
        this.restTemplate = new RestTemplate();
    }
    public AppUser findFreelancer(String email) {
        try {
            AppUser response= restTemplate.getForObject(serverUrl+"/api/freelancer/findByEmailApi/"+email,AppUser.class);
            System.out.println(response);
            return response;
        } catch (Exception e) {
            return new AppUser(Role.FREELANCER);
        }
    }
}
