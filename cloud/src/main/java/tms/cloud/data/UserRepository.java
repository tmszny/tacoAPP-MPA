package tms.cloud.data;

import org.springframework.data.repository.CrudRepository;
import tms.cloud.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

}
