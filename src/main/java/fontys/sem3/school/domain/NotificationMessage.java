package fontys.sem3.school.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessage {
    private String from;
    private String to;
    private String content;
    private String room;
}
