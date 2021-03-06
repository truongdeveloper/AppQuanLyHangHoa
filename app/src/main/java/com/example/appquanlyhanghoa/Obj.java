package com.example.appquanlyhanghoa;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Obj {
    private int id, quantity;

    private String name,type,unit,dscribe;

    public Obj(){

    }

    public Obj(int id, String name, int quantity, String type, String unit, String dscribe){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this. type = type;
        this. unit = unit;
        this.dscribe = dscribe;
    }

    public int getID() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDscribe() {
        return dscribe;
    }
    public void setDscribe(String dscribe) {
        this.dscribe = dscribe;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
        public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("name",name );
        result.put("type",type);
        result.put("quantity",quantity);
        result.put("unit",unit);
        result.put("dscribe",dscribe);
        return result;
    }

}
