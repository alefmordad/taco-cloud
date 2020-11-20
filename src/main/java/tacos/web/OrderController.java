package tacos.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacos.data.OrderRepository;
import tacos.model.Order;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
@SessionAttributes("order")
public class OrderController {

	private final OrderRepository orderRepo;

	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}

	// this order parameter is both stored in session, and updated by client (th:object of form)
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
		if (errors.hasErrors())
			return "orderForm";
		orderRepo.save(order);
		// session is reset
		sessionStatus.setComplete();
		return "redirect:/";
	}

}
