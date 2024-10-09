package chatApp.Entities;

import lombok.*;

import javax.persistence.*;

@Entity
@IdClass(CompositeKeyPrivateChat.class)
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PrivateChat {
    @Id
    private int senderUser;
    @Id
    private int receiverUser;
    @Id
    private int message;


    public PrivateChat(int senderUser, int receiverUser, int message) {
        this.senderUser = senderUser;
        this.receiverUser = receiverUser;
        this.message = message;
    }
}


