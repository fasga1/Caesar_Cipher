public class EncryptEnglish {
    public static String encryptEnglish(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                boolean isLowerCase = Character.isLowerCase(c);
                char upperChar = Character.toUpperCase(c);
                int index = Alphabet.ENGLISH_ALPHABET.indexOf(upperChar);
                if (index != -1) {
                    int newIndex = (index + shift) % 26;
                    if (newIndex < 0) newIndex += 26; 
                    char newChar = Alphabet.ENGLISH_ALPHABET.charAt(newIndex);
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
