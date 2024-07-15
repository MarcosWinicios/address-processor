package addressprocessor.tests;

import addressprocessor.utils.CsvUtil;

import java.util.List;

public class RewriteFileFromMX {
	
	public static void main(String[] args) {
        // Caminho do arquivo CSV existente
        String inputFileName = "DIM1";

        // Lê o arquivo CSV existente
        System.out.println("Lendo arquivo");
//        List<String[]> data = CsvUtil.readCsvFileWithoutQuotes(inputFileName);
        List<String[]> data = CsvUtil.readCsvFile(inputFileName, "MX", 3);

        // Caminho do diretório e nome do novo arquivo
        
        String outputFileName = inputFileName + "_MX";

        // Gera o novo CSV com o formato especificado
        System.out.println("Gerando arquivo");
        CsvUtil.generateCsvFile(data, CsvUtil.getOutputBasePath(), outputFileName);
        
        System.out.println("FIM");
    }
}
