package org.janalyzer;


import org.janalyzer.gc.GCType;
import org.janalyzer.util.JDK;
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

        private JDK jdk;

        public Janalyzer build(){
            return new JanalyzerImpl(this);
        }

        public Builder withDefault(){
            this.jdk = JDK.JDK8;
            this.collector = new Collector<>(GCType.G1, GCType.G1);
            return this;
        }

        public Builder withJDK(JDK jdk){
            this.jdk = jdk;
            return this;
        }

        public Builder plugin(Collector<GCType, GCType> collector){
            this.collector = collector;
            return this;
        }

        public Collector<GCType, GCType> getCollector() {
            return collector;
        }

        public JDK getJdk() {
            return jdk;
        }

    }

}
