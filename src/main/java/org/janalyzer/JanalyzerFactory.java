package org.janalyzer;


import org.janalyzer.gc.GCType;
import org.janalyzer.util.Collector;

/**
 * @Author: Tboy
 */
public class JanalyzerFactory {

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private Collector<GCType, GCType> collector;

        public Janalyzer build(){
            return new JanalyzerImpl(this);
        }

        public Builder withCollecor(Collector<GCType, GCType> collector){
            this.collector = collector;
            return this;
        }

        public Collector<GCType, GCType> getCollector() {
            return collector;
        }

    }
}
