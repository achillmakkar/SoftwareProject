package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.constants;
// Codeanfang_Anes_22.04.2024_Nationality
public enum Nationality {

   AUSTRIA("Austria"),
    ITALY("Italy"),
    SPAIN("Spain"),
    GERMANY("Germany"),
    SWITZERLAND("Switzerland");


   private final String nationality;
    Nationality(final String nationality) {
     this.nationality = nationality;
    }
 @Override
 public String toString() {
  return nationality;
 }
}
// Codeende_Anes_22.04.2024_Nationality