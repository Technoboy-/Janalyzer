package org.janalyzer.gc.g1.phase;

import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.Phase;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.*;

/**
 * @Author: Tboy
 */
public class G1RootRegionScanPhase implements Phase<Optional<GCPhase>> {

    private static final String ROOT_REGION_SCAN_PHASE =
            ".*\\[GC\\s" + G1_ROOT_REGION_SCAN +
            ",\\s(?<" + G1_ROOT_REGION_SCAN_DURATION + ">\\d+.\\d+)\\ssecs\\]";

    private static final Pattern ROOT_REGION_SCAN_PATTERN = Pattern.compile(ROOT_REGION_SCAN_PHASE);

    @Override
    public Optional<GCPhase> action(String message) {
        if(!message.contains(G1_ROOT_REGION_SCAN)){
            return Optional.empty();
        }
        Matcher matcher = ROOT_REGION_SCAN_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }

        GCPhase phase = new GCPhase(name());

        String rootRegionScanDuration;
        if (StringUtils.isNotEmpty(rootRegionScanDuration = matcher.group(G1_ROOT_REGION_SCAN_DURATION))) {
            phase.addProperties(G1_ROOT_REGION_SCAN_DURATION, rootRegionScanDuration);
        }
        //
        return Optional.of(phase);
    }

    @Override
    public String name() {
        return G1Phase.G1_ROOT_REGION_SCAN.name();
    }
}
