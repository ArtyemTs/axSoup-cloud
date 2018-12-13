package axsoup.data;

import org.springframework.data.repository.CrudRepository;
import axsoup.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
