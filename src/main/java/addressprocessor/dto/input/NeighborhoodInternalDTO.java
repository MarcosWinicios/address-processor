package addressprocessor.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeighborhoodInternalDTO {

    private Integer neighborhoodId;
    private String neighborhoodName;
    private String neighborhoodExternalCode;

    private Integer cityId;
    private String cityName;
    private Integer cityExternalCode;

    private Integer stateId;
    private String stateName;
    private Integer stateExternalCode;

    private String countryCode;

    public NeighborhoodInternalDTO(String[] line){
        this.neighborhoodId = Integer.parseInt(line[0]);
        this.neighborhoodName = line[1];
        this.neighborhoodExternalCode = line[2];
        this.cityId = Integer.parseInt(line[3]);
        this.cityName = line[4];
        this.cityExternalCode = Integer.parseInt(line[5]);
        this.stateId = Integer.parseInt(line[6]);
        this.stateName = line[7];
        this.stateExternalCode = Integer.parseInt(line[8]);
        this.countryCode = line[9];
    }


}
