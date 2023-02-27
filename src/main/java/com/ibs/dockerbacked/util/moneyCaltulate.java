package com.ibs.dockerbacked.util;

public class moneyCaltulate {

    public static double common(int cpu,int memory,int network,int disk,int portsize){
        return cpu*2+memory*2+network/10+disk/10+portsize*10;
    }


}
