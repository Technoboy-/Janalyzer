package org.janalyzer.gc;

import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.*;


/**
 * @Author: Tboy
 */
public abstract class CommonGCAction implements GCAction {

    private static final String DATETIME_ACTION =
            "(?<" + DATETIME + ">\\d{4}(-\\d{2}){2}T(\\d{2}:){2}\\d{2}\\.\\d{3}[-|+]\\d{4}:\\s.*)\\s*";

    private static final String TIMES_ACTION =
            "\\s*\\[Times:" +
            "\\suser=(?<" + USER_TIME + ">\\d+\\.\\d+)" +
            "\\s*sys=(?<" + SYS_TIME + ">\\d+\\.\\d+)," +
            "\\sreal=(?<" + REAL_TIME + ">\\d+\\.\\d+)\\ssecs\\]";

    private static final Pattern DATETIME_PATTERN = Pattern.compile(DATETIME_ACTION);

    private static final Pattern TIMES_PATTERN = Pattern.compile(TIMES_ACTION);

    public void doCommonAction(String message, GCData data){
        matchDatetime(message, data);
        matchTimes(message, data);
    }

    public void matchDatetime(String message, GCData data){
        //
        Matcher matcher = DATETIME_PATTERN.matcher(message);
        if (!matcher.find()) {
            return;
        }
        String datetime;
        if (StringUtils.isNotEmpty(datetime = matcher.group(DATETIME))) {
            data.addProperties(DATETIME, datetime);
        }
    }

    public void matchTimes(String message, GCData data){
        //
        Matcher matcher = TIMES_PATTERN.matcher(message);
        if (!matcher.find()) {
            return;
        }
        String userTime;
        if (StringUtils.isNotEmpty(userTime = matcher.group(USER_TIME))) {
            data.addProperties(USER_TIME, userTime);
        }
        String sysTime;
        if (StringUtils.isNotEmpty(sysTime = matcher.group(SYS_TIME))) {
            data.addProperties(SYS_TIME, sysTime);
        }
        String realTime;
        if (StringUtils.isNotEmpty(realTime = matcher.group(REAL_TIME))) {
            data.addProperties(REAL_TIME, realTime);
        }
    }
}
