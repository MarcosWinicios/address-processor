package addressprocessor.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class CsvUtil {

    private static final String INPUT_BASE_PATH = "inputFiles/";
    private static final String OUTPUT_BASE_PATH = "outputFiles/";

    public static String getInputBasePath(){
        return INPUT_BASE_PATH;
    }

    public static String getOutputBasePath(){
        return OUTPUT_BASE_PATH;
    }

    /**
     * @param data          Informações que irão compor o conteúdo do arquivo CSV
     * @param directoryPath Diretório alvo onde o arquivo será salvo
     * @param fileName      Nome do arquivo a ser gerado
     */
    public static String generateCsvFile(List<String[]> data, String directoryPath, String fileName) {

        try {
            String fileFullName = directoryPath + fileName + ".csv";

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
            CSVWriter cw = new CSVWriter(fw,
            		';',//Delimitador
            		'\'',//Caractere de aspas simples
            		CSVWriter.NO_ESCAPE_CHARACTER,
            		CSVWriter.DEFAULT_LINE_END);
            
         // Processar os dados para remover aspas duplas
            for (String[] line : data) {
                for (int i = 0; i < line.length; i++) {
                    if (line[i] != null) {
                        line[i] = line[i].replace("\"", "");  // Remove aspas duplas
                    }
                }
                cw.writeNext(line);
            }

//            cw.writeAll(data);

            System.err.println("\nArquivo CSV gerado com sucesso em: " + file.getAbsolutePath() + "\n");

            cw.close();
            fw.close();
            return file.getAbsolutePath();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<String[]> readCsvFile(String fileName) {

        String pathName = INPUT_BASE_PATH + fileName;

        try (CSVReader reader = new CSVReader(new FileReader(pathName))) {

            List<String[]> lines = reader.readAll();
            return lines;
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String[]> readCsvFileWithFilter(String fileName) {

        String pathName = INPUT_BASE_PATH + fileName;

        try (CSVReader reader = new CSVReader(new FileReader(pathName))) {

            List<String[]> lines = reader.readAll();
            return lines;
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void printCsvFile(String fileName){
        printCsvLines(Objects.requireNonNull(readCsvFile(fileName)));
    }

    public static void printCsvLines(List<String[]> lines){
        for (String[] line : lines) {
            for (String field : line) {
                System.out.print(field + " | ");
            }
            System.out.println(); // Pula para a próxima linha
        }
    }

}
