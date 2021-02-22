package com.java.design.pattern.adapter;

/**
 * 适配器测试
 */
public class AdapterTest {

    public static void main(String[] args) {
        //220v电源接口
        Cn220vInterface cn220vInterface = new Cn220vInterfaceImpl();
        //适配器接口(在220v的电压下使用110v的设备)
        PowerAdapter powerAdapter = new PowerAdapter(cn220vInterface);
        //电饭煲
        ElectricCooker electricCooker = new ElectricCooker(powerAdapter);
        electricCooker.cook();
    }
}
