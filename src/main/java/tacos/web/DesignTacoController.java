package tacos.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.data.IngredientRepository;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;
import tacos.model.Taco;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

// model attributes would accessible in th ${}
@Slf4j
@Controller
@RequestMapping("/design")
@RequiredArgsConstructor
public class DesignTacoController {

	private final IngredientRepository ingredientRepo;

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
		List<Ingredient> ingredients = ingredientRepo.findAll();
		Map<Type, List<Ingredient>> groupedByType = ingredients.stream().collect(groupingBy(Ingredient::getType));
		groupedByType.keySet().forEach(type -> model.addAttribute(type.toString().toLowerCase(), groupedByType.get(type)));
		model.addAttribute("taco", taco);
	}

}
