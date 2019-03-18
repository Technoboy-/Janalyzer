package org.janaylzer.gc.parnew;

import org.janaylzer.gc.GCAction;
import org.janaylzer.gc.GCData;

import java.util.regex.Pattern;

import static org.janaylzer.util.Constants.*;

/**
 * @Author: Tboy
 */
public class ParNewGCAction implements GCAction {

    public static final String PARNEW_ACTION = ".*" +
            "((\\[GC\\s\\((?<" +
            PARNEW_CAUTION +
            ">[\\w ]+)\\).*?\\[)|(\\[GC.*?\\[))"+
            PARNEW +
            "((:\\s)|(.*\\]:\\s))(?<" +
            PARNEW_YOUNG_USAGE_BEFORE +
            ">\\d+\\w)->(?<" +
            PARNEW_YOUNG_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            PARNEW_YOUNG_SIZE +
            ">\\d+\\w)\\),\\s(?<" +
            PARNEW_CLEANUP_DURATION +
            ">\\d+\\.\\d+)\\ssecs\\]\\s(?<" +
            HEAP_USAGE_BEFORE +
            ">\\d+\\w)->(?<" +
            HEAP_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            HEAP_SIZE +
            ">\\d+\\w)\\),\\s(?<" +
            PARNEW_DURATION +
            ">\\d+\\.\\d+)\\ssecs\\]\\s*";

    private static final Pattern PARNEW_PATTERN = Pattern.compile(PARNEW_ACTION);

    public void action(String message, GCData data){

    }
}
