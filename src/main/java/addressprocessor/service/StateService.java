package addressprocessor.service;

import addressprocessor.dto.input.StateInputDTO;
import addressprocessor.model.State;
import addressprocessor.utils.CsvUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateService {

    private int memoryCount;
    
    public StateService() {
    	this.memoryCount = 20;
    }
    
    public StateService(int memoryCount) {
    	this.memoryCount = memoryCount;
    }


    public List<State> csvToStateObject(List<String[]> data) {
        List<State> stateList = new ArrayList<>();

        for (int i = 1; i < data.size(); i++) {
            State newState = new State();

            newState.setId(Integer.parseInt(data.get(i)[0]));
            newState.setCode(data.get(i)[1]);
            newState.setName(data.get(i)[2]);
            newState.setCountryCode(data.get(i)[3]);

            stateList.add(newState);

        }
        return stateList;
    }


    public void printStateList(List<State> stateList){
        stateList.forEach( state -> {
            System.out.println(state.toString());
        });
    }
    

    public List<String[]> readCsvFile(String fileName){
        return CsvUtil.readCsvFile(fileName);
    }

    public void printCsv(String fileName){
        CsvUtil.printCsvFile(fileName);
    }

    public void prinCsv(List<String[]> lines){
        CsvUtil.printCsvLines(lines);
    }

    public List<StateInputDTO> csvToStateInputDTO(List<String[]> stateLines) {

        System.out.println("Transformando dados para StateInputDTO");

        List<StateInputDTO> result = new ArrayList<>();

        int countClearMemory = 0;
        for(int i = 0; i < stateLines.size(); i++){
            countClearMemory++;
            if(countClearMemory == memoryCount){
                System.gc();
                countClearMemory = 0;
            }
            result.add(new StateInputDTO(stateLines.get(i)));

            if(i > 0){
                stateLines.set(i -1 , null);
            }
        }
        return result;
    }

    public List<StateInputDTO> csvToStateInputDTOFunctional(List<String[]> stateLines){
        return stateLines.stream()
                .map(StateInputDTO::new)
                .collect(Collectors.toList());
    }


    public void printStateInputDTOList(List<StateInputDTO> stateList){
        stateList.forEach( state -> {
            System.out.println(state.toString());
        });
    }


}
