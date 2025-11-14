package caesar.cipher;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CaesarCipherApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Шифр Цезаря — GUI");

        // Элементы интерфейса
        TextArea inputArea = new TextArea();
        inputArea.setPromptText("Введите текст для шифрования/расшифровки...");
        inputArea.setWrapText(true);

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPromptText("Результат появится здесь...");

        TextField shiftField = new TextField();
        shiftField.setPromptText("Сдвиг (целое число)");
        shiftField.setText("3");

        ComboBox<String> modeCombo = new ComboBox<>();
        modeCombo.getItems().addAll("Зашифровать", "Расшифровать", "Brute Force");
        modeCombo.setValue("Зашифровать");

        ComboBox<String> languageCombo = new ComboBox<>();
        languageCombo.getItems().addAll("Английский", "Русский");
        languageCombo.setValue("Английский");

        Button executeButton = new Button("Выполнить");
        Button saveButton = new Button("Сохранить результат в файл");
        saveButton.setDisable(true);

        // Логика кнопки "Выполнить"
        executeButton.setOnAction(e -> {
            String input = inputArea.getText();
            String mode = modeCombo.getValue();
            String language = languageCombo.getValue();
            int shift = 0;

            if (!mode.equals("Brute Force")) {
                try {
                    shift = Integer.parseInt(shiftField.getText());
                } catch (NumberFormatException ex) {
                    showError("Ошибка", "Сдвиг должен быть целым числом!");
                    return;
                }
            }

            String result = "";
            try {
                if (mode.equals("Зашифровать")) {
                    if (language.equals("Английский")) {
                        result = EncryptEnglish.encryptEnglish(input, shift);
                    } else {
                        result = EncryptRussian.encryptRussian(input, shift);
                    }
                } else if (mode.equals("Расшифровать")) {
                    if (language.equals("Английский")) {
                        result = DecryptEnglish.decryptEnglish(input, shift);
                    } else {
                        result = DecryptRussian.decryptRussian(input, shift);
                    }
                } else if (mode.equals("Brute Force")) {
                    if (language.equals("Английский")) {
                        List<String> variants = BruteForce.bruteForceEnglish(input);
                        result = String.join("\n", variants);
                    } else {
                        List<String> variants = BruteForce.bruteForceRussian(input);
                        result = String.join("\n", variants);
                    }
                }
                outputArea.setText(result);
                saveButton.setDisable(false);
            } catch (Exception ex) {
                showError("Ошибка выполнения", ex.getMessage());
            }
        });

        // Логика кнопки "Сохранить"
        saveButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Сохранить результат");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"));
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(outputArea.getText());
                    showAlert("Успех", "Файл успешно сохранён!");
                } catch (IOException ex) {
                    showError("Ошибка записи", "Не удалось сохранить файл: " + ex.getMessage());
                }
            }
        });

        // Разметка
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        grid.add(new Label("Режим:"), 0, 0);
        grid.add(modeCombo, 1, 0);
        grid.add(new Label("Язык:"), 0, 1);
        grid.add(languageCombo, 1, 1);
        grid.add(new Label("Сдвиг:"), 0, 2);
        grid.add(shiftField, 1, 2);

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                new Label("Входной текст:"),
                inputArea,
                grid,
                executeButton,
                new Label("Результат:"),
                outputArea,
                saveButton
        );

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}