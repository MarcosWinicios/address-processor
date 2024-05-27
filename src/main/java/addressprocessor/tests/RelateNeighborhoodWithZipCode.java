package addressprocessor.tests;

import java.util.List;

import addressprocessor.dto.input.CityExternalInputDTO;
import addressprocessor.dto.input.NeighborhoodExternalInputDTO;
import addressprocessor.model.Neighborhood;
import addressprocessor.service.CityService;
import addressprocessor.service.NeighborhoodService;

public class RelateNeighborhoodWithZipCode {
	
	private static final int MEMORY_COUNT = 20000;

    public static void main(String[] args) {

        System.out.println("Lendo arquivo com as cidades\n");

        String cityFileName = "neighborhood_relation.csv";
        CityService cityService = new CityService(MEMORY_COUNT);

        var cityLines = cityService.readCsvFile(cityFileName);
        List<CityExternalInputDTO> cityExternalInputDTOList = cityService.csvToCityExternalObject(cityLines);
        cityLines.clear();
        cityLines = null;
        System.gc();

        System.out.println("cityExternalInputDTOList: " + cityExternalInputDTOList.size());

//        cityService.printCityExternalObj(cityExternalInputDTOList);

        System.out.println("\n_______________________________________________\n");
        System.out.println("Lendo arquivo com os bairros\n");
        String neighborhoodName = "input_tb_neighborhood_external.csv";

        NeighborhoodService neighborhoodService = new NeighborhoodService(MEMORY_COUNT);
        var neighborhoodLines = neighborhoodService.readCsvFile(neighborhoodName);
        
        System.out.println("Linhas dos bairros: " + neighborhoodLines.size());
        

        
        List<NeighborhoodExternalInputDTO> neighborhoodExternalInputDTOList = neighborhoodService.csvToNeighborhoodExternalObject(neighborhoodLines);
        neighborhoodLines.clear();
        neighborhoodLines = null;
        System.gc();

        System.out.println("neighborhoodExternalInputDTO: " + neighborhoodExternalInputDTOList.size());

//        neighborhoodService.printNeighborhoodExternalInputDTOObj(neighborhoodExternalInputDTOList);


        //--------------------
//        List<Neighborhood> neighborhoodList = neighborhoodService.relateNeighborhoodToCities(cityExternalInputDTOList, neighborhoodExternalInputDTOList);

        List<Neighborhood> neighborhoodList = neighborhoodService.relateNeighborhoodToCitiesPagination(cityExternalInputDTOList, neighborhoodExternalInputDTOList);

        System.gc();
        System.out.println("neighborhoodListSize: " + neighborhoodList.size());

//        neighborhoodService.printNeighborhoodList(neighborhoodList);


        neighborhoodService.generateNeighborhoodListCSVFile(neighborhoodList);
        
        neighborhoodService.generateNeighborhoodExternalListCSVFile(neighborhoodService.getExternalNeighborhoodNotFoundList());
        System.out.println("FIM");
    }
}
