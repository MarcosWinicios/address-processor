package addressprocessor.tests;

import java.util.List;

import addressprocessor.dto.input.NeighborhoodExternalInputDTO;
import addressprocessor.dto.input.NeighborhoodInternalDTO;
import addressprocessor.model.Zipcode;
import addressprocessor.service.NeighborhoodService;
import addressprocessor.service.ZipcodeService;

public class RelateNeighborhoodWithZipCode {
	
	private static final int MEMORY_COUNT = 20000;

    public static void main(String[] args) {

        System.out.println("Lendo arquivo com bairros INTERNOS\n");
        NeighborhoodService neighborhoodService = new NeighborhoodService(MEMORY_COUNT);

        String neighborhoodInternalFileName = "new_neighborhood_relation.csv";

        List<String[]> neighborhoodInternalLines = neighborhoodService.readCsvFile(neighborhoodInternalFileName);
        List<NeighborhoodInternalDTO> neighborhoodInternalDTOList = neighborhoodService.csvToNeighborhoodInternalDTO(neighborhoodInternalLines);

        neighborhoodInternalLines.clear();
        neighborhoodInternalLines = null;
        System.gc();

        System.out.println("neighborhoodInternalDTOList: " + neighborhoodInternalDTOList.size());

//        cityService.printCityExternalObj(cityExternalInputDTOList);

        System.out.println("\n_______________________________________________\n");
        System.out.println("Lendo arquivo com os bairros EXTERNOS\n");
        String neighborhoodName = "input_tb_neighborhood_external.csv";

        var neighborhoodLines = neighborhoodService.readCsvFile(neighborhoodName);
        
        System.out.println("Linhas dos bairros EXTERNOS: " + neighborhoodLines.size());
        
        List<NeighborhoodExternalInputDTO> neighborhoodExternalInputDTOList = neighborhoodService.csvToNeighborhoodExternalObject(neighborhoodLines);
        neighborhoodLines.clear();
        neighborhoodLines = null;
        System.gc();

        System.out.println("neighborhoodExternalInputDTO: " + neighborhoodExternalInputDTOList.size());

//        neighborhoodService.printNeighborhoodExternalInputDTOObj(neighborhoodExternalInputDTOList);


        //--------------------


        ZipcodeService zipcodeService = new ZipcodeService(MEMORY_COUNT);
        List<Zipcode> zipCodeList = zipcodeService.relateNeighborhoodToZipcode(neighborhoodInternalDTOList, neighborhoodExternalInputDTOList);

        System.gc();
        System.out.println("zipcodeListSize: " + zipCodeList.size());

//        neighborhoodService.printNeighborhoodList(neighborhoodList);


        zipcodeService.generateZipcodeCsvFile(zipCodeList);

        neighborhoodService.generateNeighborhoodExternalListCSVFile(zipcodeService.getExternalNeighborhoodNotFoundList(), "new_output_zipcodeNotFoundList");
        System.out.println("FIM");
    }
}
