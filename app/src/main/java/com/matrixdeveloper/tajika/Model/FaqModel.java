package com.matrixdeveloper.tajika.Model;

public class FaqModel {
    private int id;
    private String faqQue;
    private String faqAns;

    public FaqModel(int id, String faqQue, String faqAns) {
        this.id = id;
        this.faqQue = faqQue;
        this.faqAns = faqAns;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFaqQue() {
        return faqQue;
    }

    public void setFaqQue(String faqQue) {
        this.faqQue = faqQue;
    }

    public String getFaqAns() {
        return faqAns;
    }

    public void setFaqAns(String faqAns) {
        this.faqAns = faqAns;
    }
}
