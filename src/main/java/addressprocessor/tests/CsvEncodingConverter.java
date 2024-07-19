package addressprocessor.tests;

import addressprocessor.utils.FileUtil;

public class CsvEncodingConverter {
    public static void main(String[] args) {
        String inputFileName = "tb_country.csv";
        FileUtil.encodingConverter(inputFileName);
    }

}
