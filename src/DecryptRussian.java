
public class DecryptRussian {
    public static String decryptRussian(String text, int shift) {
        return EncryptRussian.encryptRussian(text, -shift);
    }
}
