package addressprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Neighborhood {

    Integer id;
    String name;
    String externalCode;
    Integer cityId;

    @Override
    public String toString() {
        return "Neighborhood{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", externalCode='" + externalCode + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
