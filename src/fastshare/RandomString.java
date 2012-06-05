package fastshare;

import java.util.Random;

/**
 * Random String generator
 */
public class RandomString {

    /**
     * Symbols' array
     */
    private static final char[] symbols = new char[36];

    static {
        for (int idx = 0; idx < 10; ++idx) {
            symbols[idx] = (char) ('0' + idx);
        }
        for (int idx = 10; idx < 36; ++idx) {
            symbols[idx] = (char) ('a' + idx - 10);
        }
    }
    /**
     * Generated otput string
     */
    private final Random random = new Random();
    /**
     * Additional buffer of char's
     */
    private final char[] buf;

    /**
     * Constructor, generating random string
     * @param length length of generated string
     */
    public RandomString(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("length < 1: " + length);
        }
        buf = new char[length];
    }

    /**
     * Generates next random string
     * @return generated random string
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }
}