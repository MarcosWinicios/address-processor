package addressprocessor.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityCoreDTO {

    private Integer stateId;

    private String name;

    private String countryCode;

    @Override
    public String toString() {
        return "CityCoreDTO{" +
                "stateId=" + stateId +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
