package chatApp.Entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(CompositeKeyGroupMembers.class)
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class GroupMembers {

    @Id
    private int groupId;
    @Id
    private int userId;

    public GroupMembers(int groupId, int userId) {
        this.groupId = groupId;
        this.userId = userId;
    }

}
