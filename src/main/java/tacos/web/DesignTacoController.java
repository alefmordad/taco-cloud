package tacos.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;
import tacos.model.Order;
import tacos.model.Taco;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

// model attributes would accessible in th braces
@Slf4j
@Controller
@RequestMapping("/design")
@RequiredArgsConstructor
// this means order is saved in session, and would not be renewed before @RequestMapping call
// session is handled like this: session id would be passed to client by JSESSIONID cookie
// and it would be passed to client in request headers
@SessionAttributes("order")
public class DesignTacoController {

	private final IngredientRepository ingredientRepo;
	private final TacoRepository tacoRepo;

	// when a controller method is annotated with @ModelAttribute
	// it would be called before calling @RequestMapping methods and adds its output to Model
	// it can also take Model as parameter and add attributes to it
	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}

	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}

	@ModelAttribute
	public void initForm(Model model) {
		List<Ingredient> ingredients = ingredientRepo.findAll();
		Map<Type, List<Ingredient>> groupedByType = ingredients.stream().collect(groupingBy(Ingredient::getType));
		groupedByType.keySet().forEach(type -> model.addAttribute(type.toString().toLowerCase(), groupedByType.get(type)));
	}

	@GetMapping
	public String showDesignForm() {
		return "design";
	}

	// when a method parameter is annotated with @Valid
	// jsr-303 validations would be checked for it and possible errors would be inside an Errors type parameter
	//
	// when a method parameter is annotated with @ModelAttribute
	// it would be binding to corresponding attribute in Model
	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
		if (errors.hasErrors())
			return "design";
		Taco saved = tacoRepo.save(design);
		order.addDesign(saved);
		return "redirect:/orders/current";
	}

}
