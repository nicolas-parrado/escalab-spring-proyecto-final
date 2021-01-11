package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "topic")
@Data
public class Topic extends RepresentationModel<Topic> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTopic;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("topic")
    private Set<Thought> thoughts = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("topic")
    private Set<Someday> somedays = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("topic")
    private Set<Project> projects = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("topic")
    private Set<Action> actions = new HashSet<>();

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "font_color")
    private String fontColor;

    @Column(name = "background_color")
    private String backgroundColor;
}
