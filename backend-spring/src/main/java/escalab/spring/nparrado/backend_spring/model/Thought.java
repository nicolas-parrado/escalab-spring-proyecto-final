package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "thought")
@Data
public class Thought {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idThought;

    @Column(name = "notes")
    private String notes;

    @Column(name = "processed")
    private Boolean processed;
}
