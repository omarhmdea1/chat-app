package chatApp.Entities;

import chatApp.Utils.Role;
import chatApp.Utils.Status;
import lombok.*;

//import javax.management.relation.Role;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean isEnabled;
    private String photoURL;
    private String nikeName;
    private Date dateOfBirth;
    private String bio;
    private Role role = Role.GUEST;
    private Status status = Status.OFFLINE;
    private boolean muted = false;


    public String getFullName(){
        return firstName + " " + lastName;
    }

}
