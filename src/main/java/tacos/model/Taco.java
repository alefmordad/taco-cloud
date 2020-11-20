package tacos.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco implements Serializable {

	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min = 5, message = "Name must be at least 5 characters long")
	private String name;

	private Date createdAt;

	@ManyToMany(targetEntity = Ingredient.class)
	@NotEmpty(message = "You must choose at least 1 ingredient")
	private List<Ingredient> ingredients;

	@PrePersist
	void initCreatedAt() {
		createdAt = new Date();
	}

}
