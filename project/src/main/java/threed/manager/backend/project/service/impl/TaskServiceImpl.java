package threed.manager.backend.project.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import threed.manager.backend.project.domain.enumerations.DeliveryStatus;
import threed.manager.backend.project.domain.enumerations.MessageMetadata;
import threed.manager.backend.project.domain.models.*;
import threed.manager.backend.project.domain.value_objects.AppUser;
import threed.manager.backend.project.service.TaskService;
import threed.manager.backend.project.xport.rest.ClientRestClient;
import threed.manager.backend.project.xport.rest.FreelancerRestClient;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static threed.manager.backend.project.config.Constants.userRootPath;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final FreelancerRestClient freelancerRestClient;
    private final ClientRestClient clientRestClient;
    @Override
    public Task addNewTaskToProject(Project p, String taskTitle) {
        Task task=p.createNewTask(taskTitle);
        //create a new folder for the task
        String path=userRootPath +p.getId().getId()+"_"+p.getName().replace(" ","_")+"/"+task.getId().getId()+"_"+task.getTitle().replace(" ","_");
        File f=new File(path);
        f.mkdir();
        task.updateFolderLocation(path);
        return task;
    }

    @Override
    public Task addMessageToTask(Task task, String email, String role, String content, MultipartFile[] messageAttachments) {
        AppUser appuser=null;
        if(role.equals("CLIENT")){
            appuser=clientRestClient.findClient(email);
        }else{
            appuser=freelancerRestClient.findFreelancer(email);
        }
        Message message=Message.build(task,appuser, content);

        //create folder for message, upload messageAttachments
        //save the attachments
        if(messageAttachments!=null){
            Arrays.stream(messageAttachments).forEach(attachment->{
                MessageAttachment messageAttachment= null;
                try {
                    messageAttachment = MessageAttachment.saveAttachment(attachment, task.getFolderLocation()+"/"+message.getId().getId()+"_"+attachment.getOriginalFilename().replace(" ","_"),message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                message.addAttachment(messageAttachment);
            });

        }

        task.addMessage(message);

        return task;
    }

    @Override
    public Task addDeliveryToTask(Task task, String email, String role, String content, MultipartFile[] deliveryAttachments) {
        AppUser appuser=null;
        if(role.equals("CLIENT")){
            appuser=clientRestClient.findClient(email);
        }else{
            appuser=freelancerRestClient.findFreelancer(email);
        }
        Delivery delivery=Delivery.build(task,content);
        Message message=Message.build(task,appuser, content, MessageMetadata.DELIVERED);
        //create folder for delivery, upload deliveryAttachments
        //save the attachments
        if(deliveryAttachments!=null){
            Arrays.stream(deliveryAttachments).forEach(attachment->{
                DeliveryAttachment deliveryAttachment= null;
                try {
                    deliveryAttachment = DeliveryAttachment.saveAttachment(attachment, task.getFolderLocation()+"/"+delivery.getId().getId()+"_"+attachment.getOriginalFilename().replace(" ","_"),delivery);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                delivery.addAttachment(deliveryAttachment);
            });

        }

        task.addMessage(message);
        task.addDelivery(delivery);

        return task;
    }

    @Override
    public Task provideFeedbackForDelivery(Task task, String deliveryId, String email, String role, String feedback, boolean status) {
        AppUser appuser=null;
        if(role.equals("CLIENT")){
            appuser=clientRestClient.findClient(email);
        }else{
            appuser=freelancerRestClient.findFreelancer(email);
        }

        Delivery delivery=task.getDeliveries().stream().filter(x->x.getId().getId().equals(deliveryId)).findFirst().get();
        delivery.changeStatus(status? DeliveryStatus.ACCEPTED:DeliveryStatus.DECLINED);
        Message message=Message.build(task,appuser,feedback,status?MessageMetadata.ACCEPTED:MessageMetadata.REVISION);
        task.addMessage(message);
        return task;
    }
}
