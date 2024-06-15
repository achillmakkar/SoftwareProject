package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.constants;

import java.util.HashMap;
import java.util.Map;

public enum Category {
  FIVE("*****", 5),
  FOUR("****", 4),
  THREE("***", 3),
  TWO("**", 2),
  ONE("*", 1);


  // Reverse-LOOKUP_STARS map for getting a day from an abbreviation
  private static final Map<String, Category> LOOKUP_STARS = new HashMap<>();


  static {
    for (Category category : Category.values()) {
      LOOKUP_STARS.put(category.getStars(), category);
    }
  }

  private final String stars;
  private final int starAsNumber;

  Category(final String stars, final int starAsNumber) {
    this.stars = stars;
    this.starAsNumber = starAsNumber;
  }

  public String getStars() {
    return stars;
  }

  public int getStarAsNumber() {
    return starAsNumber;
  }

  public static Category get(final String stars) {

    return LOOKUP_STARS.get(stars);
  }

  @Override
  public String toString() {
    return stars;
  }
}
