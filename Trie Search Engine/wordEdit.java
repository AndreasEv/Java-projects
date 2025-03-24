package project231;

public class wordEdit {
    
    public static String extractWords(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        String result = "";
        String word = "";

        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);

            if (current == ' ') {
                if (!word.isEmpty()) {
                    if (isValidWord(word)) {
                        word = cleanWord(word);
                        result += word.toLowerCase() + "\n";
                    }
                    word = "";
                }
            } else {
                word += current;
            }
        }

        if (!word.isEmpty()) {
            if (isValidWord(word)) {
                word = cleanWord(word);
                result += word.toLowerCase() + "\n";
            }
        }

        return result;
    }

    protected static String cleanWord(String word) {
        String cleanedWord = "";
        for (int i = 0; i < word.length(); i++) {
            char current = word.charAt(i);
            if (Character.isLetter(current)) {
                cleanedWord += current;
            }
        }
        return cleanedWord;
    }

    private static boolean isValidWord(String word) {
        for (int i = 0; i < word.length(); i++) {
            char current = word.charAt(i);

            if (!Character.isLetter(current)) {
                if (i > 0 && i < word.length() - 1) {
                    return false;
                }
            }
        }
        return true;
    }
}
