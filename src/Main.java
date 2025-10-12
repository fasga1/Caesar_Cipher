import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Выбор режима
        System.out.println("Выберите режим:");
        System.out.println("1 — Зашифровать");
        System.out.println("2 — Расшифровать");
        int mode = scanner.nextInt();
        scanner.nextLine(); // поглотить \n

        // Имена файлов
        System.out.print("Имя входного файла (по умолчанию: input.txt): ");
        String inputFile = scanner.nextLine().trim();
        if (inputFile.isEmpty()) inputFile = "input.txt";

        System.out.print("Имя выходного файла (по умолчанию: output.txt): ");
        String outputFile = scanner.nextLine().trim();
        if (outputFile.isEmpty()) outputFile = "output.txt";

        // Язык
        System.out.println("Выберите язык:");
        System.out.println("1 — Английский");
        System.out.println("2 — Русский");
        int langChoice = scanner.nextInt();

        // Сдвиг
        System.out.print("Введите сдвиг (целое число): ");
        int shift = scanner.nextInt();

        // Проверка существования входного файла
        Path inputPath = Paths.get(inputFile);
        if (!Files.exists(inputPath)) {
            System.err.println("❌ Файл не найден: " + inputPath.toAbsolutePath());
            return;
        }

        // Чтение текста
        String text;
        try {
            text = Files.readString(inputPath);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            return;
        }

        // Обработка
        String result;
        if (mode == 1) {
            // Шифрование
            if (langChoice == 1) {
                result = EncryptEnglish.encryptEnglish(text, shift);
            } else if (langChoice == 2) {
                result = EncryptRussian.encryptRussian(text, shift);
            } else {
                System.err.println("Неверный выбор языка.");
                return;
            }
        } else if (mode == 2) {
            // Расшифровка
            if (langChoice == 1) {
                result = DecryptEnglish.decryptEnglish(text, shift);
            } else if (langChoice == 2) {
                result = DecryptRussian.decryptRussian(text, shift);
            } else {
                System.err.println("Неверный выбор языка.");
                return;
            }
        } else {
            System.err.println("Неверный выбор режима.");
            return;
        }

        // Запись результата
        try {
            Files.writeString(Paths.get(outputFile), result);
            System.out.println("✅ Результат записан в: " + Paths.get(outputFile).toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Ошибка записи: " + e.getMessage());
        }
    }
}