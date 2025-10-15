import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите режим:");
        System.out.println("1 — Зашифровать");
        System.out.println("2 — Расшифровать (известен сдвиг)");
        System.out.println("3 — Brute Force (перебор всех сдвигов)");
        int mode = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Имя входного файла (по умолчанию: input.txt): ");
        String inputFile = scanner.nextLine().trim();
        if (inputFile.isEmpty()) inputFile = "input.txt";

        Path inputPath = Paths.get(inputFile);
        if (!Files.exists(inputPath)) {
            System.err.println("❌ Файл не найден: " + inputPath.toAbsolutePath());
            return;
        }

        String text;
        try {
            text = Files.readString(inputPath);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            return;
        }

        if (mode == 1 || mode == 2) {
            System.out.print("Имя выходного файла (по умолчанию: output.txt): ");
            String outputFile = scanner.nextLine().trim();
            if (outputFile.isEmpty()) outputFile = "output.txt";

            System.out.println("Выберите язык:");
            System.out.println("1 — Английский");
            System.out.println("2 — Русский");
            int langChoice = scanner.nextInt();

            System.out.print("Введите сдвиг (целое число): ");
            int shift = scanner.nextInt();

            String result;
            if (mode == 1) {
                if (langChoice == 1) {
                    result = EncryptEnglish.encryptEnglish(text, shift);
                } else if (langChoice == 2) {
                    result = EncryptRussian.encryptRussian(text, shift);
                } else {
                    System.err.println("Неверный выбор языка.");
                    return;
                }
            } else {
                if (langChoice == 1) {
                    result = DecryptEnglish.decryptEnglish(text, shift);
                } else if (langChoice == 2) {
                    result = DecryptRussian.decryptRussian(text, shift);
                } else {
                    System.err.println("Неверный выбор языка.");
                    return;
                }
            }

            try {
                Files.writeString(Paths.get(outputFile), result);
                System.out.println("✅ Результат записан в: " + Paths.get(outputFile).toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Ошибка записи: " + e.getMessage());
            }

        } else if (mode == 3) {
            System.out.print("Имя выходного файла (по умолчанию: brute_force_output.txt): ");
            String outputFile = scanner.nextLine().trim();
            if (outputFile.isEmpty()) outputFile = "brute_force_output.txt";

            System.out.println("Выберите язык для перебора:");
            System.out.println("1 — Английский");
            System.out.println("2 — Русский");
            int langChoice = scanner.nextInt();

            List<String> results;
            if (langChoice == 1) {
                results = BruteForce.bruteForceEnglish(text);
            } else if (langChoice == 2) {
                results = BruteForce.bruteForceRussian(text);
            } else {
                System.err.println("Неверный выбор языка.");
                return;
            }

            BruteForce.saveResultsToFile(results, outputFile);

        } else {
            System.err.println("Неверный выбор режима.");
        }
    }
}