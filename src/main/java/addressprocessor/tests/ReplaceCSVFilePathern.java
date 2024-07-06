package addressprocessor.tests;

import java.util.List;

import addressprocessor.utils.CsvUtil;

public class ReplaceCSVFilePathern {
	
	public static void main(String[] args) {
        // Caminho do arquivo CSV existente
        String inputFileName = "TC_ACTIVIDADES_BANXICO.csv";
        
        // Lê o arquivo CSV existente
        System.out.println("Lendo arquivo");
        List<String[]> data = CsvUtil.readCsvFileWithoutQuotes(inputFileName);
//        List<String[]> data = CsvUtil.readCsvFile(inputFileName);

        // Caminho do diretório e nome do novo arquivo
        
        String outputFileName = "NEW_" + inputFileName;

        // Gera o novo CSV com o formato especificado
        System.out.println("Gerando arquivo");
        CsvUtil.generateCsvFile(data, CsvUtil.getOutputBasePath(), outputFileName.replace(".csv", ""));
        
        System.out.println("FIM");
    }
}
