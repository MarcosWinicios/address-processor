package addressprocessor.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateInputDTO {

    private Integer sequencial;
    private Integer Id;
    private String code;
    private String name;
    private String countryCode;

    @Override
    public String toString() {
        return "StateInputDTO{" +
                "sequencial=" + sequencial +
                ", Id=" + Id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
