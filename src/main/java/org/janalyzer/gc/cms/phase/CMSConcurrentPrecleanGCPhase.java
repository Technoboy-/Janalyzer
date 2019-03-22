package org.janalyzer.gc.cms.phase;

import org.janalyzer.gc.CommonGCAction;
import org.janalyzer.gc.GCData;
import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.GCType;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import javax.crypto.Mac;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.CMS_CONCURRENT_PRECLEAN;
import static org.janalyzer.util.Constants.CMS_CONCURRENT_PRECLEAN_DURATION;

/**
 * @Author: Tboy
 */
public class CMSConcurrentPrecleanGCPhase extends CommonGCAction {

    public static final String CMS_CONCURRENT_PRECLEAN_PHASE = ".*" +
            CMS_CONCURRENT_PRECLEAN +
            ":\\s(?<" +
            CMS_CONCURRENT_PRECLEAN_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs]";

    public static final Pattern CMS_CONCURRENT_PRECLEAN_PATTERN = Pattern.compile(CMS_CONCURRENT_PRECLEAN_PHASE);

    @Override
    public boolean match(String message) {
        if(!message.contains(CMS_CONCURRENT_PRECLEAN)){
            return false;
        }
        Matcher matcher = CMS_CONCURRENT_PRECLEAN_PATTERN.matcher(message);

        return matcher.find();
    }

    @Override
    public void doAction(String message, GCData gcData) {
        Matcher matcher = CMS_CONCURRENT_PRECLEAN_PATTERN.matcher(message);
        matcher.find();
        //
        String concurrentPrecleanPhase;
        if (StringUtils.isNotEmpty(concurrentPrecleanPhase = matcher.group(CMS_CONCURRENT_PRECLEAN_DURATION))) {
            gcData.addProperties(CMS_CONCURRENT_PRECLEAN_DURATION, concurrentPrecleanPhase);
        }
    }

    @Override
    public GCType type() {
        return GCType.CMS;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.of(new GCPhase(CMSPhase.CMS_CONCURRENT_PRECLEAN.name(), CMSPhase.CMS_CONCURRENT_PRECLEAN.isSTW()));
    }
}
