package addressprocessor.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityCoreDTO {

    private String name;
    private Integer stateExternalId;
    private String countryCode;

    public CityCoreDTO(String[] lines) {
        this.name = lines[0];
        this.stateExternalId = Integer.parseInt(lines[1]);
        this.countryCode = lines[2];
    }

    @Override
    public String toString() {
        return "CityCoreDTO{" +
                "name='" + name + '\'' +
                ", stateExternalId=" + stateExternalId +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
