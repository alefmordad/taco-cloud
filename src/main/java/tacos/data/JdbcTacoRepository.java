package tacos.data;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tacos.model.Ingredient;
import tacos.model.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import static java.util.Arrays.asList;

@Repository
@RequiredArgsConstructor
public class JdbcTacoRepository implements TacoRepository {

	private final JdbcTemplate jdbc;

	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		if (!CollectionUtils.isEmpty(taco.getIngredients()))
			taco.getIngredients().forEach(ingredient -> saveIngredientToTaco(ingredient, tacoId));
		return taco;
	}

	private long saveTacoInfo(Taco taco) {
		// jdbc update method does not return generated id
		// so in case of need to get saved entity id, this is the way:
		taco.setCreatedAt(new Date());
		PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(
				"insert into Taco (name, createdAt) values (?, ?)", Types.VARCHAR, Types.TIMESTAMP);
		pscFactory.setReturnGeneratedKeys(true);
		PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(
				asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(psc, keyHolder);
		return keyHolder.getKey().longValue();
	}

	private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
		jdbc.update("insert into Taco_Ingredients (taco, ingredient) values (?, ?)", tacoId, ingredient.getId());
	}

}
