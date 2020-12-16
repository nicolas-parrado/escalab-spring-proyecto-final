package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "topic")
@Data
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTopic;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "font_color")
    private String fontColor;

    @Column(name = "background_color")
    private String backgroundColor;
}
