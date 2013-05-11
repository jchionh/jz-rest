package com.appspot.jzrest.util;

public class WordDictQueryResponse {

    // do this for reuse
    private static final int INITIAL_CAPACITY = 128;
    private static final StringBuilder sb = new StringBuilder(INITIAL_CAPACITY);
    private static final String OPEN_PARAN = "{";
    private static final String CLOSE_PARAN = "}";
    private static final String SPACE = " ";
    private static final String COLON = ":";
    private static final String QUOTE = "\"";
    private static final String RESULT = "\"r\":";
    private static final String TIME = "\"t\":\"";
    private static final String COMMA = ",";
    private static final String POSITIVE_RESPONSE = "\"1\"";
    private static final String NEGATIVE_RESPONSE = "\"0\"";
    private static final String COMMA_PLUS_TIME = COMMA + TIME;

    public static String formatJSONString(boolean exist, long queryTime) {
        // clear our sb
        sb.delete(0, sb.length());

        // format the open
        sb.append(OPEN_PARAN);

        // format the result property name
        sb.append(RESULT);

        // true or false?
        sb.append(exist ? POSITIVE_RESPONSE : NEGATIVE_RESPONSE);

        // format the comma
        sb.append(COMMA_PLUS_TIME);

        // format the time
        sb.append(queryTime);

        // close the quote
        sb.append(QUOTE);

        // format the close
        sb.append(CLOSE_PARAN);

        // return the formatted string
        return sb.toString();
    }

}
