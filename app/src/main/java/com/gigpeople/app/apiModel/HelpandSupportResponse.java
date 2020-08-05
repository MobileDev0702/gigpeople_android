package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HelpandSupportResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("faq_list")
    @Expose
    private List<FaqList> faqList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FaqList> getFaqList() {
        return faqList;
    }

    public void setFaqList(List<FaqList> faqList) {
        this.faqList = faqList;
    }

    public class FaqList {

        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("faq")
        @Expose
        private List<Faq> faq = null;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<Faq> getFaq() {
            return faq;
        }

        public void setFaq(List<Faq> faq) {
            this.faq = faq;
        }


        public class Faq {

            @SerializedName("question")
            @Expose
            private String question;
            @SerializedName("answer")
            @Expose
            private String answer;

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

        }
    }
}