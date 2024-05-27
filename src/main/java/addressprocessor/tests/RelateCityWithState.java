package addressprocessor.tests;

import addressprocessor.service.CityService;
import addressprocessor.service.StateService;

public class RelateCityWithState {
	
	private static final int MEMORY_COUNT = 20;

    public static void main(String[] args) {
        // Nome do arquivo CSV a ser lido
//        String stateFileName = "tb_state_id.csv";
        String stateFileName = "tb_state_id_external_id.csv";
        StateService stateService = new StateService(MEMORY_COUNT);

        var stateLines = stateService.readCsvFile(stateFileName);
        var stateInputDTOList = stateService.csvToStateInputDTO(stateLines);

        stateLines.clear();
        stateLines = null;
        System.gc();

//        stateService.printStateInputDTOList(stateInputDTOList);

        //--------------

        System.out.println("\n_______________________________________________\n");

        String cityFileName = "tb_city_core.csv";
        CityService cityService = new CityService(MEMORY_COUNT);

        var cityLines = cityService.readCsvFile(cityFileName);
        var cityCoreDTOList = cityService.csvToCityCoreDTOObject(cityLines);
        System.out.println("cityCoreListSize: " + cityCoreDTOList.size());

        cityLines.clear();
        cityLines = null;
        System.gc();

//        cityService.printCityCoreDTOList(cityCoreDTOList);

        System.out.println("\n_______________________________________________\n");

        //--------------------
        var cityList =  cityService.processorCityListOfStateList(cityCoreDTOList, stateInputDTOList);
        System.out.println("cityListSize: " + cityList.size());
//        cityService.printCityList(cityList);

        cityService.generateCityListCSVFile(cityList);
    }
}
