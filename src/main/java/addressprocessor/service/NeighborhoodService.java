package addressprocessor.service;

import addressprocessor.dto.input.CityExternalInputDTO;
import addressprocessor.dto.input.NeighborhoodExternalInputDTO;
import addressprocessor.model.Neighborhood;
import addressprocessor.utils.CsvUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NeighborhoodService {

    public List<NeighborhoodExternalInputDTO> csvToNeighborhoodExternalObject(List<String[]> lines) {
        System.out.println("\nTransfomando CSV em objetos NeighborhoodExternalInputDTO");
        
        List<NeighborhoodExternalInputDTO> result = new ArrayList<>();
       
        for (int i = 1; i < lines.size(); i++) {
        	System.gc();
            NeighborhoodExternalInputDTO obj = new NeighborhoodExternalInputDTO(lines.get(i));
            result.add(obj);
            obj = null;
            
            if(i > 0){
            	lines.set(i -1 , null);
            }
        }

        System.out.println("\nFinalizando transformação de CSV em objetos NeighborhoodExternalInputDTO");
        return result;
    }
    
	public List<Neighborhood> csvToNeighborhood(List<String[]> neighborhoodLines) {
		System.out.println("\nTransfomando CSV em objetos Neighborhood");
		List<Neighborhood> result = new ArrayList<>();
		for (int i = 1; i < neighborhoodLines.size(); i++) {
//			System.gc();
            result.add(new Neighborhood(neighborhoodLines.get(i))); 
            
//            if(i > 0){
//            	neighborhoodLines.set(i -1 , null);
//            }
        }
		System.out.println("\nFinalizando transformação de CSV em objetos Neighborhood");
		return result;
	}


    public List<String[]> readCsvFile(String fileName) {
        System.out.println("\nLendo arquivo");

        return CsvUtil.readCsvFile(fileName);
    }

    public void printNeighborhoodExternalInputDTOObj(List<NeighborhoodExternalInputDTO> neighborhoodExternalInputDTO) {
        System.out.println("\nPrintando dados dos bairros- ENTRADA");
        int count = 1;
        for (NeighborhoodExternalInputDTO neighborhood : neighborhoodExternalInputDTO) {
            System.out.println("[ " + count + "] = " + neighborhood.toString());
            count++;
        }
    }

    public void printNeighborhoodList(List<Neighborhood> neighborhoodList) {
        System.out.println("\n PRINTANDO LISTA DE BAIRRROS - SAÍDA\n");

        int count = 1;
        for (Neighborhood neighborhood : neighborhoodList) {
            System.out.println("[ " + count + "] = " + neighborhood.toString());
            count++;
        }
    }

    public List<Neighborhood> relateNeighborhoodToCities(List<CityExternalInputDTO> cityExternalInputDTOList, List<NeighborhoodExternalInputDTO> neighborhoodExternalInputDTOList) {
        List<Neighborhood> neighborhoodList = new ArrayList<>();

        for (NeighborhoodExternalInputDTO neighborhoodExternalInputDTO : neighborhoodExternalInputDTOList) {
            var countryCode = neighborhoodExternalInputDTO.getCountryCode();
            var stateExternalCode = neighborhoodExternalInputDTO.getStateExternalCode();
            var cityExternalCode = neighborhoodExternalInputDTO.getCityExternalCode();

            Neighborhood newNeighborhood = new Neighborhood();

            for (CityExternalInputDTO cityExternalInputDTO : cityExternalInputDTOList) {
                if (
                        countryCode.equalsIgnoreCase(cityExternalInputDTO.getCountryCode())
                                && stateExternalCode.equals(cityExternalInputDTO.getStateExternalCode())
                                && cityExternalCode.equals(cityExternalInputDTO.getCityExternalCode())
                ) {
                    newNeighborhood.setName(neighborhoodExternalInputDTO.getName());
                    newNeighborhood.setExternalCode(neighborhoodExternalInputDTO.getExternalCode());
                    newNeighborhood.setCityId(cityExternalInputDTO.getId());
                }
                neighborhoodList.add(newNeighborhood);
            }
        }

        return neighborhoodList;
    }


    public List<Neighborhood> relateNeighborhoodToCitiesPagination(List<CityExternalInputDTO> cityExternalInputDTOList, List<NeighborhoodExternalInputDTO> neighborhoodExternalInputDTOList) {
        List<Neighborhood> neighborhoodList = new ArrayList<>();


        int count = 0;
        int countFilesGenerated = 1;
        int neighborhoodExternalListSize = neighborhoodExternalInputDTOList.size();
        int aux1 = neighborhoodExternalListSize / 15;

        System.out.println("1° aux1: " + aux1);
        try {
            for (int i = 0; i < neighborhoodExternalInputDTOList.size(); i++) {
            	System.gc();

                var countryCode = neighborhoodExternalInputDTOList.get(i).getCountryCode();
                var stateExternalCode = neighborhoodExternalInputDTOList.get(i).getStateExternalCode();
                var cityExternalCode = neighborhoodExternalInputDTOList.get(i).getCityExternalCode();

                Neighborhood newNeighborhood = new Neighborhood();

                for (CityExternalInputDTO cityExternalInputDTO : cityExternalInputDTOList) {
                    if (
                            countryCode.equalsIgnoreCase(cityExternalInputDTO.getCountryCode())
                                    && stateExternalCode.equals(cityExternalInputDTO.getStateExternalCode())
                                    && cityExternalCode.equals(cityExternalInputDTO.getCityExternalCode())
                    ) {
                        newNeighborhood.setName(neighborhoodExternalInputDTOList.get(i).getName());
                        newNeighborhood.setExternalCode(neighborhoodExternalInputDTOList.get(i).getExternalCode());
                        newNeighborhood.setCityId(cityExternalInputDTO.getId());
                        neighborhoodList.add(newNeighborhood);
                        System.out.println("Adicionando novo bairro a cidade: " + cityExternalInputDTO.getName());
                    }
                    
                   
                }
                if (i == aux1) {
                    this.generateNeighborhoodListCSVFile(neighborhoodList, "tb_neighborhood_" + countFilesGenerated);
                    countFilesGenerated++;


                    System.out.println("Último item gerado na página " + countFilesGenerated + ": " + i);
                }
                count = i;

                if(i > 0){
                    neighborhoodExternalInputDTOList.set(i -1 , null);
                }

                
            }
            countFilesGenerated++;
            this.generateNeighborhoodListCSVFile(neighborhoodList, "tb_neighborhood_" + countFilesGenerated);

            System.out.println("Último item gerado na página " + countFilesGenerated + ": " + count);

            return neighborhoodList;
        }catch (OutOfMemoryError ex){
            System.err.println("Quebrou");
            System.out.println("countFilesGenerated: " + countFilesGenerated);
            System.out.println("count: " + count);
            System.out.println("aux1: " + aux1);
            this.generateNeighborhoodListCSVFile(neighborhoodList, "tb_neighborhood_" + countFilesGenerated);
            throw new OutOfMemoryError(ex.getMessage());
        }
    }


    public void generateNeighborhoodListCSVFile(List<Neighborhood> neighborhoodList) {
        this.generateNeighborhoodListCSVFile(neighborhoodList, "output_tb_neighborhood_not_found");
    }

    public void generateNeighborhoodListCSVFile(List<Neighborhood> neighborhoodList, String fileName) {
        String path = CsvUtil.getOutputBasePath();
        List<String[]> data = this.neighborhoodListToCsvLines(neighborhoodList);

        CsvUtil.generateCsvFile(data, path, fileName);
    }

    public List<String[]> neighborhoodListToCsvLines(List<Neighborhood> neighborhoodList) {
        List<String[]> lines = new ArrayList<>();
        String[] header = {"nghb_name", "nghb_external_code", "city_id"};
        lines.add(header);
        for (Neighborhood neighborhood : neighborhoodList) {

            String[] newLine = {neighborhood.getName(), neighborhood.getExternalCode().toString(), neighborhood.getCityId().toString()};
            lines.add(newLine);
        }
        neighborhoodList = null;
        return lines;
    }
    
    public List<NeighborhoodExternalInputDTO> compareNeighboardExternalToInternal(List<Neighborhood> neighborhoodList, List<NeighborhoodExternalInputDTO> externalInputDTOList) {
    	
    	List<NeighborhoodExternalInputDTO> externalInputDTOsNotFound = new ArrayList<>();
    	
    	boolean isPresent = false;
    	for(int i = 0; i < externalInputDTOList.size(); i++) {
    		System.gc();
    		isPresent = false;
    	
    		var name = externalInputDTOList.get(i).getName();
    		var neighborhoodExternalId = externalInputDTOList.get(i).getExternalCode();
    		
    		for(Neighborhood neighborhood : neighborhoodList) {
    			if(!((neighborhood.getExternalCode() == null) || (neighborhood.getName() == null))) {
	    			if(neighborhood.getName().equalsIgnoreCase(name) && neighborhood.getExternalCode().equals(neighborhoodExternalId)) {
	    				isPresent = true;
	    				continue;
	    			}
    			}else {
    				System.out.println("Bairro interno com valores null: \n" + neighborhood.toString());
    				System.out.println("Bairro externo com valores null: \n" + externalInputDTOList.get(i).toString());
    				
    			}
    		}
    		
    		if(!isPresent) {
    			externalInputDTOsNotFound.add(externalInputDTOList.get(i));
    		}
    		
    		if(i > 0){
    			externalInputDTOList.set(i -1 , null);
            }
    	}
    	
    	return externalInputDTOsNotFound;
    }
    
    public void generateNeighborhoodExternalListCSVFile(List<NeighborhoodExternalInputDTO> neighborhoodNotFoundList) {
    	this.generateNeighborhoodExternalListCSVFile(neighborhoodNotFoundList, "output_neighborhoodNotFoundList");
    }
    
	public void generateNeighborhoodExternalListCSVFile(List<NeighborhoodExternalInputDTO> neighborhoodNotFoundList, String fileName) {
		String path = CsvUtil.getOutputBasePath();
        List<String[]> data = this.neighborhoodExternalListToCSVLines(neighborhoodNotFoundList);

        CsvUtil.generateCsvFile(data, path, fileName);
		
	}
    
	public List<String[]> neighborhoodExternalListToCSVLines(List<NeighborhoodExternalInputDTO> neighborhoodNotFoundList) {
		List<String[]> lines = new ArrayList<>();
		
        String[] header = {"DIM1","DIM2","DIM3","NOMBRE","CODIGOPAIS"};
        lines.add(header);
        
        for (NeighborhoodExternalInputDTO neighborhood : neighborhoodNotFoundList) {

            String[] newLine = {
            		neighborhood.getStateExternalCode().toString(),
            		neighborhood.getCityExternalCode().toString(),
            		neighborhood.getExternalCode().toString(), 
            		neighborhood.getName(),
            		neighborhood.getCountryCode()};
            lines.add(newLine);
        }
        
        neighborhoodNotFoundList = null;
        
        return lines;
		
	}

}
