package tacos.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tacos.model.Order;
import tacos.model.Taco;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {

	private final SimpleJdbcInsert orderInserter;
	private final SimpleJdbcInsert orderTacoInserter;
	private final ObjectMapper objectMapper;

	@Autowired
	public JdbcOrderRepository(JdbcTemplate jdbc) {
		orderInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order")
				.usingGeneratedKeyColumns("id");
		orderTacoInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order_Tacos");
		objectMapper = new ObjectMapper();
	}

	@Override
	public Order save(Order order) {
		order.setPlacedAt(new Date());
		long orderId = saveOrderDetails(order);
		order.setId(orderId);
		order.getTacos().forEach(taco -> saveTacoToOrder(taco, orderId));
		return order;
	}

	@SuppressWarnings("unchecked")
	private long saveOrderDetails(Order order) {
		Map<String, Object> values = objectMapper.convertValue(order, Map.class);
		// because by default objectMapper puts timestamp for dates
		values.put("placedAt", order.getPlacedAt());
		return orderInserter.executeAndReturnKey(values).longValue();
	}

	private void saveTacoToOrder(Taco taco, long orderId) {
		Map<String, Object> values = new HashMap<>();
		values.put("tacoOrder", orderId);
		values.put("taco", taco.getId());
		orderTacoInserter.execute(values);
	}

}
