package addressprocessor.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityExternalInputDTO {

    //city_id,city_name,stte_id,ctry_code,state_external_code,city_external_code
    private Integer id;
    private String name;
    private Integer stateId;

    private String countryCode;
    private Integer stateExternalCode;
    private Integer cityExternalCode;

    public CityExternalInputDTO(String[] lines){
        this.id = Integer.parseInt(lines[0]);
        this.name = lines[1];
        this.stateId = Integer.parseInt(lines[2]);
        this.countryCode = lines[3];
        this.stateExternalCode = Integer.parseInt(lines[4]);
        this.cityExternalCode = Integer.parseInt(lines[5]);
    }

    @Override
    public String toString() {
        return "CityExternalInputDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stateId=" + stateId +
                ", countryCode='" + countryCode + '\'' +
                ", stateExternalCode=" + stateExternalCode +
                ", cityExternalCode=" + cityExternalCode +
                '}';
    }
}
