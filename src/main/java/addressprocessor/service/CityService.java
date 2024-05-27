package addressprocessor.service;

import addressprocessor.dto.input.CityCoreDTO;
import addressprocessor.dto.input.CityExternalInputDTO;
import addressprocessor.dto.input.StateInputDTO;
import addressprocessor.model.City;
import addressprocessor.utils.CsvUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CityService {

    private int memoryCount;
    
    public CityService() {
    	this.memoryCount = 20;
    }
    
    public CityService(int memoryCount) {
    	this.memoryCount = memoryCount;
    }

    public List<CityCoreDTO> csvToCityCoreDTOObject(List<String[]> data) {

        System.out.println("Transformando dados para cityCoreDTO");
        List<CityCoreDTO> cityList = new ArrayList<>();

        int countClearMemory = 0;
        for (int i = 1; i < data.size(); i++) {

            countClearMemory++;
            if(countClearMemory == memoryCount){
                System.gc();
                countClearMemory = 0;
            }
            cityList.add( new CityCoreDTO(data.get(i)));

            if(i > 0){
                data.set(i -1 , null);
            }
        }
        return cityList;
    }

    public List<CityExternalInputDTO> csvToCityExternalObject(List<String[]> cityLines) {
        List<CityExternalInputDTO> result = new ArrayList<>();

        System.out.println("Transformando dados para CityExternalInputDTO");

        int countClearMemory = 0;
        for(int i = 1; i < cityLines.size(); i++){
            countClearMemory++;
            if(countClearMemory == memoryCount){
                System.gc();
                countClearMemory = 0;
            }

            result.add(new CityExternalInputDTO(cityLines.get(i)));

            if(i > 0){
                cityLines.set(i -1 , null);
            }
        }
        return result;
    }


    public List<String[]> readCsvFile(String fileName) {
        return CsvUtil.readCsvFile(fileName);
    }

    public void printCityCoreDTOList(List<CityCoreDTO> cityCoreDTOList) {

        int count = 1;
        for (CityCoreDTO cityCoreDTO : cityCoreDTOList) {
            System.out.println("[ " + count + "] = " + cityCoreDTO.toString());
            count++;
        }
    }

    public void printCityList(List<City> cityList) {
        int count = 1;
        for (City city : cityList) {
            System.out.println("[ " + count + "] = " + city.toString());
            count++;
        }
    }

    public void printCityExternalObj(List<CityExternalInputDTO> cityExternalList){
        int count = 1;
        for (CityExternalInputDTO city : cityExternalList) {
            System.out.println("[ " + count + "] = " + city.toString());
            count++;
        }
    }

    public List<City> processorCityListOfStateList(List<CityCoreDTO> cityCoreDTOList, List<StateInputDTO> stateList) {

        System.out.println("\nRelacionando cidades com estados\n");

        List<City> cityList = new ArrayList<>();
        int countClearMemory = 0;
        boolean isPresent = false;
        
        for (int i = 0; i < cityCoreDTOList.size(); i++){

            countClearMemory++;
            if(countClearMemory == memoryCount){
                System.gc();
                countClearMemory = 0;
            }

            var countryCode = cityCoreDTOList.get(i).getCountryCode();
            Integer stateExternalId = cityCoreDTOList.get(i).getStateExternalId();

            City newCity = new City();

            for (StateInputDTO state : stateList) {
                if (countryCode.equalsIgnoreCase(state.getCountryCode()) && stateExternalId.equals(state.getStateExternalCode())) {
                    newCity.setName(cityCoreDTOList.get(i).getName());
                    newCity.setStateId(state.getId());
                    isPresent = true;
                }
            }
            
            if(isPresent) {
            	cityList.add(newCity);
            	isPresent = false;
            }else {
            	System.out.println("Cidade não relacionada: " + cityCoreDTOList.get(i).toString());
            }
            

            if(i > 0){
                cityCoreDTOList.set(i -1 , null);
            }
        }

        return cityList;
    }

    public List<City> processorCityExternalListOfStateList(List<CityExternalInputDTO> cityExternalInputDTOList, List<StateInputDTO> stateList) {

        System.out.println("\nRelacionando cidades com estados\n");

        List<City> cityList = new ArrayList<>();
        int countClearMemory = 0;
        boolean isPresent = false;

        for (int i = 0; i < cityExternalInputDTOList.size(); i++){

            countClearMemory++;
            if(countClearMemory == memoryCount){
                System.gc();
                countClearMemory = 0;
            }

            var countryCode = cityExternalInputDTOList.get(i).getCountryCode();
            Integer stateExternalId = cityExternalInputDTOList.get(i).getStateExternalCode();

            City newCity = new City();

            for (StateInputDTO state : stateList) {
                if (countryCode.equalsIgnoreCase(state.getCountryCode()) && stateExternalId.equals(state.getStateExternalCode())) {

                    newCity.setName(cityExternalInputDTOList.get(i).getName());
                    newCity.setStateId(state.getId());

                    newCity.setExternalCityCode(cityExternalInputDTOList.get(i).getCityExternalCode());
                    newCity.setStateExternalCode(state.getStateExternalCode());
                    newCity.setCountryCode(state.getCountryCode());

                    isPresent = true;
                }
            }

            if(isPresent) {
                cityList.add(newCity);
                isPresent = false;
            }else {
                System.out.println("Cidade não relacionada: " + cityExternalInputDTOList.get(i).toString());
            }


            if(i > 0){
                cityExternalInputDTOList.set(i -1 , null);
            }
        }

        return cityList;
    }

    public List<City> relateCitiesToStates(List<CityCoreDTO> cityCoreDTOList, List<StateInputDTO> stateInputDTOList) {
        List<City> cityList = new ArrayList<>();

        for (CityCoreDTO city : cityCoreDTOList) {

            if (city.getName().equalsIgnoreCase("Urbano")) {
                System.out.println("CITY AQUI: \n " + city.toString());
            }

            var countryCode = city.getCountryCode();
            Integer stateId = city.getStateExternalId();

            City newCity = new City();

            for (StateInputDTO state : stateInputDTOList) {

                if (state.getCountryCode().equalsIgnoreCase(countryCode) && Objects.equals(state.getStateExternalCode(), stateId)) {

                    newCity.setName(city.getName());
                    newCity.setStateId(state.getId());
                    newCity.setExternalCityCode(state.getStateExternalCode());
                }
            }
            
            cityList.add(newCity);
        }

        return cityList;
    }

    public List<City> relateCitiesToStatesOptimized(List<CityCoreDTO> cityCoreDTOList, List<StateInputDTO> stateInputDTOList) {
        List<City> cityList = new ArrayList<>();

        for(int i = 0; i < cityCoreDTOList.size(); i++){

            City newCity = new City();
            var countryCode = cityCoreDTOList.get(i).getCountryCode();
            Integer stateId = cityCoreDTOList.get(i).getStateExternalId();
            
            boolean isPresent = false;
            
            for (StateInputDTO state : stateInputDTOList) {
            	
            	
                if (state.getCountryCode().equalsIgnoreCase(countryCode) && Objects.equals(state.getStateExternalCode(), stateId)) {
                		
                    newCity.setName(cityCoreDTOList.get(i).getName());
                    newCity.setStateId(state.getId());
                    newCity.setExternalCityCode(state.getStateExternalCode());
                    
                    isPresent = true;
                }
            }
            
            if(isPresent) {
            	cityList.add(newCity);
            	isPresent = false;
            }else {
            	cityCoreDTOList.get(i).toString();
            }
            
        }
        for (CityCoreDTO city : cityCoreDTOList) {

            if (city.getName().equalsIgnoreCase("Urbano")) {
                System.out.println("CITY AQUI: \n " + city.toString());
            }

            var countryCode = city.getCountryCode();
            Integer stateId = city.getStateExternalId();

            City newCity = new City();

            for (StateInputDTO state : stateInputDTOList) {

                if (state.getCountryCode().equalsIgnoreCase(countryCode) && Objects.equals(state.getStateExternalCode(), stateId)) {

                    newCity.setName(city.getName());
                    newCity.setStateId(state.getId());
                    newCity.setExternalCityCode(state.getStateExternalCode());
                }
            }
            cityList.add(newCity);
        }

        return cityList;
    }

    public void generateCityListCSVFile(List<City> cityList) {
        String path = CsvUtil.getOutputBasePath();
        List<String[]> data = this.cityListToCsvLines(cityList);

        CsvUtil.generateCsvFile(data, path, "output_tb_city_new_version");
    }

    public List<String[]> cityListToCsvLines(List<City> cityList) {

        System.out.println("Transformando lista de objetos em linhas csv\n\n");
        
//        printCityList(cityList);
        System.out.println();

        List<String[]> lines = new ArrayList<>();
        String[] header = {"city_name", "stte_id"};
        lines.add(header);
        
        int count = 0;
        for (City city : cityList) {
        	
            if (city.getStateId() == null) {
                System.out.println("[" + count + "]: " + city.toString());
            }
            String[] newLine = {city.getName(), city.getStateId().toString()};
            lines.add(newLine);
            
            count++;
        }

        return lines;
    }


    public void generateCityExternalFile(List<City> cityList) {
        String path = CsvUtil.getOutputBasePath();
        List<String[]> data = this.cityExternalListToCsvLines(cityList);

        CsvUtil.generateCsvFile(data, path, "output_tb_city_external_list_new_version");
    }

    private List<String[]> cityExternalListToCsvLines(List<City> cityList) {
        System.out.println("Transformando lista de objetos em linhas csv\n\n");

        List<String[]> lines = new ArrayList<>();
        String[] header = {"city_name", "stte_id", "ctry_code", "state_external_id", "city_external_id"};
        lines.add(header);

        int count = 0;
        for (City city : cityList) {

            if (city.getStateId() == null) {
                System.out.println("[" + count + "]: " + city.toString());
            }
            String[] newLine = {
                    city.getName(),
                    city.getStateId().toString(),
                    city.getCountryCode(),
                    city.getStateExternalCode().toString(),
                    city.getExternalCityCode().toString()};

            lines.add(newLine);

            count++;
        }

        return lines;
    }
}
