package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attachment")
@Data
public class Attachment extends RepresentationModel<Attachment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAttachment;

    @ManyToOne
    @JoinColumn(name = "id_action")
    @JsonIgnoreProperties("attachments")
    private Action action;

    @ManyToOne
    @JoinColumn(name = "id_reference")
    @JsonIgnoreProperties("attachments")
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
