package tms.cloud.data;

import org.springframework.data.repository.CrudRepository;
import tms.cloud.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
