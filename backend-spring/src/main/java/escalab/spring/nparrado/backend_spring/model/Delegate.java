package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "delegate")
@Data
public class Delegate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDelegate;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "active")
    private Boolean active;
}
