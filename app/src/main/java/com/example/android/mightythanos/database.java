package com.example.android.mightythanos;

import java.util.List;

public class database {
    int state=1;
    List<Integer> stoneIndex;
    int status=4;
    public void setStatus(int b){
        state++;
        status = b;
    }
    public void setStoneIndex(List<Integer> a){
        stoneIndex = a;
    }
    public int getStatus(){
        return status;
    }
    public List<Integer> getStoneIndex(){
        return stoneIndex;
    }
    public int getState(){
        return state;
    }
}
