package addressprocessor.service;

import addressprocessor.dto.input.CityExternalInputDTO;
import addressprocessor.dto.input.NeighborhoodExternalInputDTO;
import addressprocessor.utils.CsvUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NeighborhoodService {

    public List<NeighborhoodExternalInputDTO> csvToNeighborhoodExternalObject(List<String[]> lines) {
        System.out.println("\nTransfomando dados");
        List<NeighborhoodExternalInputDTO> result = new ArrayList<>();
        for(int i = 1; i < lines.size(); i++){
            NeighborhoodExternalInputDTO obj = new NeighborhoodExternalInputDTO(lines.get(i));
            result.add(obj);
        }

        System.out.println("\nFinalizando transformação de dados");
        return result;
    }


    public List<String[]> readCsvFile(String fileName) {
        System.out.println("\nLendo arquivo");
        return CsvUtil.readCsvFile(fileName);
    }

    public void printNeighborhoodExternalInputDTOObj(List<NeighborhoodExternalInputDTO> neighborhoodExternalInputDTO){
        System.out.println("\nPrintando dados dos bairros");
        int count = 1;
        for (NeighborhoodExternalInputDTO neighborhood : neighborhoodExternalInputDTO) {
            System.out.println("[ " + count + "] = " + neighborhood.toString());
            count++;
        }
    }
}
