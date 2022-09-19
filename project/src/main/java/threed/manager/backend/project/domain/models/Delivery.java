package threed.manager.backend.project.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import threed.manager.backend.project.domain.enumerations.DeliveryStatus;
import threed.manager.backend.project.domain.models.ids.DeliveryId;
import threed.manager.backend.project.domain.models.ids.MessageId;
import threed.manager.backend.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name="deliveries")
public class Delivery extends AbstractEntity<DeliveryId> {

    @ManyToOne
    private Task task;
    private String content;
    @JsonIgnoreProperties(value = {"delivery"})
    @OneToMany(mappedBy = "delivery", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DeliveryAttachment> deliveryAttachments;
    private LocalDateTime deliveryTime;
    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    public Delivery() {
        super(DeliveryId.randomId(DeliveryId.class));
    }
    public static Delivery build(Task task, String content){
        Delivery delivery=new Delivery();
        delivery.task=task;
        delivery.content=content;
        delivery.deliveryAttachments=new HashSet<>();
        delivery.deliveryTime=LocalDateTime.now();
        delivery.deliveryStatus=DeliveryStatus.PENDING;
        return delivery;
    }
    public void changeStatus(DeliveryStatus status){
        this.deliveryStatus=status;
    }
    public void addAttachment(DeliveryAttachment deliveryAttachment){
        this.deliveryAttachments.add(deliveryAttachment);
    }
}
