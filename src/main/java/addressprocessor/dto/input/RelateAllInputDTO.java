package addressprocessor.dto.input;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class RelateAllInputDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // d1 (State)
    private String d1StateId;
    private String d1StateName;
    private String d1StateCode;
    private String d1CountryCode;

    // d2 (City)
    private String d2StateId;
    private String d2CityId;
    private String d2CityName;
    private String d2CountryCode;

    // d3 (Neighborhood)
    private String d3StateId;
    private String d3CityId;
    private String d3NeighborhoodId;
    private String d3NeighborhoodName;
    private String d3NeighborhoodZipCode;
    private String d3CountryCode;

    public boolean compareData(){
        boolean compareStateId =   this.getD1StateId().equals(this.d1StateId) && this.d2StateId.equals(this.d3StateId);
        boolean compareCityId = this.d2CityId.equals(this.d3CityId);
        boolean compareCountryCode = this.d1CountryCode.equals(this.d2CountryCode) && this.d2CountryCode.equals(this.d3CountryCode);

//        System.out.printf("stateId: %s, cityId: %s, countryCode: %s\n", compareStateId, compareCityId, compareCountryCode);
        return compareCityId == compareStateId == compareCountryCode;
    }

    public RelateAllInputDTO (String[] line){
        this.d1StateId = line[0];
        this.d1StateName = line[1];
        this.d1StateCode = line[2];
        this.d1CountryCode = line[3];
        this.d2StateId = line[4];
        this.d2CityId = line[5];
        this.d2CityName = line[6];
        this.d2CountryCode = line[7];
        this.d3StateId = line[8];
        this.d3CityId = line[9];
        this.d3NeighborhoodId = line[10];
        this.d3NeighborhoodName = line[11];
        this.d3NeighborhoodZipCode = line[12];
        this.d3CountryCode = line[13];
    }

    @Override
    public String toString() {
        return "RelateAllInputDTO{" +
                "d1StateId='" + d1StateId + '\'' +
                ", d1StateName='" + d1StateName + '\'' +
                ", d1StateCode='" + d1StateCode + '\'' +
                ", d1CountryCode='" + d1CountryCode + '\'' +
                ", d2StateId='" + d2StateId + '\'' +
                ", d2CityId='" + d2CityId + '\'' +
                ", d2CityName='" + d2CityName + '\'' +
                ", d2CountryCode='" + d2CountryCode + '\'' +
                ", d3StateId='" + d3StateId + '\'' +
                ", d3CityId='" + d3CityId + '\'' +
                ", d3NeighborhoodId='" + d3NeighborhoodId + '\'' +
                ", d3NeighborhoodName='" + d3NeighborhoodName + '\'' +
                ", d3NeighborhoodZipCode='" + d3NeighborhoodZipCode + '\'' +
                ", d3CountryCode='" + d3CountryCode + '\'' +
                '}';
    }
}
