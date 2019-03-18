package org.janaylzer.gc.cms;

import org.janaylzer.gc.GCAction;
import org.janaylzer.gc.GCData;
import org.janaylzer.gc.cms.phase.PhaseChain;
import org.janaylzer.util.StringUtils;


/**
 * @Author: Tboy
 */
public class CMSGCAction implements GCAction {

    private final PhaseChain phaseChain = new CMSPhaseChain();

    public void action(String message, GCData data){
        if(StringUtils.isEmpty(message)){
            throw new IllegalArgumentException("message is empty");
        }
        phaseChain.doAction(message, data);
    }
}
