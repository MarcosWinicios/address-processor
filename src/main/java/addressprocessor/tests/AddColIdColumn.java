package addressprocessor.tests;

import addressprocessor.utils.AddIdColumnUtil;

import java.util.Arrays;
import java.util.List;

public class AddColIdColumn {

    public static void main(String[] args) {

        List<String> csvFileNameList = Arrays.asList(
                "tb_state",
                "tb_city",
                "tb_neighborhood",
                "tb_address"
        );

        for (String file: csvFileNameList){
            file = file + ".csv";
             AddIdColumnUtil.execute(file);
        }
    }
}

