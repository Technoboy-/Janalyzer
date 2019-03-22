package org.janalyzer.gc.g1.phase;

import org.janalyzer.gc.CommonGCAction;
import org.janalyzer.gc.GCData;
import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.GCType;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.G1_ROOT_REGION_SCAN;
import static org.janalyzer.util.Constants.G1_ROOT_REGION_SCAN_DURATION;

/**
 * @Author: Tboy
 */
public class G1RootRegionScanGCPhase extends CommonGCAction {

    private static final String ROOT_REGION_SCAN_PHASE =
            ".*\\[GC\\s" + G1_ROOT_REGION_SCAN +
            ",\\s(?<" + G1_ROOT_REGION_SCAN_DURATION + ">\\d+.\\d+)\\ssecs\\]";

    private static final Pattern ROOT_REGION_SCAN_PATTERN = Pattern.compile(ROOT_REGION_SCAN_PHASE);

    @Override
    public boolean match(String message) {
        if(!message.contains(G1_ROOT_REGION_SCAN)){
            return false;
        }
        Matcher matcher = ROOT_REGION_SCAN_PATTERN.matcher(message);

        return matcher.find();
    }

    @Override
    public void doAction(String message, GCData gcData) {
        //
        Matcher matcher = ROOT_REGION_SCAN_PATTERN.matcher(message);
        matcher.find();
        //
        String rootRegionScanDuration;
        if (StringUtils.isNotEmpty(rootRegionScanDuration = matcher.group(G1_ROOT_REGION_SCAN_DURATION))) {
            gcData.addProperties(G1_ROOT_REGION_SCAN_DURATION, rootRegionScanDuration);
        }
    }

    @Override
    public GCType type() {
        return GCType.G1;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.of(new GCPhase(G1Phase.G1_ROOT_REGION_SCAN.name(), G1Phase.G1_ROOT_REGION_SCAN.isSTW()));
    }
}
