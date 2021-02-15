package com.example.codingfaction.Models;

public class Class {

    private String Fan1;
    private String Fan2;
    private String Fan3;
    private String Fan4;
    private String Light1;
    private String Light2;
    private String Mode;

    public Class(String fan1, String fan2, String fan3, String fan4, String light1, String light2, String mode) {
        Fan1 = fan1;
        Fan2 = fan2;
        Fan3 = fan3;
        Fan4 = fan4;
        Light1 = light1;
        Light2 = light2;
        Mode = mode;
    }

    public Class() {
    }

    public String getFan1() {
        return Fan1;
    }

    public void setFan1(String fan1) {
        Fan1 = fan1;
    }

    public String getFan2() {
        return Fan2;
    }

    public void setFan2(String fan2) {
        Fan2 = fan2;
    }

    public String getFan3() {
        return Fan3;
    }

    public void setFan3(String fan3) {
        Fan3 = fan3;
    }

    public String getFan4() {
        return Fan4;
    }

    public void setFan4(String fan4) {
        Fan4 = fan4;
    }

    public String getLight1() {
        return Light1;
    }

    public void setLight1(String light1) {
        Light1 = light1;
    }

    public String getLight2() {
        return Light2;
    }

    public void setLight2(String light2) {
        Light2 = light2;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }
}
