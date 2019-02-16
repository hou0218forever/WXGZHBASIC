package com.hzb.util;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * emoji表情的工具类
 * 工具类中是将emoji转换为unicode编码，可以将其替换为图片，或者用空格过滤掉。
 */
public class EmojiUtil {

    /**
     * 显示不可见字符的Unicode
     *
     * @param input
     * @return
     */
    public static String escapeUnicode(String input) {
        StringBuilder sb = new StringBuilder(input.length());
        @SuppressWarnings("resource")
        Formatter format = new Formatter(sb);
        for (char c : input.toCharArray()) {
            if (c < 128) {
                sb.append(c);
            } else {
                format.format("\\u%04x", (int) c);
            }
        }
        return sb.toString();
    }

    /**
     * 将emoji替换为unicode
     *
     * @param source
     * @return
     */
    public  String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ue000-\uefff]", Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            Map<String, String> tmpMap = new HashMap<>();
            while (emojiMatcher.find()) {
                String key = emojiMatcher.group();
                String value = escapeUnicode(emojiMatcher.group());
                tmpMap.put(key, value);
            }
            if (!tmpMap.isEmpty()) {
                for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
                    String key = entry.getKey().toString();
                    String value = entry.getValue().toString();
                    source = source.replace(key, value);
                }
            }
        }
        return source;
    }
}

