package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.constants.Category;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.converter.CategoryConverter;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.HotelRepository;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Service forhandling the hotel data
 * This class is able to read either from a csv file or reading from an H2 database.
 * Also is this class a middleman between the "outer world" and the storing of the data.
 * As in this class shown it can read data from a csv file or an H2 database. So the
 * programmer of the business logic has not to think about what to use. This class forwards
 * the reading and storing to the correct medium. In this wasy it is easy to switch from an
 * H2 database to MsSQL or MyDWL, MariaDB or other.
 * During development you may mock the database and return static fiexed data. so you can
 * continue writing on the GUI and business logic and be independent from the database
 * development-
 */
@Service
public class HotelService {

  public static final int GROUP_STAR_MIN = 2;

  private final HotelRepository hotelRepository;


  @Autowired
  public HotelService(final HotelRepository hotelRepository) {
      this.hotelRepository = hotelRepository;
  }

  /**
   * retrieve a hotel by id
   *
   * @param id of the hotel to find
   * @return Instance of Hotel with the corresponding id
   */
  public Hotel findById(final int id) {
    // Accesses the repository i.e. the database
    // Maybe isPresent() should here be used for safety reasons
    return hotelRepository.findById(id).get();
  }

  public List<Hotel> findAllOrderedById() {
    // Return the hotels currently loaded
    return hotelRepository.findAllByOrderByIdAsc();
  }

  public Page<Hotel> findAllOrderedById(final Pageable pageRequest) {
    // Return the hotels currently loaded
    // long version for debugging reasons
    Page<Hotel> allHotelsById = hotelRepository.findAll(pageRequest);
    return allHotelsById;

  }

  /**
   * Create a string for the hotel category
   * Category * and ** are combined together
   *
   * @param category Category to get the Star index of
   * @return String representing the category group
   */
  private static String getStarIndex(final Category category) {
    String index;

    if (category.getStarAsNumber() <= GROUP_STAR_MIN) {
      // category one and two are combined to "* & **"
      index = Category.TWO + " & " + Category.ONE;
    } else {
      index = category.getStars();
    }

    return index;
  }

  public Page<Hotel> getHotelsOrderedByZipAndName(Pageable pageable) {
    return hotelRepository.findAllByOrderByZipAscNameAsc(pageable);
  }


  /**
   * Save the data of a new Hotel
   *
   * @param newHotel Hotel to save
   * @return true if saving is a success
   */
  public boolean save(final Hotel newHotel) {
    hotelRepository.save(newHotel);
    exportDatabase();
    return true;
  }

  public void addHotel(Hotel hotel) {
    hotelRepository.save(hotel);
  }

  public void update(final Hotel alteredHotel) {

    // Data of the original one has to be altered with the new ones.
    Hotel oldHotel = findById(alteredHotel.getId());
    oldHotel.updateWith(alteredHotel);
    // update is the same as save! as long as the id is the same!!!!!!!!
    hotelRepository.save(oldHotel);

    // Export the database as SQL file
    exportDatabase();


  }

  /**
   * Dump the data as SQL file
   */
  private void exportDatabase() {
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
    Date date = new Date();

    String sql = "";
    // Iterate over all hotels and create an SQL String
    // todo: concatenating Strings like this is very unperformant and should be changed!!!!!!!
    for (Hotel hotel : hotelRepository.findAll()) {
      // +=  with a string in a loop is not the best solution
      sql += "INSERT INTO HOTEL VALUES (";
      sql += "" + hotel.getId() + ", ";
      // The category is stored in the database as an integer, so the textual
      // representation with a string has to be converted to an integer value
      // Therefore we use a converter
      sql += "" + new CategoryConverter().convertToDatabaseColumn(hotel.getCategory()) + ", ";
      sql += "'" + hotel.getName() + "', ";
      sql += "'" + hotel.getOwner() + "', ";
      sql += "'" + hotel.getContact() + "', ";
      sql += "'" + hotel.getAddress() + "', ";
      sql += "'" + hotel.getCity() + "', ";
      sql += "'" + hotel.getCityCode() + "', ";
      sql += "'" + hotel.getPhone() + "', ";
      sql += "" + hotel.getNoRooms() + ", ";
      sql += "" + hotel.getNoBeds() + ");\n";
    }

    // Write the SQL string to the file system
    BufferedWriter writer = null;
    try {
      String fileName = "hotel" + dateFormat.format(date) + ".sql";
      writer = new BufferedWriter(new FileWriter(fileName));
      writer.write(sql);
      writer.close();

    } catch (IOException exc) {
      exc.printStackTrace();
    }
  }


}
