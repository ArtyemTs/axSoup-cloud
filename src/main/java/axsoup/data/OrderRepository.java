package axsoup.data;

import axsoup.Order;

public interface OrderRepository {
    Order save(Order order);
}
