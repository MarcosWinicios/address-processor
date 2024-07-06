package addressprocessor.tests;

import addressprocessor.utils.CsvUtil;

import java.util.List;

public class RewriteFileFromMX {
	
	public static void main(String[] args) {
        // Caminho do arquivo CSV existente
        String inputFileName = "input_tb_neighborhood_external.csv";
        
        // Lê o arquivo CSV existente
        System.out.println("Lendo arquivo");
//        List<String[]> data = CsvUtil.readCsvFileWithoutQuotes(inputFileName);
        List<String[]> data = CsvUtil.readCsvFileMX(inputFileName);

        // Caminho do diretório e nome do novo arquivo
        
        String outputFileName = "output_tb_neighborhood_external_MX";

        // Gera o novo CSV com o formato especificado
        System.out.println("Gerando arquivo");
        CsvUtil.generateCsvFile(data, CsvUtil.getOutputBasePath(), outputFileName);
        
        System.out.println("FIM");
    }
}
