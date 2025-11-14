module Caesar_Cipher {
    requires javafx.controls;
    requires javafx.fxml;

    opens caesar.cipher to javafx.fxml;
    exports caesar.cipher;
}