package axsoup.data;

import org.springframework.data.repository.CrudRepository;
import axsoup.Ingredient;
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}

