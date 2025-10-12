public class DecryptEnglish {
    public static String decryptEnglish(String text, int shift) {
        return EncryptEnglish.encryptEnglish(text, -shift);
    }
}