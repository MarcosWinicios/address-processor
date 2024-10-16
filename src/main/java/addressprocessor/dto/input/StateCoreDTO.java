package addressprocessor.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateCoreDTO {

    private String id;
    private String name;
    private String code;
    private String countryCode;


    public StateCoreDTO(String[] stateCoreLine){
//        this.id = stateCoreLine[0];
        this.code = stateCoreLine[1];
        this.name = stateCoreLine[2];
        this.countryCode = stateCoreLine[3];
    }

    @Override
    public String toString() {
        return "StateCoreDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
