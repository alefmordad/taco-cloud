package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;
import tacos.model.Taco;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

// model attributes would accessible in th ${}
@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

	private final List<Ingredient> ingredients = Arrays.asList(
			new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
			new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
			new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
			new Ingredient("CARN", "Carnitas", Type.PROTEIN),
			new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
			new Ingredient("LETC", "Lettuce", Type.VEGGIES),
			new Ingredient("CHED", "Cheddar", Type.CHEESE),
			new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
			new Ingredient("SLSA", "Salsa", Type.SAUCE),
			new Ingredient("SRCR", "Sour Cream", Type.SAUCE));

	@GetMapping
	public String showDesignForm(Model model) {
		initForm(model, new Taco());
		return "design";
	}

	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors, Model model) {
		if (errors.hasErrors()) {
			initForm(model, design);
			return "design";
		}
		// save the taco design
		log.info("Processing design: " + design);
		return "redirect:/orders/current";
	}

	private void initForm(Model model, Taco taco) {
		Map<Type, List<Ingredient>> groupedByType = ingredients.stream().collect(groupingBy(Ingredient::getType));
		groupedByType.keySet().forEach(type -> model.addAttribute(type.toString().toLowerCase(), groupedByType.get(type)));
		model.addAttribute("taco", taco);
	}

}
