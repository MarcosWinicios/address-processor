package addressprocessor.tests;

import addressprocessor.utils.CsvUtil;

public class PrintContentFile {

    public static void main(String[] args) {

        String fileName =  "tc_dir_dim2-temp";

        CsvUtil.printCsvFile(fileName);
    }
}
