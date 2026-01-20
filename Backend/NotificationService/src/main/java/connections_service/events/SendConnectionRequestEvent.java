package connections_service.events;


import lombok.Data;

@Data
public class SendConnectionRequestEvent {
    private Long senderId;
    private Long receiverId;
}
