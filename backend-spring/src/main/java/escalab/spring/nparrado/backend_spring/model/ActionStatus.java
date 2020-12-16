package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "action_status")
@Data
public class ActionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idActionStatus;

    @Column(name = "name")
    private String name;
}
