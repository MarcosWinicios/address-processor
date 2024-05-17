package addressprocessor.service;

import addressprocessor.dto.input.CityCoreDTO;
import addressprocessor.dto.input.CityExternalInputDTO;
import addressprocessor.dto.input.StateInputDTO;
import addressprocessor.model.City;
import addressprocessor.model.State;
import addressprocessor.utils.CsvUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CityService {

    public List<CityCoreDTO> csvToCityObject(List<String[]> data) {
        List<CityCoreDTO> cityList = new ArrayList<>();

        for (int i = 1; i < data.size(); i++) {
            CityCoreDTO cityCoreDTO = new CityCoreDTO();

            cityCoreDTO.setName(data.get(i)[0]);
            cityCoreDTO.setStateId(Integer.parseInt(data.get(i)[1]));
            cityCoreDTO.setCountryCode(data.get(i)[2]);


            cityList.add(cityCoreDTO);

        }
        return cityList;
    }

    public List<CityExternalInputDTO> csvToCityExternalObject(List<String[]> cityLines) {
        List<CityExternalInputDTO> result = new ArrayList<>();
        for(int i = 1; i < cityLines.size(); i++){
            CityExternalInputDTO obj = new CityExternalInputDTO(cityLines.get(i));
            result.add(obj);
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

    public List<City> processorCityListOfStateList(List<CityCoreDTO> cityCoreDTOS, List<State> stateList) {

        List<City> cityList = new ArrayList<>();

        for (CityCoreDTO city : cityCoreDTOS) {
            var countryCode = city.getCountryCode();
            Integer stateId = city.getStateId();
            City newCity = new City();

            for (State state : stateList) {
                if (countryCode.equalsIgnoreCase(state.getCountryCode()) && stateId.equals(stateId)) {
                    newCity.setName(city.getName());
                    newCity.setStateId(city.getStateId());
                }
            }
            cityList.add(newCity);
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
            Integer stateId = city.getStateId();

            City newCity = new City();

            for (StateInputDTO state : stateInputDTOList) {

                if (state.getCountryCode().equalsIgnoreCase(countryCode) && Objects.equals(state.getSequencial(), stateId)) {

                    newCity.setName(city.getName());
                    newCity.setStateId(state.getId());
                    newCity.setExternalCode(state.getSequencial());
                }
            }
            cityList.add(newCity);
        }

        return cityList;
    }

    public void generateCityListCSVFile(List<City> cityList) {
        String path = CsvUtil.getOutputBasePath();
        List<String[]> data = this.cityListToCsvLines(cityList);

        CsvUtil.generateCsvFile(data, path, "output_tb_city");
    }

    public List<String[]> cityListToCsvLines(List<City> cityList) {
        List<String[]> lines = new ArrayList<>();
        String[] header = {"city_name", "stte_id"};
        lines.add(header);
        for (City city : cityList) {
            if (city.getStateId() == null) {
                System.out.println(city.toString());
            }
            String[] newLine = {city.getName(), city.getStateId().toString()};
            lines.add(newLine);
        }

        return lines;
    }



}
