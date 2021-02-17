package cn.xjjdog.bcmall.utils.utils;

public class IdUtil {
    final static char[] digits = {'0', '1', '3', '2', '4', '7', '6', '5', '8',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', '1', '0',};

    public static String genSpecCode() {
        long code = Snowflake.createId();
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << 6;
        long mask = radix - 1;
        do {
            buf[--charPos] = digits[(int) (code & mask)];
            code >>>= 6;
        } while (code != 0);
        return new String(buf, charPos, (64 - charPos));
    }

    public static String genCommonId() {
        return "" + Snowflake.createId();
    }
}
