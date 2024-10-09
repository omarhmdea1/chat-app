package chatApp.Entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "publicGroups")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PublicGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String groupName;

    public PublicGroups(int id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

}
