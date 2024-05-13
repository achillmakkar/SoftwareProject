package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * This class declares the Database accesses.
 * See also the Spring SQL example on the Moodle page of the course!
 * -
 * Defining Query Methods:
 * <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.details">...</a>
 * -
 * Supported query method subject keywords:
 * <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#appendix.query.method.subject">...</a>
 * -
 * Supported query method predicate keywords and modifiers:
 * <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#appendix.query.method.predicate">...</a>
 */
public interface HotelRepository  extends PagingAndSortingRepository<Hotel, Integer> {
  List<Hotel> findAllByOrderByIdAsc();

  @Query(value = "SELECT o FROM Hotel o ORDER BY id")
  Page<Hotel> findAll(Pageable pageable);

  List<Hotel> findByName(String lastName);

  List<Hotel> findAllById(int id, Pageable pageable);

  // Codeanfang_Achill_12.05.2024_ZIP_Name_HotelSortieren
  @Query("SELECT h FROM Hotel h ORDER BY zip ASC, name ASC")
  Page<Hotel> findAllByOrderByZipAscNameAsc(Pageable pageable);
  // Codeende_Achill_12.05.2024_ZIP_Name_HotelSortieren

}
