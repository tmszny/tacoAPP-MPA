package tms.cloud.data;

import org.springframework.data.repository.CrudRepository;
import tms.cloud.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
