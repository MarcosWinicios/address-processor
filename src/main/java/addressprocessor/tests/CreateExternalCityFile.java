package addressprocessor.tests;

import addressprocessor.dto.input.StateInputDTO;
import addressprocessor.service.StateService;
import addressprocessor.utils.TestPerformanceUtil;

import java.util.List;

public class CreateExternalCityFile {

    public static void main(String[] args) {

        // Lendo arquivo com os estados
        String stateFileName = "DIM1.csv";
        StateService stateService = new StateService();

//        setTimeValues(System.nanoTime());
        List<String[]> stateLines = stateService.readCsvFile(stateFileName);


        TestPerformanceUtil.setTimeValues(System.nanoTime(), "csvToStateInputDTOFunctional");
        List<StateInputDTO> stateInputDTOList = stateService.csvToStateInputDTOFunctional(stateLines);
        TestPerformanceUtil.setTimeValues(System.nanoTime(), "csvToStateInputDTOFunctional");

        System.out.println("Tamanho da lista 1: " + stateInputDTOList.size());

        TestPerformanceUtil.setTimeValues(System.nanoTime(), "csvToStateInputDTO");
        List<StateInputDTO> stateInputDTOList2 = stateService.csvToStateInputDTO(stateLines);
        TestPerformanceUtil.setTimeValues(System.nanoTime(), "csvToStateInputDTO");

        System.out.println("Tamanho da lista 2: " + stateInputDTOList2.size());

        TestPerformanceUtil.printMethodsInAnalysis();
        TestPerformanceUtil.printSlowerAndFasterMethod();
        TestPerformanceUtil.resetMethodList();


//        stateService.printStateFunctional(stateLines);


//        stateService.printStateInputDTOList(stateInputDTOList);

//        setTimeValues(System.nanoTime());
        //--------------
        /*
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
        */

    }
}
