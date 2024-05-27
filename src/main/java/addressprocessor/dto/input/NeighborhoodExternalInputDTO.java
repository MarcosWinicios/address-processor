package addressprocessor.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeighborhoodExternalInputDTO {

    //TZ_LOCK,DIM1,DIM2,DIM3,NOMBRE,CODIGOPOSTAL,ASENTAMIENTO,CIUDAD,CODIGOPAIS,ZONAFISCAL,MIN_NOMBRE
    private Integer id;
    private String name;
    private String externalCode;
    private Integer stateExternalCode;
    private Integer cityExternalCode;
    private String countryCode;
    private String zipcode;


    public NeighborhoodExternalInputDTO(String[] line) {
        this.name = line[4];
        this.externalCode = line[3];
        this.stateExternalCode = Integer.parseInt(line[1]);
        this.cityExternalCode = Integer.parseInt(line[2]);
        this.countryCode = line[8];
        this.zipcode = line[5];
    }
    
//    public NeighborhoodExternalInputDTO(String[] line) {
//        this.name = line[3];
//        this.externalCode = line[2];
//        this.stateExternalCode = Integer.parseInt(line[0]);
//        this.cityExternalCode = Integer.parseInt(line[1]);
//        this.countryCode = line[4];
//
//    }



    public String formatString(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Use regex to check if the string contains a comma
        if (input.contains(",")) {
            // Use regex to find the parts before and after the comma
            String regex = "^(.*),\\s*(\\w+)$";
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
            java.util.regex.Matcher matcher = pattern.matcher(input);

            if (!matcher.find()) {
                throw new IllegalArgumentException("Input string must be in the format 'PALAVRA, AB'");
            }

            // Extract the parts
            String firstPart = matcher.group(1).trim();
            String secondPart = matcher.group(2).trim();

            // Format the first part
            firstPart = capitalizeWords(firstPart);

            // Ensure the second part is uppercase
            secondPart = secondPart.toUpperCase();

            return firstPart + ", " + secondPart;
        } else {
            // Capitalize the first letter of each word
            return capitalizeWords(input);
        }
    }

    private String capitalizeWords(String str) {
        String[] words = str.toLowerCase().split("\\s+");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                capitalized.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }

        return capitalized.toString().trim();
    }


    @Override
    public String toString() {
        return "NeighborhoodExternalInputDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", externalCode='" + externalCode + '\'' +
                ", stateExternalCode=" + stateExternalCode +
                ", cityExternalCode=" + cityExternalCode +
                ", countryCode='" + countryCode + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
