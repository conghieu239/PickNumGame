package com.example.myapplication;

import java.util.Collections;
import java.util.List;

public class OrderCells {

    public  List<Integer>  list;

    public OrderCells(List<Integer> list){
        this.list = list;
        Collections.shuffle(list);
    }
    public int getNumber(){
        if (!list.isEmpty()){
            return list.remove(0);
        }
        return -1;
    }
}
