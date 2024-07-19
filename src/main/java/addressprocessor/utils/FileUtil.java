package addressprocessor.utils;

import org.apache.commons.io.IOUtils;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileUtil {

    private static final String INPUT_BASE_PATH = "inputNewFiles/";
    private static final String OUTPUT_BASE_PATH = "inputUtf8Files/";

    public static String getInputBasePath(){
        return INPUT_BASE_PATH;
    }

    public static String getOutputBasePath(){
        return OUTPUT_BASE_PATH;
    }

    public static void encodingConverter(String fileName){

        String inputFilePath = INPUT_BASE_PATH + fileName;
        String outputFilePath = OUTPUT_BASE_PATH + fileName;
        try {
            // Step 1: Detect the encoding of the input file
            Charset detectedCharset = detectCharset(inputFilePath);

            System.out.println(detectedCharset.toString());

            // Step 2: Read the CSV file with the detected encoding
            String csvContent = readFile(inputFilePath, detectedCharset);

            // Step 3: Write the CSV file with the desired encoding (UTF-8)
            writeFile(outputFilePath, csvContent, StandardCharsets.UTF_8);

            System.out.println("File converted successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Charset detectCharset(String filePath) throws IOException {
        byte[] buf = new byte[4096];
        try (FileInputStream fis = new FileInputStream(filePath)) {
            UniversalDetector detector = new UniversalDetector(null);
            int nread;
            while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            detector.reset();
            return encoding != null ? Charset.forName(encoding) : StandardCharsets.UTF_8;
        }
    }

    private static String readFile(String filePath, Charset charset) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath);
             Reader reader = new InputStreamReader(inputStream, charset)) {
            return IOUtils.toString(reader);
        }
    }


    private static void writeFile(String filePath, String content, Charset charset) throws IOException {
        File outputFile = new File(filePath);
        File parentDir = outputFile.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Failed to create directory " + parentDir);
            }
        }

        try (OutputStream outputStream = new FileOutputStream(outputFile);
             Writer writer = new OutputStreamWriter(outputStream, charset)) {
            IOUtils.write(content, writer);
        }
    }

}
