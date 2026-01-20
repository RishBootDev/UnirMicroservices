package connections_service.events;


import lombok.Data;

@Data
public class AcceptConnectionsRequestEvent {
    private Long senderId;
    private Long receiverId;
}
