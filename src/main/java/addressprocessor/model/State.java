package addressprocessor.model;

import addressprocessor.dto.input.StateCoreDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String code;

    private String name;

    private String countryCode;

    public State(StateCoreDTO stateCoreDTO){
        this.code = stateCoreDTO.getCode();
        this.name = stateCoreDTO.getName();
        this.countryCode = stateCoreDTO.getCountryCode();
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
