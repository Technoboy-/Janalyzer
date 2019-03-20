package org.janalyzer;

import org.janalyzer.gc.GCAction;
import org.janalyzer.gc.GCData;
import org.janalyzer.gc.GCReport;
import org.janalyzer.gc.GCType;
import org.janalyzer.gc.cms.CMSGCAction;
import org.janalyzer.gc.g1.G1GCAction;
import org.janalyzer.gc.parallel.old.ParallelOldGCAction;
import org.janalyzer.gc.parallel.scavenge.ParallelScavengeGCAction;
import org.janalyzer.gc.parnew.ParNewGCAction;
import org.janalyzer.gc.serial.SerialGCAction;
import org.janalyzer.gc.serial.SerialOldGCAction;
import org.janalyzer.util.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Tboy
 */
class JanalyzerImpl implements Janalyzer{


    private final ConcurrentHashMap<GCType, GCAction> actionMap = new ConcurrentHashMap<>(3);

    private final List<GCAction> actionList = new ArrayList<>(3);

    public JanalyzerImpl(JanalyzerFactory.Builder builder){

        Collector<GCType, GCType> collector = builder.getCollector();

        if(collector instanceof Collector.All){
            for(GCType type : GCType.values()){
                actionList.add(getGCAction(type).getRight());
            }
        } else{
            Preconditions.checkArgument(collector.getOld() == null, "Collector old should not be empty" );
            Preconditions.checkArgument(GCType.SERIAL == collector.getYoung() && GCType.PARALLEL_OLD == collector.getOld(), "Collector SERIAL and PARALLEL_OLD can not compose pair" );
            Preconditions.checkArgument(GCType.PARNEW == collector.getYoung() && GCType.PARALLEL_OLD == collector.getOld(), "Collector PARNEW and PARALLEL_OLD can not compose pair" );
            Preconditions.checkArgument(GCType.PARALLEL_SCAVENGE == collector.getYoung() && GCType.CMS == collector.getOld(), "Collector PARALLEL_SCAVENGE and CMS can not compose pair" );
            //
            Pair<GCType, GCAction> oldAction = getGCAction(collector.getOld());
            actionMap.putIfAbsent(oldAction.getLeft(), oldAction.getRight());

            //
            Pair<GCType, GCAction> youngAction = getGCAction(collector.getYoung());
            actionMap.putIfAbsent(youngAction.getLeft(), youngAction.getRight());

            if(GCType.CMS == collector.getOld()){
                Pair<GCType, GCAction> parAction = getGCAction(GCType.PARNEW);
                actionMap.putIfAbsent(parAction.getLeft(), parAction.getRight());
                Pair<GCType, GCAction> serialOldAction = getGCAction(GCType.SERIAL_OLD);
                actionMap.putIfAbsent(serialOldAction.getLeft(), serialOldAction.getRight());
            }

            //
            if(builder.getJdk() != null && builder.getJdk().getCode() < JDK.JDK8.getCode()){
                actionMap.remove(GCType.G1);
            }
            //
            if(actionMap.get(GCType.G1) != null){
                actionList.add(actionMap.get(GCType.G1));
            } else{
                actionList.addAll(actionMap.values());
            }
        }
    }

    @Override
    public Optional<GCData> analyze(String message) {
        for(GCAction action : actionList){
            Optional<GCData> result = (Optional<GCData>)action.action(message);
            if(result.isPresent()){
                return result;
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<GCReport> analyze(File file) {
        throw new UnsupportedOperationException("will support soon");
    }


    private Pair<GCType, GCAction> getGCAction(GCType type){
        switch (type){
            case SERIAL:
                return new Pair(type, new SerialGCAction());
            case PARNEW:
                return new Pair(type, new ParNewGCAction());
            case PARALLEL_SCAVENGE:
                return new Pair(type,new ParallelScavengeGCAction());
            case CMS:
                return new Pair(type, new CMSGCAction());
            case SERIAL_OLD:
                return new Pair(type, new SerialOldGCAction());
            case PARALLEL_OLD:
                return new Pair(type, new ParallelOldGCAction());
            case G1:
                return new Pair(type, new G1GCAction());
            default:
                throw new IllegalArgumentException("invalid GCType : " + type);
        }
    }
}
