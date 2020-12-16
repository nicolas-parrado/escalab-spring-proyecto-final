package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "someday")
@Data
public class Someday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSomeday;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "tickle_date")
    private LocalDateTime tickleDate;

    @Lob
    @Column(name = "notes")
    private String notes;
}
