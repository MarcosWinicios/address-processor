package addressprocessor.tests;

import addressprocessor.dto.input.CityExternalInputDTO;
import addressprocessor.dto.input.NeighborhoodExternalInputDTO;
import addressprocessor.dto.input.StateInputDTO;
import addressprocessor.model.City;
import addressprocessor.model.Neighborhood;
import addressprocessor.service.CityService;
import addressprocessor.service.NeighborhoodService;
import addressprocessor.service.StateService;

import java.util.List;

public class ReadCsvFileCityExternal {

    public static void main(String[] args) {

        System.out.println("Lendo arquivo com as cidades\n");

        String cityFileName = "tb_city_external.csv";
        CityService cityService = new CityService();

        var cityLines = cityService.readCsvFile(cityFileName);
        List<CityExternalInputDTO> cityExternalInputDTOList = cityService.csvToCityExternalObject(cityLines);
        cityLines.clear();
        cityLines = null;
        System.gc();

        System.out.println("cityExternalInputDTOList: " + cityExternalInputDTOList.size());

//        cityService.printCityExternalObj(cityExternalInputDTOList);

        System.out.println("\n_______________________________________________\n");
        System.out.println("Lendo arquivo com os bairros\n");
        String neighborhoodName = "input_neighborhoodNotFoundList.csv";

        NeighborhoodService neighborhoodService = new NeighborhoodService();
        var neighborhoodLines = neighborhoodService.readCsvFile(neighborhoodName);
        
        System.out.println("Linhas dos bairros: " + neighborhoodLines.size());
        
        System.out.println(neighborhoodLines.get(1)[0]);
//        System.out.println(neighborhoodLines.get(1)[1]);
//        System.out.println(neighborhoodLines.get(1)[2]);
//        System.out.println(neighborhoodLines.get(1)[3]);
        
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

        System.out.println("FIM");

        neighborhoodService.generateNeighborhoodListCSVFile(neighborhoodList);
    }
}
