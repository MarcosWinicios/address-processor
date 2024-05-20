package addressprocessor.service;

import addressprocessor.dto.input.CityCoreDTO;
import addressprocessor.dto.input.CityExternalInputDTO;
import addressprocessor.dto.input.NeighborhoodExternalInputDTO;
import addressprocessor.dto.input.StateInputDTO;
import addressprocessor.model.City;
import addressprocessor.model.Neighborhood;
import addressprocessor.utils.CsvUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NeighborhoodService {

    public List<NeighborhoodExternalInputDTO> csvToNeighborhoodExternalObject(List<String[]> lines) {
        System.out.println("\nTransfomando dados");
        List<NeighborhoodExternalInputDTO> result = new ArrayList<>();
        for(int i = 1; i < lines.size(); i++){
            NeighborhoodExternalInputDTO obj = new NeighborhoodExternalInputDTO(lines.get(i));
            result.add(obj);
        }

        System.out.println("\nFinalizando transformação de dados");
        return result;
    }


    public List<String[]> readCsvFile(String fileName) {
        System.out.println("\nLendo arquivo");
        return CsvUtil.readCsvFile(fileName);
    }

    public void printNeighborhoodExternalInputDTOObj(List<NeighborhoodExternalInputDTO> neighborhoodExternalInputDTO){
        System.out.println("\nPrintando dados dos bairros");
        int count = 1;
        for (NeighborhoodExternalInputDTO neighborhood : neighborhoodExternalInputDTO) {
            System.out.println("[ " + count + "] = " + neighborhood.toString());
            count++;
        }
    }

    public List<Neighborhood> relateNeighborhoodToCities(List<CityExternalInputDTO> cityExternalInputDTOList, List<NeighborhoodExternalInputDTO> neighborhoodExternalInputDTOList){
        List<Neighborhood> neighborhoodList = new ArrayList<>();

        for (NeighborhoodExternalInputDTO neighborhoodExternalInputDTO : neighborhoodExternalInputDTOList){
            var countryCode = neighborhoodExternalInputDTO.getCountryCode();
            var stateExternalCode = neighborhoodExternalInputDTO.getStateExternalCode();
            var cityExternalCode = neighborhoodExternalInputDTO.getCityExternalCode();

            Neighborhood newNeighborhood = new Neighborhood();

            for(CityExternalInputDTO cityExternalInputDTO : cityExternalInputDTOList){
                if(
                        countryCode.equalsIgnoreCase(cityExternalInputDTO.getCountryCode())
                        && stateExternalCode.equals(cityExternalInputDTO.getStateExternalCode())
                        && cityExternalCode.equals(cityExternalInputDTO.getCityExternalCode())
                ){
                    newNeighborhood.setName(neighborhoodExternalInputDTO.getName());
                    newNeighborhood.setExternalCode(neighborhoodExternalInputDTO.getExternalCode());
                    newNeighborhood.setCityId(cityExternalInputDTO.getId());
                }
                neighborhoodList.add(newNeighborhood);
            }
        }

        return neighborhoodList;
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
}
