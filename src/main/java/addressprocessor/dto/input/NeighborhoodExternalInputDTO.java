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


//    public NeighborhoodExternalInputDTO(String[] line) {
//        this.name = line[4];
//        this.externalCode = line[3];
//        this.stateExternalCode = Integer.parseInt(line[1]);
//        this.cityExternalCode = Integer.parseInt(line[2]);
//        this.countryCode = line[8];
//
//    }
    
    public NeighborhoodExternalInputDTO(String[] line) {
        this.name = line[3];
        this.externalCode = line[2];
        this.stateExternalCode = Integer.parseInt(line[0]);
        this.cityExternalCode = Integer.parseInt(line[1]);
        this.countryCode = line[4];

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
                '}';
    }
}
