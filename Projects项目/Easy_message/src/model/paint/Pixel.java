package model.paint;

import javafx.scene.paint.Color;

public class Pixel {
    private String property_1;
    private String property_2;
    private String property_3;
    private String property_4;
//    private Color property_4=null;
//    private String property_5;

    /*public Pixel(String property_1, String property_2, String property_3, Color property_4, String property_5) {
        this.property_1 = property_1;
        this.property_2 = property_2;
        this.property_3 = property_3;
        this.property_4 = property_4;
        this.property_5 = property_5;
    }*/

    public Pixel(String property_1, String property_2, String property_3, String property_4) {
        this.property_1 = property_1;
        this.property_2 = property_2;
        this.property_3 = property_3;
        this.property_4 = property_4;
    }

    public String getProperty_1() {
        return property_1;
    }

    public void setProperty_1(String property_1) {
        this.property_1 = property_1;
    }

    public String getProperty_2() {
        return property_2;
    }

    public void setProperty_2(String property_2) {
        this.property_2 = property_2;
    }

    public String getProperty_3() {
        return property_3;
    }

    public void setProperty_3(String property_3) {
        this.property_3 = property_3;
    }

    public String getProperty_4() {
        return property_4;
    }

    public void setProperty_4(String property_4) {
        this.property_4 = property_4;
    }
}
