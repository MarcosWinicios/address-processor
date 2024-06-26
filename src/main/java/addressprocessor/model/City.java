package addressprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Integer stateId;

    private Integer externalCityCode;

    private Integer StateExternalCode;

    private String countryCode;

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stateId=" + stateId +
                ", externalCityCode=" + externalCityCode +
                ", StateExternalCode=" + StateExternalCode +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
