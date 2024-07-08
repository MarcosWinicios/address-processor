package addressprocessor.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateInputDTO {

    private Integer id;
    private String code;
    private String name;
    private String countryCode;
    private Integer stateExternalCode;

//    public StateInputDTO(String[] lines){
//
//        this.id = Integer.parseInt(lines[0]);
//        this.code = lines[1];
//        this.name = lines[2];
//        this.countryCode = lines[3];
//        this.stateExternalCode = Integer.parseInt(lines[4]);
//    }

    public StateInputDTO(String[] lines){
//        System.out.println(lines.toString());

        if(lines == null ){
            System.out.println("ACHEI O NULL: ");
            Arrays.stream(lines).forEach(System.out::println);
        }
        this.code = lines[5];
        this.name = lines[2];
        this.countryCode = lines[3];
        this.stateExternalCode = Integer.parseInt(lines[1]);
    }

    @Override
    public String toString() {
        return "StateInputDTO{" +
                ", id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ",stateExternalCode=" + stateExternalCode +
                '}';
    }
}
