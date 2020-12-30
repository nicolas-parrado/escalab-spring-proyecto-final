package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rol")
@Data
public class Rol extends RepresentationModel<Rol> {
	
	@Id
	private Integer idRol;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
}
