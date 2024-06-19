package fontys.sem3.school.controller;

import fontys.sem3.school.domain.NotificationMessage;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatController {
    @MessageMapping("/chat")
    @SendTo("/topic/chatroom")
    public NotificationMessage send(NotificationMessage message) {
        // Log the received message for debugging
        System.out.println("Received message: " + message);

        // You can add any additional processing or validation here if needed

        return message;
    }
}
