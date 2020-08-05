package com.gigpeople.app.model;

/**
 * Created by bharath on 12/10/17.
 */

public class BarChartModel {
    private int barValue;
    private int barColor;
    private Object barTag;
    private String barText;

    public String getBarmonth() {
        return barmonth;
    }

    public void setBarmonth(String barmonth) {
        this.barmonth = barmonth;
    }

    private String barmonth;

    BarChartModel(int barValue, int barColor) {
        this.barValue = barValue;
        this.barColor = barColor;
    }
    BarChartModel(int barValue, int barColor, Object barTag) {
        this.barValue = barValue;
        this.barColor = barColor;
        this.barTag = barTag;
    }
    BarChartModel(int barValue, int barColor, String barText, String barmonth , Object barTag) {
        this.barValue = barValue;
        this.barColor = barColor;
        this.barTag = barTag;
        this.barText =barText;
        this.barmonth =barmonth;
    }



    public BarChartModel() {

    }

    public int getBarValue() {
        return barValue;
    }

    public void setBarValue(int barValue) {
        this.barValue = barValue;
    }

    public int getBarColor() {
        return barColor;
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
    }

    public Object getBarTag() {
        return barTag;
    }

    public void setBarTag(Object barTag) {
        this.barTag = barTag;
    }

    public String getBarText() {
        return barText;
    }

    public void setBarText(String barText) {
        this.barText = barText;
    }
}
