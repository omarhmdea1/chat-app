package chatApp.Entities;

import javax.persistence.Id;
import java.io.Serializable;

public class CompositeKeyGroupMembers implements Serializable {

    @Id
    private int groupId;
    @Id
    private int userId;
}
