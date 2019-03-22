package org.janalyzer;

import org.janalyzer.gc.GCData;
import org.janalyzer.gc.GCReport;
import org.janalyzer.util.Optional;

import java.io.File;
import java.util.List;

/**
 * @Author: Tboy
 */
public interface Janalyzer {

    Optional<List<GCData>> analyze(String message);

    Optional<GCReport> analyze(File file);
}
