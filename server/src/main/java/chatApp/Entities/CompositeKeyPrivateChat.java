package chatApp.Entities;

import javax.persistence.Id;
import java.io.Serializable;

public class CompositeKeyPrivateChat implements Serializable {

    @Id
    private int senderUser;
    @Id
    private int receiverUser;
    @Id
    private int message;
}
