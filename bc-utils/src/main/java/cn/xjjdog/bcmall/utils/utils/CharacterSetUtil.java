package cn.xjjdog.bcmall.utils.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JerriLiu
 */
public class CharacterSetUtil {
    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串中是否包含韩文
     *
     * @param str 待校验字符串
     * @return 是否为韩文
     * @warn 不能校验是否为韩文标点符号
     */
    public static boolean isContainKorea(String str) {
        Pattern p = Pattern.compile("[\uac00-\ud7ff]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串中是否包含日文
     *
     * @param str 待校验字符串
     * @return 是否为日文
     * @warn 不能校验是否为日文标点符号
     */
    public static boolean isContainJapan(String str) {
        Pattern p = Pattern.compile("[\u0800-\u4e00]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 过滤掉中文
     *
     * @param str 待过滤中文的字符串
     * @return 过滤掉中文后字符串
     */
    public static String filterChinese(String str) {
        // 用于返回结果
        String result = str;
        boolean flag = isContainChinese(str);
        if (flag) {// 包含中文
            // 用于拼接过滤中文后的字符
            StringBuffer sb = new StringBuffer();
            // 用于校验是否为中文
            boolean flag2 = false;
            // 用于临时存储单字符
            char chinese = 0;
            // 5.去除掉文件名中的中文
            // 将字符串转换成char[]
            char[] charArray = str.toCharArray();
            // 过滤到中文及中文字符
            for (int i = 0; i < charArray.length; i++) {
                chinese = charArray[i];
                flag2 = isChinese(chinese);
                if (!flag2) {// 不是中日韩文字及标点符号
                    sb.append(chinese);
                }
            }
            result = sb.toString();
        }
        return result;
    }


    /**
     * 校验一个字符是否是汉字
     *
     * @param c 被校验的字符
     * @return true代表是汉字
     */
    public static boolean isChineseChar(char c) {
        try {
            return String.valueOf(c).getBytes("UTF-8").length > 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 验证字符串内容是否包含下列非法字符<br>
     * `~!#%^&*=+\\|{};:'\",<>/?○●★☆☉♀♂※¤╬の〆
     *
     * @param content 字符串内容
     * @return 't'代表不包含非法字符，otherwise代表包含非法字符。
     */
    public static char validateLegalString(String content) {
        String illegal = "`~!#%^&*=+\\|{};:'\",<>/?○●★☆☉♀♂※¤╬の〆";
        char isLegalChar = 't';
        L1:
        for (int i = 0; i < content.length(); i++) {
            for (int j = 0; j < illegal.length(); j++) {
                if (content.charAt(i) == illegal.charAt(j)) {
                    isLegalChar = content.charAt(i);
                    break L1;
                }
            }
        }
        return isLegalChar;
    }

    /**
     * 验证是否是汉字或者0-9、a-z、A-Z
     *
     * @param c 被验证的char
     * @return true代表符合条件
     */
    public static boolean isRightChar(char c) {
        return isChinese(c) || isWord(c);
    }

    /**
     * 校验某个字符是否是a-z、A-Z、_、0-9
     *
     * @param c 被校验的字符
     * @return true代表符合条件
     */
    public static boolean isWord(char c) {
        String regEx = "[\\w]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher("" + c);
        return m.matches();
    }

    /**
     * 校验某个字符是否是a-z、A-Z
     *
     * @param str 被校验的字符
     * @return true代表符合条件
     */
    public static boolean isContainEnglishWord(String str) {
        String regEx = ".*[a-zA-Z]+.*";
        return str.matches(regEx);
    }

    /**
     * 校验某个字符是否是纯数字
     *
     * @param str 被校验的字符
     * @return true代表符合条件
     */
    public static boolean isNumeral(String str) {
        String regEx = "^\\d+$";
        return str.matches(regEx);
    }

    /**
     * 判定输入的是否是汉字
     *
     * @param c 被校验的字符
     * @return true代表是汉字
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 校验String是否全是中文
     *
     * @param name 被校验的字符串
     * @return true代表全是汉字
     */
    public static boolean checkNameChinese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }
}
