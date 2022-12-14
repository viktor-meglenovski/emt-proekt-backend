package threed.manager.backend.project.xport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import threed.manager.backend.project.domain.enumerations.ProjectStatusEnumeration;
import threed.manager.backend.project.domain.models.Project;
import threed.manager.backend.project.domain.models.Task;
import threed.manager.backend.project.service.ProjectService;
import threed.manager.backend.sharedkernel.domain.enumerations.Role;
import threed.manager.backend.sharedkernel.security.JwtValidator;
import threed.manager.backend.sharedkernel.security.exceptions.NotAuthorisedAccessException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/project")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class ProjectRestController {
    private final ProjectService projectService;

    //get project by ID
    @GetMapping("/{id}")
    public Project getProjectById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        Project project=projectService.findByProjectId(id);
        if(projectService.isClient(project,jws.getBody().get("email").toString())||
                projectService.isFreelancer(project,jws.getBody().get("email").toString())){
            return project;
        }else throw new NotAuthorisedAccessException();
    }

    //get projects by token user information
    @GetMapping("/myProjects")
    public List<Project> getMyProjects(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        if(jws.getBody().get("role").equals(Role.CLIENT))
            return projectService.findAllByClientEmail(jws.getBody().get("email").toString());
        else if(jws.getBody().get("role").equals(Role.FREELANCER))
            return projectService.findAllByFreelancerEmail(jws.getBody().get("email").toString());
        return new ArrayList<>();
    }

    //get projects by token user information and project status
    @GetMapping("/myProjectsByStatus")
    public List<Project> getMyProjects(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                       @RequestParam String projectStatus){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        if(jws.getBody().get("role").equals("CLIENT"))
            return projectService.findAllByClientEmailAndProjectStatus(jws.getBody().get("email").toString(),projectStatus);
        else if(jws.getBody().get("role").equals("FREELANCER"))
            return projectService.findAllByFreelancerEmailAndProjectStatus(jws.getBody().get("email").toString(),projectStatus);
        return new ArrayList<>();
    }

    @InitBinder
    public void bindingPreparation(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, orderDateEditor);
    }
    //create new project and propose it to a freelancer (for clients only)
    @PostMapping("/createProject")
    public Project createNewProject(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                    @RequestParam String name,
                                    @RequestParam String description,
                                    @RequestParam Date dueDate,
                                    @RequestParam("attachments") @Nullable MultipartFile[] attachments,
                                    @RequestParam String freelancerEmail){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        if(jws.getBody().get("role").equals("CLIENT")){
            return projectService.createNewProject(name,description,dueDate,attachments,jws.getBody().get("email").toString(),freelancerEmail);
        }else throw new NotAuthorisedAccessException();
    }

    //accept/decline the project proposal (for freelancers only)
    @PostMapping("/acceptProposal")
    public Project acceptProposal(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                  @RequestParam String projectId){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        if(jws.getBody().get("role").equals("FREELANCER")){
            Project p=projectService.findByProjectId(projectId);
            if(p.isFreelancerForProject(jws.getBody().get("email").toString())){
                return projectService.changeStatus(p,ProjectStatusEnumeration.ACCEPTED);
            }else throw new NotAuthorisedAccessException();

        }else throw new NotAuthorisedAccessException();
    }
    @PostMapping("/declineProposal")
    public Project declineProposal(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                   @RequestParam String projectId){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        Project p=projectService.findByProjectId(projectId);
        if(p.isFreelancerForProject(jws.getBody().get("email").toString()) || p.isClientForProject(jws.getBody().get("email").toString())){
            return projectService.changeStatus(p, ProjectStatusEnumeration.CANCELED);
        }
        else throw new NotAuthorisedAccessException();
    }

    @GetMapping("/downloadFile")
    public ResponseEntity downloadFile(@RequestParam String filePath) throws IOException {

        File file = new File(filePath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }
    @PostMapping("/addTask")
    public Project addNewTask(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                              @RequestParam String projectId,
                              @RequestParam String taskTitle){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        if(jws.getBody().get("role").equals("FREELANCER")){
            Project p=projectService.findByProjectId(projectId);
            if (p.isFreelancerForProject(jws.getBody().get("email").toString())){
                return projectService.addNewTask(p,taskTitle);
            }else throw new NotAuthorisedAccessException();
        }else throw new NotAuthorisedAccessException();
    }
    @PostMapping("/addMessageToTask")
    public Task addMessageToTask(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                 @RequestParam String projectId,
                                 @RequestParam String taskId,
                                 @RequestParam String content,
                                 @RequestParam("messageAttachments") @Nullable MultipartFile[] messageAttachments){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        Project p=projectService.findByProjectId(projectId);
        if (p.isFreelancerForProject(jws.getBody().get("email").toString()) || p.isClientForProject(jws.getBody().get("email").toString())){
            return projectService.addNewMessageToTask(p, taskId, jws.getBody().get("email").toString(), jws.getBody().get("role").toString(), content, messageAttachments);
        }else throw new NotAuthorisedAccessException();
    }
    @PostMapping("/addDeliveryToTask")
    public Task addDeliveryToTask(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                 @RequestParam String projectId,
                                 @RequestParam String taskId,
                                 @RequestParam String content,
                                 @RequestParam("deliveryAttachments") MultipartFile[] deliveryAttachments){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        Project p=projectService.findByProjectId(projectId);
        if (p.isFreelancerForProject(jws.getBody().get("email").toString())){
            return projectService.addNewDeliveryToTask(p, taskId, jws.getBody().get("email").toString(), jws.getBody().get("role").toString(), content, deliveryAttachments);
        }else throw new NotAuthorisedAccessException();
    }

    @PostMapping("/provideFeedbackForDelivery")
    public Task provideFeedbackForDelivery(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                  @RequestParam String projectId,
                                  @RequestParam String taskId,
                                  @RequestParam String deliveryId,
                                  @RequestParam String feedback,
                                  @RequestParam boolean accepted){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        Project p=projectService.findByProjectId(projectId);
        if (p.isClientForProject(jws.getBody().get("email").toString())){
            return projectService.provideFeedbackForDelivery(p, taskId, deliveryId, jws.getBody().get("email").toString(), jws.getBody().get("role").toString(), feedback,accepted);
        }else throw new NotAuthorisedAccessException();
    }
    @PostMapping("/finishProject")
    public Project finishProject(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                 @RequestParam String projectId){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        Project p=projectService.findByProjectId(projectId);
        if (p.isClientForProject(jws.getBody().get("email").toString())){
            return projectService.changeStatus(p,ProjectStatusEnumeration.FINISHED);
        }else throw new NotAuthorisedAccessException();
    }
    @PostMapping("/rate")
    public Project rateProject(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                               @RequestParam String projectId,
                               @RequestParam int rating){
        Jws<Claims> jws = JwtValidator.validateToken(token);
        Project p=projectService.findByProjectId(projectId);
        if(p.isClientForProject(jws.getBody().get("email").toString())){
            return projectService.addRatingFromClient(p,rating);
        }else if (p.isFreelancerForProject(jws.getBody().get("email").toString())){
            return projectService.addRatingFromFreelancer(p,rating);
        }else throw new NotAuthorisedAccessException();
    }
}
