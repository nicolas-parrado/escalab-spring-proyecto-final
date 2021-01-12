package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attachment")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idAttachment"
)
public class Attachment extends RepresentationModel<Attachment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAttachment;

    @ManyToOne
    @JoinColumn(name = "id_action")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","topic","actionStatus","context","delegate","attachments","project"})
    private Action action;

    @ManyToOne
    @JoinColumn(name = "id_reference")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","attachments","project"})
    private Reference reference;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "path")
    private String path;

    @Column(name = "size")
    private long size;
}
