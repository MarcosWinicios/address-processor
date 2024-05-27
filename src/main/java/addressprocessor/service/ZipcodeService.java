package addressprocessor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import addressprocessor.dto.input.NeighborhoodExternalInputDTO;
import addressprocessor.dto.input.NeighborhoodInternalDTO;
import addressprocessor.model.Zipcode;
import addressprocessor.utils.CsvUtil;
import lombok.Getter;

@Service
public class ZipcodeService {

    @Getter
    private List<NeighborhoodExternalInputDTO> externalNeighborhoodNotFoundList;

    private int memoryCount;

    public ZipcodeService() {
        this.memoryCount = 20;
    }

    public ZipcodeService(int memoryCount) {
        this.memoryCount = memoryCount;
    }


    public List<Zipcode> relateNeighborhoodToZipcode(List<NeighborhoodInternalDTO> neighborhoodInternalDTOList, List<NeighborhoodExternalInputDTO> neighborhoodExternalInputDTOList) {
        List<Zipcode> zipcodeList = new ArrayList<>();
        List<Zipcode> zipcodeListPage = new ArrayList<>();
        externalNeighborhoodNotFoundList = new ArrayList<>();


        int count = 0;
        int countFilesGenerated = 1;
        int pageSize = neighborhoodExternalInputDTOList.size() / 2;
        boolean isPresent = false;

        System.out.println("1° aux1: " + pageSize);
        
            int countClearMemory = 0;
            for (int i = 0; i < neighborhoodExternalInputDTOList.size(); i++) {

                countClearMemory++;
                if (countClearMemory == memoryCount) {
                    System.gc();
                    countClearMemory = 0;
                }

                var countryCode = neighborhoodExternalInputDTOList.get(i).getCountryCode();
                var stateExternalCode = neighborhoodExternalInputDTOList.get(i).getStateExternalCode();
                var cityExternalCode = neighborhoodExternalInputDTOList.get(i).getCityExternalCode();
                var neighborhoodExternalCode = neighborhoodExternalInputDTOList.get(i).getExternalCode();

                Zipcode zipcode = new Zipcode();

                for (NeighborhoodInternalDTO neighborhoodInternalDTO : neighborhoodInternalDTOList) {
                    if (
                            countryCode.equalsIgnoreCase(neighborhoodInternalDTO.getCountryCode()) &&
                                    stateExternalCode.equals(neighborhoodInternalDTO.getStateExternalCode()) &&
                                    cityExternalCode.equals(neighborhoodInternalDTO.getCityExternalCode()) &&
                                    neighborhoodExternalCode.equalsIgnoreCase(neighborhoodInternalDTO.getNeighborhoodExternalCode())

                    ) {

                        zipcode.setStreet(null);
                        zipcode.setZipCode(neighborhoodExternalInputDTOList.get(i).getZipcode());
                        zipcode.setNeighborhoodId(neighborhoodInternalDTO.getNeighborhoodId());
                        zipcode.setCityId(neighborhoodInternalDTO.getCityId());
                        zipcode.setStateId(neighborhoodInternalDTO.getStateId());
                        zipcode.setCountryCode(neighborhoodInternalDTO.getCountryCode());

                        zipcodeList.add(zipcode);
                        zipcodeListPage.add(zipcode);

                        isPresent = true;
                    }

                }

                if (!isPresent) {
                    externalNeighborhoodNotFoundList.add(neighborhoodExternalInputDTOList.get(i));
                }
                if (count == pageSize) {
                    this.generateZipcodeCsvFile(zipcodeListPage, "new_tb_address_" + countFilesGenerated);
                    countFilesGenerated++;

                    System.out.println("Último item gerado na página " + countFilesGenerated + ": " + i);
                    count = 0;
                    zipcodeListPage.clear();
                }
                count++;

                if (i > 0) {
                    neighborhoodExternalInputDTOList.set(i - 1, null);
                }

                isPresent = false;
            }
            return zipcodeList;
        
    }



    public void generateZipcodeCsvFile(List<Zipcode> zipCodeList) {
        this.generateZipcodeCsvFile(zipCodeList, "output_new_tb_address");
    }

    public void generateZipcodeCsvFile(List<Zipcode> zipCodeList, String fileName){
        String path = CsvUtil.getOutputBasePath();
        List<String[]> data = this.zipcodeListToCsvLines(zipCodeList);

        CsvUtil.generateCsvFile(data, path, fileName);
    }

    private List<String[]> zipcodeListToCsvLines(List<Zipcode> zipCodeList) {
        List<String[]> lines = new ArrayList<>();
        String[] header = {"adrs_street", "adrs_zip_code", "nghb_id", "city_id", "stte_id", "ctry_code"};
        lines.add(header);
        for (Zipcode zipcode : zipCodeList) {

            String[] newLine = {
                    zipcode.getStreet(),
                    zipcode.getZipCode(),
                    zipcode.getNeighborhoodId().toString(),
                    zipcode.getCityId().toString(),
                    zipcode.getStateId().toString(),
                    zipcode.getCountryCode()
            };
            lines.add(newLine);
        }
        zipCodeList = null;
        return lines;
    }
}
