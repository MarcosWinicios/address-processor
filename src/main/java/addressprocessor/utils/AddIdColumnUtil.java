package addressprocessor.utils;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddIdColumnUtil {

    private static final String INPUT_BASE_PATH = "outputFiles/auxFiles/";
    private static final String OUTPUT_BASE_PATH = "outputFiles/finalFiles/";

    public static String getInputBasePath(){
        return INPUT_BASE_PATH;
    }

    public static String getOutputBasePath(){
        return OUTPUT_BASE_PATH;
    }

    public static void execute(String inputFileName){
        execute(inputFileName, INPUT_BASE_PATH);
    }
    public static void execute(String inputFileName, String inputDirectoryPath){
        System.out.println("\nINICIANDO ADIÇÃO DA COLUNA COM IDs\n");

        List<String[]> originalData = CsvUtil.readCsvFile(inputFileName, inputDirectoryPath);

//        CsvUtil.printLine(originalData.get(0));

        List<String[]> preparedData = prepareData(originalData);
//        CsvUtil.printCsvLines(preparedData);

        generateCsvFile(preparedData, OUTPUT_BASE_PATH, inputFileName);
    }

    private static void generateCsvFile(List<String[]> data, String directoryPath, String fileName) {
        try {
            directoryPath = directoryPath.endsWith("/") ? directoryPath : directoryPath + "/";
            fileName = (fileName.endsWith(".csv") ? fileName.replace(".csv", "") : fileName);
            String fileFullName = directoryPath + fileName + ".csv";

            System.out.println("Gerando arquivo CSV: " + fileFullName);

            File directory = new File(fileFullName).getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Criar o arquivo se não existir
            File file = new File(fileFullName);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(fileFullName);
            // Utiliza o CSVWriter para escrita manual das linhas
            try (CSVWriter cw = new CSVWriter(fw,
                    ';',  // Delimitador
                    CSVWriter.NO_QUOTE_CHARACTER, // Não usa aspas de citação automáticas
                    CSVWriter.NO_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END)) {

                int count = 0;
                for (String[] line : data) {

                    String[] newLine = new String[line.length];
                    if (count > 0) {
                        // A primeira coluna não terá aspas
                        newLine[0] = line[0]; // ID ou valor numérico

                        // Para as outras colunas, adiciona aspas simples e remove aspas duplas internas
                        for (int i = 1; i < line.length; i++) {
                            if (line[i] != null) {
                                newLine[i] = '\'' + line[i].replace("'", "") + '\''; // Adiciona aspas simples e remove aspas simples duplicadas internas
                            }
                        }
                    }else {
                        newLine = line;
                    }

                    // Escreve a linha no arquivo CSV
                    cw.writeNext(newLine);
                    count++;
                }

                System.err.println("\nArquivo CSV gerado com sucesso em: " + file.getAbsolutePath() + "\n");

            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static List<String[]> prepareData(List<String[]> rawData) {
        List<String[]> processedData = new ArrayList<>();
        List<String[]> result = new ArrayList<>();

        int id = 1;
        result.add(generateNewHeader(rawData.get(0)));
//        CsvUtil.printLine(result.get(0));
        rawData.remove(0);
        for (String[] line : rawData) {
            String[] newLine = new String[line.length + 1];
            newLine[0] = String.valueOf(id++); // Converte o ID para String
            for (int i = 0; i < line.length; i++) {
                newLine[i + 1] = line[i]; // Outras colunas permanecem como estão
            }
            processedData.add(newLine);
        }
        result.addAll(processedData);
        return result;
    }

    private static String[] generateNewHeader(String[] currentHeader) {
//        CsvUtil.printLine(currentHeader);
        String[] newHeader = new String[currentHeader.length + 1];
        System.arraycopy(currentHeader, 0, newHeader, 1, currentHeader.length);
        newHeader[0] = "ID";
        return newHeader;
    }
}
