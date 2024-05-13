package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
  @Query("SELECT u FROM User u WHERE u.username = :username")
  public User getUserByUsername(@Param("username") String username);
}
