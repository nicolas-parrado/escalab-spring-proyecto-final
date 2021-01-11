package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "delegate")
@Data
public class Delegate extends RepresentationModel<Delegate> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDelegate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "delegate", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("delegate")
    private Set<Action> actions = new HashSet<>();

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "active")
    private Boolean active;
}
