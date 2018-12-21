package axsoup.data;

import axsoup.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import axsoup.Order;

import java.sql.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByUserOrderByPlacedAtDesc(User user);

//    List<Order> findByDeliveryZip(String deliveryZip);
//
//    List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(
//            String deliveryZip, Date startDate, Date endDate);
//
//    List<Order> findByDeliveryToAndDeliveryCityAllIgnoresCase(
//            String deliveryTo, String deliveryCity);
//
//// to order by the deliveryTo
//    List<Order> findByDeliveryCityOrderByDeliveryTo(String city);
//
////    @Query("Order o where o.deliveryCity='Moskow'")
////    List<Order> readOrdersDeliveredInSeattle();
//

}
