package chatApp.Entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String content;

    private LocalDateTime dateTime;

    public int compareTo(Message other) {
        return this.getDateTime().compareTo(other.getDateTime());
    }
}
