package org.janalyzer.gc;

import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.*;


/**
 * @Author: Tboy
 */
public abstract class CommonGCAction implements Phase, GCAction {

    private static final String DATETIME_ACTION =
            "(?<" + DATETIME + ">\\d{4}(-\\d{2}){2}T(\\d{2}:){2}\\d{2}\\.\\d{3}[-|+]\\d{4})\\s*";

    private static final String TIMES_ACTION =
            "\\s*\\[Times:" +
            "\\suser=(?<" + USER_TIME + ">\\d+\\.\\d+)" +
            "\\s*sys=(?<" + SYS_TIME + ">\\d+\\.\\d+)," +
            "\\sreal=(?<" + REAL_TIME + ">\\d+\\.\\d+)\\ssecs\\]";

    private static final Pattern DATETIME_PATTERN = Pattern.compile(DATETIME_ACTION);

    private static final Pattern TIMES_PATTERN = Pattern.compile(TIMES_ACTION);

    public void action(String message, GCData data){
        Optional<String> datetime = matchDatetime(message);
        if(datetime.isPresent()){
            data.setDatetime(datetime.get());
        }
        Optional<GCTime> times = matchTimes(message);
        if(times.isPresent()){
            data.setGcTime(times.get());
        }
    }

    protected Optional<String> matchDatetime(String message){
        //
        Matcher matcher = DATETIME_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }
        String datetime;

        return StringUtils.isNotEmpty(datetime = matcher.group(DATETIME)) ? Optional.of(datetime) : Optional.empty();
    }

    protected Optional<GCTime> matchTimes(String message){
        if(!message.contains(TIMES)){
            return Optional.empty();
        }
        Matcher matcher = TIMES_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }

        GCTime gcTime = new GCTime();

        String userTime;
        if (StringUtils.isNotEmpty(userTime = matcher.group(USER_TIME))) {
            gcTime.setUser(userTime);
        }
        String sysTime;
        if (StringUtils.isNotEmpty(sysTime = matcher.group(SYS_TIME))) {
            gcTime.setSys(sysTime);
        }
        String realTime;
        if (StringUtils.isNotEmpty(realTime = matcher.group(REAL_TIME))) {
            gcTime.setReal(realTime);
        }
        return Optional.of(gcTime);
    }
}
