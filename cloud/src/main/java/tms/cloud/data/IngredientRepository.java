package tms.cloud.data;

import org.springframework.data.repository.CrudRepository;
import tms.cloud.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
