package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Table(name = "delegate")
@Data
public class Delegate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDelegate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "delegate")
    private Set<Action> actions;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "active")
    private Boolean active;
}
