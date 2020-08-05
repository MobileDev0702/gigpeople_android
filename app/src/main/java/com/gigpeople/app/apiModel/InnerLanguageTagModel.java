package com.gigpeople.app.apiModel;

public class InnerLanguageTagModel {
    String langName;
    String langStatus;

    public InnerLanguageTagModel(String langName, String langStatus) {
        this.langName = langName;
        this.langStatus = langStatus;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public String getLangStatus() {
        return langStatus;
    }

    public void setLangStatus(String langStatus) {
        this.langStatus = langStatus;
    }
}
