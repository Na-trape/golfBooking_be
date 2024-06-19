package fontys.sem3.school.controller;

import fontys.sem3.school.domain.NotificationMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatControllerIntegrationTest {
    private WebSocketStompClient stompClient;

    @Value("${local.server.port}")
    private int port;

    private String WEBSOCKET_URI;
    private static final String SUBSCRIBE_QUEUE = "/topic/chatroom";
    private static final String SEND_CHAT = "/app/chat";

    @BeforeEach
    public void setup() {
        WEBSOCKET_URI = "ws://localhost:" + port + "/ws";
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    public void testChat() throws Exception {
        BlockingQueue<NotificationMessage> blockingQueue = new ArrayBlockingQueue<>(1);

        ListenableFuture<StompSession> future = stompClient.connect(WEBSOCKET_URI, new WebSocketHttpHeaders(), new StompSessionHandlerAdapter() {});
        StompSession session = future.get(5, TimeUnit.SECONDS); // Increased timeout

        session.subscribe(SUBSCRIBE_QUEUE, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return NotificationMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                System.out.println("Received message: " + o); // Debug logging
                blockingQueue.offer((NotificationMessage) o);
            }
        });

        NotificationMessage message = new NotificationMessage("user1", "user2", "Hello!", "chatroom");
        session.send(SEND_CHAT, message);

        NotificationMessage receivedMessage = blockingQueue.poll(15, TimeUnit.SECONDS); // Increased timeout
        assertNotNull(receivedMessage, "Received message should not be null");
        assertEquals("user1", receivedMessage.getFrom());
        assertEquals("user2", receivedMessage.getTo());  // Added assertion for 'to'
        assertEquals("Hello!", receivedMessage.getContent());
        assertEquals("chatroom", receivedMessage.getRoom());  // Added assertion for 'room'
    }
}
