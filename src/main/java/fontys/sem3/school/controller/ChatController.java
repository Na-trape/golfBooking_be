package fontys.sem3.school.controller;

import fontys.sem3.school.domain.NotificationMessage;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/chatroom")
    public NotificationMessage send(NotificationMessage message) {
        return message;
    }
}
