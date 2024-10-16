package addressprocessor.tests;

import addressprocessor.AddressProcessorApplication;
import addressprocessor.dto.input.StateCoreDTO;
import addressprocessor.model.State;
import addressprocessor.service.StateService;
import addressprocessor.utils.AddIdColumnUtil;
import addressprocessor.utils.CsvUtil;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;

public class GenerateStateFile {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new SpringApplicationBuilder(AddressProcessorApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        StateService stateService = applicationContext.getBean(StateService.class);

        String fileName = "tb_state_core";

        List<String[]> csvContent = CsvUtil.readCsvFile(fileName);

//        csvContent.forEach(x -> System.out.println(Arrays.toString(x)));

        List<StateCoreDTO> stateCoreDTOList = stateService.csvToStateCoreDTO(csvContent);

        List<State> stateList = stateService.stateCoreDTOToState(stateCoreDTOList);

//        stateList.forEach(System.out::println);

        String generatedFileName =  stateService.generateStateCsvFile(stateList);

        AddIdColumnUtil.execute(generatedFileName, CsvUtil.getOutputBasePath() );

    }
}
