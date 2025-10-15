import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BruteForce {

    public static List<String> bruteForceEnglish(String cipherText) {
        List<String> results = new ArrayList<>();
        for (int shift = 1; shift <= 25; shift++) {
            String decrypted = DecryptEnglish.decryptEnglish(cipherText, shift);
            results.add("Сдвиг " + shift + ": " + decrypted);
        }
        return results;
    }

    public static List<String> bruteForceRussian(String cipherText) {
        List<String> results = new ArrayList<>();
        for (int shift = 1; shift <= 32; shift++) {
            String decrypted = DecryptRussian.decryptRussian(cipherText, shift);
            results.add("Сдвиг " + shift + ": " + decrypted);
        }
        return results;
    }

    public static void saveResultsToFile(List<String> results, String outputFile) {
        try {
            Files.write(Paths.get(outputFile), results);
            System.out.println("Все варианты перебора сохранены в: " + Paths.get(outputFile).toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Ошибка записи файла: " + e.getMessage());
        }
    }
}