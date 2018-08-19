package users.db;

public class KeyConverter {
    public static String toKey(String raw) {
        StringBuilder sb = new StringBuilder();
        for (char c : raw.toCharArray()) {
            if (c == '.') {
                sb.append("_P");
            } else if (c == '$') {
                sb.append("_D");
            } else if (c == '#') {
                sb.append("_H");
            } else if (c == '[') {
                sb.append("_O");
            } else if (c == ']') {
                sb.append("_C");
            } else if (c == '/') {
                sb.append("_S");
            } else if (c == '_') {
                sb.append("__");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String toRaw(String key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < key.length() - 1; i++) {
            char c = key.charAt(i);
            if (c == '_') {
                i++;
                c = key.charAt(i);
                if (c == 'P') {
                    sb.append('.');
                } else if (c == 'D') {
                    sb.append('$');
                } else if (c == 'H') {
                    sb.append('#');
                } else if (c == 'O') {
                    sb.append('[');
                } else if (c == 'C') {
                    sb.append(']');
                } else if (c == 'S') {
                    sb.append('/');
                } else {
                    // ignore bad encoding
                    sb.append(c);
                }
            } else {
                sb.append(c);
            }
        }
        int lastIndex = key.length() - 1;
        char lastChar = key.charAt(lastIndex);
        // ignore bad encoding
        sb.append(lastChar);
        return sb.toString();
    }
}
