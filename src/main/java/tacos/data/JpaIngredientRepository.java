package tacos.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tacos.model.Ingredient;

@Repository
public interface JpaIngredientRepository extends JpaRepository<Ingredient, String> {
}
