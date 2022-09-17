package threed.manager.backend.freelancer.xport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import threed.manager.backend.freelancer.domain.models.ExternalLinkName;
import threed.manager.backend.freelancer.domain.models.Freelancer;
import threed.manager.backend.freelancer.service.ExternalLinkNameService;
import threed.manager.backend.freelancer.service.FreelancerService;
import threed.manager.backend.sharedkernel.security.JwtValidator;

import java.util.List;

@RestController
@RequestMapping("/api/freelancer")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class FreelancerRestController {

    private final FreelancerService freelancerService;

    //find all
    @GetMapping("/findAll")
    public List<Freelancer> findAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        JwtValidator.validateToken(token);
        return freelancerService.findAll();
    }

    //find by email
    @GetMapping("/findByEmail")
    public Freelancer findByEmail(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam String email){
        //JwtValidator.validateToken(token);
        return freelancerService.findByEmail(email);
    }

    @GetMapping("/findByEmailApi/{email}")
    public Freelancer findByEmailApi(@PathVariable String email){
        return freelancerService.findByEmail(email);
    }

    //find by token
    @GetMapping("/findByToken")
    public Freelancer findByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        Jws<Claims> jws= JwtValidator.validateToken(token);
        return freelancerService.findByEmail(jws.getBody().get("email").toString());
    }

    //rate by email
    @PostMapping("/rate")
    public Freelancer rateFreelancer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                     @RequestParam String email,
                                     @RequestParam int grade){
        JwtValidator.validateToken(token);
        freelancerService.rateByEmail(email,grade);
        return freelancerService.findByEmail(email);
        //not sure ova da se proveri ubo
    }

    //add external link
    @PostMapping("/addExternalLink")
    public Freelancer addExternalLinkToFreelancer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                  @RequestParam String externalLinkName,
                                                  @RequestParam String link){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        return freelancerService.addExternalLinkToFreelancer(jws.getBody().get("email").toString(),externalLinkName,link);
    }

    //remove external link
    @PostMapping("/removeExternalLink")
    public Freelancer removeExternalLinkToFreelancer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                     @RequestParam String externalLinkName){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        return freelancerService.removeExternalLinkFromFreelancer(jws.getBody().get("email").toString(),externalLinkName);
    }




}
