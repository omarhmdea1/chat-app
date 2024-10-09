package chatApp.Entities;

import lombok.*;

import javax.persistence.*;

@Entity
@IdClass(CompositeKeyGroupChats.class)
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class GroupChats {
    @Id
    private int senderUser;
    @Id
    private int groupId;
    @Id
    private int message;

    public GroupChats(int senderUser, int groupId, int message) {
        this.senderUser = senderUser;
        this.groupId = groupId;
        this.message = message;
    }
}


