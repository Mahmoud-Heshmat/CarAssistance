package com.h2m.carassistance.Utitls;

import java.util.ArrayList;
import java.util.List;

public class CarDataList {

    public static List<String> getCarsName(){
        List<String> list = new ArrayList<>();
        list.add("BMW");
        list.add("Audi");
        list.add("Mercedes");
        list.add("Cheverolet");
        list.add("Dodge");
        list.add("Lada");
        list.add("Ford");
        return list;
    }

}
