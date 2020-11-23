package tacos.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tacos.model.Taco;

@Repository
public interface JpaTacoRepository extends JpaRepository<Taco, Long> {
}
