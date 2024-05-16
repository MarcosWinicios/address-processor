package addressprocessor.service;

import addressprocessor.model.State;
import addressprocessor.utils.CsvUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateService {


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
}
