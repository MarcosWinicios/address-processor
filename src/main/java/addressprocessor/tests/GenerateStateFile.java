package addressprocessor.tests;

import addressprocessor.dto.input.StateCoreDTO;
import addressprocessor.model.State;
import addressprocessor.service.StateService;
import addressprocessor.utils.CsvUtil;

import java.util.List;

public class GenerateStateFile {

    public static void main(String[] args) {
        String fileName = "tc_dir_dim1_MX";
        StateService stateService = new StateService();

        List<String[]> csvContent = CsvUtil.readCsvFile(fileName);

        List<StateCoreDTO> stateCoreDTOList = stateService.csvToStateCoreDTO(csvContent);
        List<State> stateList = stateService.stateCoreDTOToState(stateCoreDTOList);

        stateService.generateStateCsvFile(stateList);

    }
}
