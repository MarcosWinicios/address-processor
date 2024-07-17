package addressprocessor.tests;

import addressprocessor.utils.CsvUtil;

import java.util.List;

public class ReplaceCSVFilePattern {
	
	public static void main(String[] args) {
        // Caminho do arquivo CSV existente
        String inputFileName = "tb_state.csv";
        
        // Lê o arquivo CSV existente
        System.out.println("Lendo arquivo");
        List<String[]> data = CsvUtil.readCsvFile(inputFileName);
//        List<String[]> data = CsvUtil.readCsvFile(inputFileName);

        // Caminho do diretório e nome do novo arquivo
        
        String outputFileName = "NEW_" + inputFileName;

        // Gera o novo CSV com o formato especificado
        System.out.println("Gerando arquivo");
        CsvUtil.generateCsvFile(data, CsvUtil.getOutputBasePath(), outputFileName.replace(".csv", ""));
        
        System.out.println("FIM");
    }
}
