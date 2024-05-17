package addressprocessor.tests;

import addressprocessor.dto.input.CityExternalInputDTO;
import addressprocessor.dto.input.StateInputDTO;
import addressprocessor.model.City;
import addressprocessor.service.CityService;
import addressprocessor.service.StateService;

import java.util.List;

public class ReadCsvFileCityExternal {

    public static void main(String[] args) {
        /*
        // Lendo arquivo com os estados
        String stateFileName = "tb_city_external.csv";
        StateService stateService = new StateService();

        var stateLines = stateService.readCsvFile(stateFileName);
        List<StateInputDTO> stateInputDTOList = stateService.csvToStateInputDTO(stateLines);

//        stateService.printStateInputDTOList(stateInputDTOList);
        */
        //--------------

        System.out.println("\n_______________________________________________\n");
        System.out.println("Lendo arquivo com as cidades\n");
        //Lendo arquivo com as cidades
        String cityFileName = "tb_city_external.csv";
        CityService cityService = new CityService();

        var cityLines = cityService.readCsvFile(cityFileName);
        List<CityExternalInputDTO> cityExternalInputDTOList = cityService.csvToCityExternalObject(cityLines);

        System.out.println("cityExternalInputDTOList: " + cityExternalInputDTOList.size());

        cityService.printCityExternalObj(cityExternalInputDTOList);

        System.out.println("\n_______________________________________________\n");
        System.out.println("Lendo arquivo com os bairros\n");
/*
        //--------------------
        List<City> cityList =  cityService.relateCitiesToStates(cityExternalInputDTOList, stateInputDTOList);

        System.out.println("cityListSize: " + cityList.size());
//        cityService.printCityList(cityList);

*/

//        cityService.generateCityListCSVFile(cityList);
    }
}
