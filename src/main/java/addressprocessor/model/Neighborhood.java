package addressprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Neighborhood {

    private Integer id;
    private String name;
    private String externalCode;
    private Integer cityId;
    
    public Neighborhood(String[] csvLine) {
    	this.name = csvLine[0];
    	this.externalCode =csvLine[1];
    	this.cityId =Integer.parseInt(csvLine[2]);
    }

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
