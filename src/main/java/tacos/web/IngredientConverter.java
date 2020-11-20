package tacos.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.data.IngredientRepository;
import tacos.model.Ingredient;

@Component
@RequiredArgsConstructor
public class IngredientConverter implements Converter<String, Ingredient> {

	private final IngredientRepository ingredientRepo;

	@Override
	public Ingredient convert(String source) {
		return ingredientRepo.findAll().stream()
				.filter(ingredient -> ingredient.getId().equals(source))
				.findFirst().orElse(null);
	}

}