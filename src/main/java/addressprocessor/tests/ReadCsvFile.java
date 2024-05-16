package addressprocessor.tests;

import addressprocessor.model.State;
import addressprocessor.service.StateService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReadCsvFile {

    public static void main(String[] args) {
        // Nome do arquivo CSV a ser lido
        String fileName = "tb_state_id.csv";
        StateService stateService = new StateService();

        var lines = stateService.readCsvFile(fileName);
        var stateList = stateService.csvToStateObject(lines);

        stateService.printStateList(stateList);
    }
}
