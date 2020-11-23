package tacos.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tacos.model.Order;

import java.util.Date;
import java.util.List;

@Repository
public interface JpaOrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByDeliveryZip(String deliveryZip);

	List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startedDate, Date endDate);

	List<Order> getAllByDeliveryNameAndDeliveryCityAllIgnoreCase(String deliveryName, String deliveryCity);

	List<Order> getAllShitsByDeliveryNameOrderByDeliveryStateDesc(String deliveryName);

	@Query("select order from Order order where order.deliveryCity='Seattle'")
	List<Order> readOrdersDeliveredInSeattle();

//  another example of @Query which returns custom type and uses join, group by, order by in jpa, pageable
//	@Query("select new taco.model.ActorSalary(a.id, a.name, sum(ma.salary)) " +
//			" from MovieActorEntity ma " +
//			" join MovieEntity m on ma.movie.id = m.id " +
//			" join ActorEntity a on ma.actor.id = a.id " +
//			" where m.releaseYear >= :yearSince " +
//			" group by a.id " +
//			" order by sum(ma.salary) desc")
//	List<ActorSalary> findTopActorsSalaryBaseAfterYear(int yearSince, Pageable pageable);
// 	which is called like this: repo.findTopActorsSalaryBaseAfterYear(sinceReleaseYear, PageRequest.of(0, 10));

}
