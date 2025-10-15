public class EncryptRussian {
    public static String encryptRussian(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                boolean isLowerCase = Character.isLowerCase(c);
                char upperChar = Character.toUpperCase(c);
                int index = Alphabet.RUSSIAN_ALPHABET.indexOf(upperChar);
                if (index != -1) {
                    int newIndex = (index + shift) % 32;
                    if (newIndex < 0) newIndex += 32; 
                    char newChar = Alphabet.RUSSIAN_ALPHABET.charAt(newIndex);
                    result.append(isLowerCase ? Character.toLowerCase(newChar) : newChar);
                } else {
                    result.append(c); 
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
