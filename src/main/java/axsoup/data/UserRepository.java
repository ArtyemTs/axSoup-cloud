package axsoup.data;

import org.springframework.data.repository.CrudRepository;
import axsoup.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
