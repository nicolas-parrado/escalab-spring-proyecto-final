package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "topic")
@Data
public class Topic extends RepresentationModel<Topic> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTopic;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
    @JsonIgnore
    private Set<Thought> thoughts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
    @JsonIgnore
    private Set<Someday> somedays;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
    @JsonIgnore
    private Set<Project> projects;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
    @JsonIgnore
    private Set<Action> actions;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "font_color")
    private String fontColor;

    @Column(name = "background_color")
    private String backgroundColor;
}
