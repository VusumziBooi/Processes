package com.example.myapplication;

import java.util.ArrayList;

public class Processors {
    public String technique , metal;
    public Integer tensileMin , tensileMax , elongationMin ,
            elongationMax , yieldMin , yieldMax ,
            resolutionMin , resolutionMax , Accuracy , utilization , finish , counter = 0;


  /*  public Processors(String technique ,String metal , int tensileMin ,
            int tensileMax , int elongationMin , int elongationMax ,
            int yieldMin , int yieldMax , int resolutionMin , int resolutionMax ,
            int Accuracy , int utilization , int finish){
        this.technique = technique;
        this.metal = metal;
        this.tensileMin = tensileMin;
        this.tensileMax = tensileMax;
        this.elongationMin = elongationMin;
        this.elongationMax = elongationMax;
        this.yieldMin = yieldMin;
        this.yieldMax = yieldMax;
        this.resolutionMin = resolutionMin;
        this.resolutionMax = resolutionMax;
        this.Accuracy = Accuracy;
        this.utilization  = utilization;
        this.finish = finish;

    }*/

    public Processors(String technique, String metal, int tmin, int tmax, int emin , int emax , int ymin, int ymax, int rmin, int rmax, int acc, int util, int fin) {
        this.technique = technique;
        this.metal = metal;
        this.tensileMin = tmin;
        this.tensileMax = tmax;
        this.elongationMin = emin;
        this.elongationMax = emax;
        this.yieldMin = ymin;
        this.yieldMax = ymax;
        this.resolutionMin = rmin;
        this.resolutionMax = rmax;
        this.Accuracy = acc;
        this.utilization  = util;
        this.finish = fin;
    }

    public void checkThem(int tensile , int elongation , int yield , int resolution , int accuracy , int utilization , int finish){
        if(tensile >= tensileMin && tensile <= tensileMax){
            counter++;
        }
        if(elongation >= elongationMin && elongation <= elongationMax){
            counter++;
        }
        if(yield >= yieldMin && yield <= yieldMax){
            counter++;
        }
        if(resolution >= resolutionMin && resolution <= resolutionMax){
            counter++;
        }
        if(accuracy == Accuracy){
            counter++;
        }
        if(utilization == this.utilization){
            counter++;
        }
        if(finish == this.finish){
            counter++;
        }
    }


}
