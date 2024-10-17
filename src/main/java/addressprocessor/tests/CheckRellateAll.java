package addressprocessor.tests;

import addressprocessor.AddressProcessorApplication;
import addressprocessor.dto.input.RelateAllInputDTO;
import addressprocessor.utils.CsvUtil;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CheckRellateAll {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AddressProcessorApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        String fileName =  "relate_all.csv";

        List<String[]> allData = CsvUtil.readCsvFile(fileName);

        System.out.println(allData.size());

        List<RelateAllInputDTO> relateAllInputDTOList =  allData.stream()
                .skip(1)
                .map(RelateAllInputDTO::new)
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println(relateAllInputDTOList.size());

        List<RelateAllInputDTO> filterdList = relateAllInputDTOList.stream()
                .filter(item -> !item.compareData())
                .collect(Collectors.toCollection(ArrayList::new));
//                .forEach(System.out::println);

        System.out.println(filterdList.size());

    }
}
