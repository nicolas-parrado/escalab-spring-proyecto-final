package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attachment")
@Data
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAttachment;

    @ManyToOne
    @JoinColumn(name = "id_action")
    private Action action;

    @ManyToOne
    @JoinColumn(name = "id_reference")
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
