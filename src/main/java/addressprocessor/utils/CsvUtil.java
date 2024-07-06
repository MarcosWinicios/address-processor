package addressprocessor.utils;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CsvUtil {

    private static final String INPUT_BASE_PATH = "inputNewFiles/";
    private static final String OUTPUT_BASE_PATH = "outputNewFiles/";

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
            CSVWriter cw = new CSVWriter(fw,
            		';',//Delimitador
            		'\'',//Caractere de aspas simples
            		CSVWriter.NO_ESCAPE_CHARACTER,
            		CSVWriter.DEFAULT_LINE_END);
            
         // Processar os dados para remover aspas duplas
            for (String[] line : data) {
                for (int i = 0; i < line.length; i++) {
                    if (line[i] != null) {
                    	line[i] = line[i].replace("\"", "\"");  // Remove aspas duplas
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

    public static List<String[]> readCsvFile(String fileName){

        System.out.println("Lendo arquivo: " + fileName);
        String pathName = INPUT_BASE_PATH + fileName;

        // Configura o parser para usar ; como separador e ' como delimitador
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withQuoteChar('\'')
                .build();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(pathName))
                .withCSVParser(parser)
                .build()) {
            List<String[]> lines = reader.readAll();
            return lines;
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String[]> readCsvFileMX(String fileName){

        System.out.println("Lendo arquivo: " + fileName);
        String pathName = INPUT_BASE_PATH + fileName;

        // Configura o parser para usar ; como separador e ' como delimitador
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withQuoteChar('\'')
                .build();

        List<String[]> filteredLines = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(pathName))
                .withCSVParser(parser)
                .build()) {

            String[] nextLine;
            int count = 0;
            while ((nextLine = reader.readNext()) != null) {
                if(count == 0){
                    filteredLines.add(nextLine);
                    count++;
                }
                // Verifica se o valor na coluna especificada corresponde ao valor de filtro
                if (nextLine[8].equals("MX")) {
                    filteredLines.add(nextLine);
                }
            }
            return filteredLines;
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String[]> readCsvFileWithoutQuotes(String fileName){

        System.out.println("Lendo arquivo: " + fileName);
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
