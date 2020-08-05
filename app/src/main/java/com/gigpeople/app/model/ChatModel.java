package com.gigpeople.app.model;

public class ChatModel {
    String chat,name;


    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatModel(String chat, String name) {
        this.chat=chat;
        this.name=name;

    }




}
