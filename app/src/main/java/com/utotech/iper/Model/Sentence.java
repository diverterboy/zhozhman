package com.utotech.iper.Model;

public class Sentence {
    private int id;
    private int beShowen;
    private int repeatNum;
    private String sentence;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBeShowen() {
        return beShowen;
    }

    public void setBeShowen(int beShowen) {
        this.beShowen = beShowen;
    }

    public int getRepeatNum() {
        return repeatNum;
    }

    public void setRepeatNum(int repeatNum) {
        this.repeatNum = repeatNum;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
