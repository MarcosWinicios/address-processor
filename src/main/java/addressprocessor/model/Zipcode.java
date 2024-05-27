package addressprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Zipcode {

    private Integer id;
    private String street;
    private String zipCode;
    private Integer neighborhoodId;
    private Integer cityId;
    private Integer stateId;
    private String countryCode;

    @Override
    public String toString() {
        return "Zipcode{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", neighborhoodId=" + neighborhoodId +
                ", cityId=" + cityId +
                ", stateId=" + stateId +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
