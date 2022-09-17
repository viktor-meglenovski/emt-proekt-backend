package threed.manager.backend.project.xport.rest;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import threed.manager.backend.project.domain.value_objects.AppUser;
import threed.manager.backend.sharedkernel.domain.enumerations.Role;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientRestClient {
    private final RestTemplate restTemplate;
    private final String serverUrl;

    public ClientRestClient() {
        this.serverUrl = "http://localhost:8001";
        this.restTemplate = new RestTemplate();
    }

    public AppUser findClient(String email) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        try {
            AppUser response= restTemplate.getForObject(serverUrl+"/api/client/findByEmailApi/"+email,AppUser.class);
            System.out.println(response);
            return response;
        } catch (Exception e) {
            return new AppUser(Role.CLIENT);
        }
    }
}