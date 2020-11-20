package tacos.model;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Order {

	private Long id;

	private Date placedAt;

	private List<Taco> tacos = new ArrayList<>();

	// NotBlank means trimmed value should have a positive length
	@NotBlank(message = "Name is required")
	private String deliveryName;

	@NotBlank(message = "Street is required")
	private String deliveryStreet;

	@NotBlank(message = "City is required")
	private String deliveryCity;

	@NotBlank(message = "State is required")
	private String deliveryState;

	@NotBlank(message = "Zip is required")
	private String deliveryZip;

	@CreditCardNumber(message = "Not a valid credit card number")
	private String ccNumber;

	@Pattern(regexp = "^(0[1-9]|1[0-2])([/])([1-9][0-9])$", message = "Must be formatted MM/YY")
	private String ccExpiration;

	// Digits is used for validation String with number value
	@Digits(integer = 3, fraction = 0, message = "Invalid CVV")
	private String ccCVV;

	public void addDesign(Taco taco) {
		tacos.add(taco);
	}

}
