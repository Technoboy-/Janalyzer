package org.janalyzer.util;

import java.util.Collection;

/**
 * @Author: Tboy
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection<?> col){
        return col == null || col.size() == 0;
    }

    public static boolean isNotEmpty(Collection<?> col){
        return !isEmpty(col);
    }
}
