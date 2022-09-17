package threed.manager.backend.client.xport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import threed.manager.backend.client.domain.models.Client;
import threed.manager.backend.client.service.ClientService;
import threed.manager.backend.sharedkernel.security.JwtValidator;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ClientRestController {
    private final ClientService clientService;

    //find all
    @GetMapping("/findAll")
    public List<Client> findAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        JwtValidator.validateToken(token);
        return clientService.findAll();
    }

    //find by email
    @GetMapping("/findByEmail")
    public Client findByEmail(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                              @RequestParam String email){
        JwtValidator.validateToken(token);
        return clientService.findByEmail(email);
    }

    @GetMapping("/findByEmailApi/{email}")
    public Client findByEmailApi(@PathVariable String email){
        return clientService.findByEmail(email);
    }

    //find by token
    @GetMapping("/findByToken")
    public Client findByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        Jws<Claims> jws= JwtValidator.validateToken(token);
        return clientService.findByEmail(jws.getBody().get("email").toString());
    }

    //rate by email
    @PostMapping("/rate")
    public Client rateClient(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                             @RequestParam String email,
                             @RequestParam int grade){
        JwtValidator.validateToken(token);
        clientService.rateByEmail(email,grade);
        return clientService.findByEmail(email);
        //not sure ova da se proveri ubo
    }

    @PostMapping("/changeCompany")
    public Client changeCompany(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                @RequestParam String newCompany){
        Jws<Claims> jws=JwtValidator.validateToken(token);
        return clientService.editClientCompanyName(jws.getBody().get("email").toString(),newCompany);
    }
}

