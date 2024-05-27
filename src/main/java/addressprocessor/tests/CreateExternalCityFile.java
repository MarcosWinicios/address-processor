package addressprocessor.tests;

import addressprocessor.dto.input.CityExternalInputDTO;
import addressprocessor.dto.input.StateInputDTO;
import addressprocessor.model.City;
import addressprocessor.service.CityService;
import addressprocessor.service.StateService;

import java.util.List;

public class CreateExternalCityFile {

    public static void main(String[] args) {

        // Lendo arquivo com os estados
        String stateFileName = "tb_state_id_external_id.csv";
        StateService stateService = new StateService();

        var stateLines = stateService.readCsvFile(stateFileName);
        List<StateInputDTO> stateInputDTOList = stateService.csvToStateInputDTO(stateLines);

//        stateService.printStateInputDTOList(stateInputDTOList);

        //--------------

        System.out.println("\n_______________________________________________\n");
        //Lendo arquivo com as cidades
        String cityFileName = "input_tb_city_external.csv";
        CityService cityService = new CityService();

        var cityLines = cityService.readCsvFile(cityFileName);
        System.out.println("cityExternalFileSize: " + cityLines.size());

        List<CityExternalInputDTO> cityExternalInputDTOList = cityService.csvToCityExternalObject(cityLines);
        System.out.println("cityExternalInputDTOList: " + cityExternalInputDTOList.size());

//        cityService.printCityCoreDTOList(cityCoreDTOList);

        System.out.println("\n_______________________________________________\n");

        //--------------------
        List<City> cityList =  cityService.processorCityExternalListOfStateList(cityExternalInputDTOList, stateInputDTOList);

        System.out.println("cityListSize: " + cityList.size());
//        cityService.printCityList(cityList);



        cityService.generateCityListCSVFile(cityList);
        cityService.generateCityExternalFile(cityList);
    }
}
