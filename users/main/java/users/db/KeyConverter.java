package users.db;

import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.Char2CharOpenHashMap;

public class KeyConverter {
    private static final Char2CharMap ENCODED = new Char2CharOpenHashMap();
    private static final Char2CharMap DECODED = new Char2CharOpenHashMap();

    static {
        addMapping('.', 'P');
        addMapping('$', 'D');
        addMapping('#', 'H');
        addMapping('[', 'O');
        addMapping(']', 'C');
        addMapping('/', 'S');
        addMapping('_', 'U');
    }

    private static void addMapping(char raw, char encoded) {
        ENCODED.put(raw, encoded);
        DECODED.put(encoded, raw);
    }

    public static String toKey(String raw) {
        StringBuilder sb = new StringBuilder();
        for (char encoded : raw.toCharArray()) {
            char replacement = ENCODED.getOrDefault(encoded, encoded);
            if (encoded != replacement) {
                sb.append('_');
            }
            sb.append(replacement);
        }
        return sb.toString();
    }

    public static String toRaw(String key) {
        int lastIndex = key.length() - 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lastIndex; i++) {
            char decoded = key.charAt(i);
            if (decoded == '_') {
                i++;
                decoded = key.charAt(i);
                decoded = DECODED.getOrDefault(decoded, decoded);
            }
            sb.append(decoded);
        }
        char lastChar = key.charAt(lastIndex);
        // ignore bad encoding
        sb.append(lastChar);
        return sb.toString();
    }
}
