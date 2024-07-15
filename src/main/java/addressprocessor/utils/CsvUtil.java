package addressprocessor.utils;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    /**
     *
     * @param fileName Input CSV file name
     * @param filterValue Value to be used in the filter.
     * @param indexPosition Position of the column where the <b>filterValue</b> value is located
     * @return
     */
    public static List<String[]> readCsvFileWithFilterSemicolonSingleQuotes(String fileName, String filterValue, int indexPosition ){

        System.out.println("Lendo arquivo: " + fileName);
        String pathName = INPUT_BASE_PATH + fileName;

        // Configura o parser para usar ; como separador e ' como delimitador
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withQuoteChar('\'')
                .build();

        List<String[]> filteredLines = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(pathName))
                .withCSVParser(parser)
                .build()) {

            String[] nextLine;
            int count = 0;
            while ((nextLine = reader.readNext()) != null) {
                if(count == 0){ // Header File
                    printLine(nextLine);
//                    filteredLines.add(nextLine);
                    count++;
                }
                // Verifica se o valor na coluna especificada corresponde ao valor de filtro
                if (nextLine[indexPosition].equals(filterValue)) {
                    filteredLines.add(nextLine);
                }
            }

            return filteredLines;
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String removeFileExtension(String fileName){
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(0, dotIndex);
        } else {
            return fileName; // Retorna o nome completo se não houver ponto ou se estiver no início/final
        }
    }

    public static List<String[]> readCsvFileGenericFormat(String fileName, String filterValue, int indexPosition){
        fileName = removeFileExtension(fileName) + ".csv";
        System.out.println("Lendo arquivo: " + fileName);

        String pathName = INPUT_BASE_PATH + fileName;

        Map<String, Character> separatorAndDelimiter = detectDelimiterAndSeparator(fileName);

        printFileFormat(separatorAndDelimiter);
        // Configura o parser para usar ; como separador e ' como delimitador
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separatorAndDelimiter.get("separator"))
                .withQuoteChar(separatorAndDelimiter.get("delimiter"))
                .build();

        List<String[]> filteredLines = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(pathName))
                .withCSVParser(parser)
                .build()) {

            String[] nextLine;
            int count = 0;
            while ((nextLine = reader.readNext()) != null) {
                if(count == 0){ // Header File
                    printLine(nextLine);
                    filteredLines.add(nextLine);
                    count++;
                }
                // Verifica se o valor na coluna especificada corresponde ao valor de filtro
                if (nextLine[indexPosition].equals(filterValue)) {
                    filteredLines.add(nextLine);
                }
            }

            return filteredLines;
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Character> detectDelimiterAndSeparator(String fileName) {
        String pathName = INPUT_BASE_PATH + fileName;
        char[] possibleSeparators = {',', ';', '\t', '|', ' '};
        char[] possibleDelimiters = {'"', '\'', ' '};
        Map<String, Character> result = new HashMap<>();
        Map<Character, Integer> separatorCounts = new HashMap<>();
        Map<Character, Integer> delimiterCounts = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathName))) {
            String line = br.readLine(); // Lê a primeira linha para análise

            if (line != null) {
                // Conta a frequência de cada possível separador na linha
                for (char sep : possibleSeparators) {
                    int count = line.length() - line.replace(String.valueOf(sep), "").length();
                    separatorCounts.put(sep, count);
                }

                // Conta a frequência de cada possível delimitador na linha
                for (char delim : possibleDelimiters) {
                    int count = line.length() - line.replace(String.valueOf(delim), "").length();
                    delimiterCounts.put(delim, count);
                }

                // Determina o separador mais provável (maior contagem)
                char probableSeparator = separatorCounts.entrySet().stream()
                        .max(Map.Entry.comparingByValue()).get().getKey();
                result.put("separator", probableSeparator);

                // Determina o delimitador mais provável (maior contagem), excluindo espaço em branco
                char probableDelimiter = delimiterCounts.entrySet().stream()
                        .filter(entry -> entry.getKey() != ' ')
                        .max(Map.Entry.comparingByValue()).get().getKey();
                result.put("delimiter", probableDelimiter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static List<String[]> readCsvFileWithoutQuotes(String fileName){

        System.out.println("Lendo arquivo: " + fileName);
        String pathName = INPUT_BASE_PATH + fileName;

        try (CSVReader reader = new CSVReader(new FileReader(pathName))) {
            List<String[]> lines = reader.readAll();
            lines.remove(0);
            return lines;
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void printCsvFile(String fileName){
        printCsvLines(Objects.requireNonNull(readCsvFile(fileName)));
    }

    @Deprecated
    public static void printCsvLinesOld(List<String[]> lines){
        for (String[] line : lines) {
            for (String field : line) {
                System.out.print(field + " | ");
            }
            System.out.println(); // Pula para a próxima linha
        }
    }

    public static void printLine(String[] line){
        System.out.println(String.join(", ", line));
    }
    public static void printCsvLines(List<String[]> lines){


        System.out.println("-------------");
        IntStream.range(0, lines.size())
                .mapToObj((index) -> { //Utilizar o mapToObj pois ao contrário do map(), aceita uma função Function. o Map só aceita UnaryOperation
                    String[] array = lines.get(index);
                    return "[" + index + "] = " + Arrays.toString(array);
                })
                .forEach(System.out::println);

        /*Podem ser utilizados caso não queira printar os indices da Lista*/
        /*
        lines.stream()
                .map((x) -> String.join(", ", x)
                ).forEach(System.out::println);


        lines.stream()
                .map(Arrays::toString)
                .forEach(System.out::println);

         */
    }
    public static  void printFileFormat(Map<String, Character> fileFormat){
        System.err.println(fileFormat);
    }
}
