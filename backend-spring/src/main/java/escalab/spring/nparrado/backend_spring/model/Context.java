package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "context")
@Data
public class Context {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContext;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
