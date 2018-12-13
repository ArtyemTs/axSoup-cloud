package axsoup.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import axsoup.Order;
import axsoup.Soup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;


    @Repository
    public class JdbcOrderRepository implements OrderRepository {
        private SimpleJdbcInsert orderInserter;
        private SimpleJdbcInsert orderSoupInserter;
        private ObjectMapper objectMapper;
        @Autowired
        public JdbcOrderRepository(JdbcTemplate jdbc) {
            this.orderInserter = new SimpleJdbcInsert(jdbc)
                    .withTableName("Soup_Order")
                    .usingGeneratedKeyColumns("id");
            this.orderSoupInserter = new SimpleJdbcInsert(jdbc)
                    .withTableName("Soup_Order_Tacos");
            this.objectMapper = new ObjectMapper();
        }
        @Override
        public Order save(Order order) {
            order.setPlacedAt(new Date());
            long orderId = saveOrderDetails(order);
            order.setId(orderId);
            List<Soup> soups = order.getSoups();
            for (Soup soup : soups) {
                saveSoupToOrder(soup, orderId);
            }
            return order;
        }
        private long saveOrderDetails(Order order) {
            @SuppressWarnings("unchecked")
            Map<String, Object> values =
                    objectMapper.convertValue(order, Map.class);
            values.put("placedAt", order.getPlacedAt());
            long orderId =
                    orderInserter
                            .executeAndReturnKey(values)
                            .longValue();
            return orderId;
        }
        private void saveSoupToOrder(Soup soup, long orderId) {
            Map<String, Object> values = new HashMap<>();
            values.put("soupOrder", orderId);
            values.put("soup", soup.getId());
            orderSoupInserter.execute(values);
        }
}
