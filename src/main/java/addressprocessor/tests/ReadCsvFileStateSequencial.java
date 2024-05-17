package addressprocessor.tests;

import addressprocessor.dto.input.StateInputDTO;
import addressprocessor.model.City;
import addressprocessor.service.CityService;
import addressprocessor.service.StateService;

import java.util.List;

public class ReadCsvFileStateSequencial {

    public static void main(String[] args) {

        // Lendo arquivo com os estados
        String stateFileName = "tb_state_sequencial.csv";
        StateService stateService = new StateService();

        var stateLines = stateService.readCsvFile(stateFileName);
        List<StateInputDTO> stateInputDTOList = stateService.csvToStateInputDTO(stateLines);

        stateService.printStateInputDTOList(stateInputDTOList);

        //--------------

        System.out.println("\n_______________________________________________\n");
        //Lendo arquivo com as cidades
        String cityFileName = "tb_city_core.csv";
        CityService cityService = new CityService();

        var cityLines = cityService.readCsvFile(cityFileName);
        var cityCoreDTOList = cityService.csvToCityObject(cityLines);
        System.out.println("cityCoreListSize: " + cityCoreDTOList.size());

        cityService.printCityCoreDTOList(cityCoreDTOList);

        System.out.println("\n_______________________________________________\n");

        //--------------------
        List<City> cityList =  cityService.relateCitiesToStates(cityCoreDTOList, stateInputDTOList);

        System.out.println("cityListSize: " + cityList.size());
        cityService.printCityList(cityList);



//        cityService.generateCityListCSVFile(cityList);
    }
}
