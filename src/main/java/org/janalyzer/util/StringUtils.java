package org.janalyzer.util;

/**
 * @Author: Tboy
 */
public class StringUtils {

    public static boolean isEmpty(String str){
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

}
