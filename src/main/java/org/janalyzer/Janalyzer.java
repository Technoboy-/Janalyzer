package org.janalyzer;

import org.janalyzer.gc.GCData;
import org.janalyzer.gc.GCReport;
import org.janalyzer.util.Optional;

import java.io.File;

/**
 * @Author: Tboy
 */
public interface Janalyzer {

    Optional<GCData> analyze(String message);

    Optional<GCReport> analyze(File file);
}
