package addressprocessor.tests;

import java.util.List;

import addressprocessor.dto.input.NeighborhoodExternalInputDTO;
import addressprocessor.model.Neighborhood;
import addressprocessor.service.NeighborhoodService;

public class ReadCsvFileNeighBorhoodToCompare {

    public static void main(String[] args) {
    	NeighborhoodService neighborhoodService = new NeighborhoodService();
        /*
        System.out.println("Lendo arquivo com as Bairros Internos\n");

        String neighborhoodFileName = "input_tb_neighborhood.csv";
        

        var neighborhoodLines = neighborhoodService.readCsvFile(neighborhoodFileName);
        List<Neighborhood> neighborhoodList = neighborhoodService.csvToNeighborhood(neighborhoodLines);
        neighborhoodLines.clear();
        neighborhoodLines = null;
        System.gc();

        System.out.println("neighborhoodList: " + neighborhoodList.size());

//        cityService.printCityExternalObj(cityExternalInputDTOList);
*/
        System.out.println("\n_______________________________________________\n");
        System.out.println("Lendo arquivo com os bairros Externos\n");
        String neighborhoodName = "tb_neighborhood_external.csv";

        
        var neighborhoodExternalLines = neighborhoodService.readCsvFile(neighborhoodName);
        
        System.out.println("Tamnho arquivo com os bairros Externos: " + neighborhoodExternalLines.size());

        List<NeighborhoodExternalInputDTO> neighborhoodExternalInputDTOList = neighborhoodService.csvToNeighborhoodExternalObject(neighborhoodExternalLines);
        neighborhoodExternalLines.clear();
        neighborhoodExternalLines = null;
        System.gc();

        System.out.println("neighborhoodExternalInputDTO: " + neighborhoodExternalInputDTOList.size());

        List<NeighborhoodExternalInputDTO> list = neighborhoodService.findByStateExternalId(neighborhoodExternalInputDTOList, 51);

        neighborhoodService.printNeighborhoodExternalInputDTOObj(list);


        //--------------------
//        List<Neighborhood> neighborhoodList = neighborhoodService.relateNeighborhoodToCities(cityExternalInputDTOList, neighborhoodExternalInputDTOList);
/*
        List<NeighborhoodExternalInputDTO> neighborhoodNotFoundList = neighborhoodService.compareNeighboardExternalToInternal(neighborhoodList, neighborhoodExternalInputDTOList);

        System.gc();
        System.out.println("neighborhoodNotFoundList: " + neighborhoodNotFoundList.size());

//        neighborhoodService.printNeighborhoodList(neighborhoodList);

        System.out.println("FIM");

        neighborhoodService.generateNeighborhoodExternalListCSVFile(neighborhoodNotFoundList);
        
        */
//        neighborhoodService.generateNeighborhoodListCSVFile(neighborhoodList);
    }
}
