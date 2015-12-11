package com.move10x.totem.models;

/**
 * Created by rahul on 05-12-2015.
 */
public class Remark {
    private String remark;
    private String time;

    public String getRemark(){
        return remark;
    }

    public String getTime(){
        return time;
    }

    public void setRemark(String remark){
        this.remark=remark;
    }
    public void setTime(String time){
        this.time=time;
    }

    public static Remark addNewRemark(String remark,String time){
        Remark remarkObject = new Remark();
        remarkObject.time=time;
        remarkObject.remark=remark;
        return remarkObject;
    }
}
