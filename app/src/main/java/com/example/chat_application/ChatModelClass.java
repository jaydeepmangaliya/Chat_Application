package com.example.chat_application;

public class ChatModelClass {

    String sender;
    String MainMessage;

    public ChatModelClass() {
    }

    public ChatModelClass(String sender, String MainMessage) {
        this.sender = sender;
        this.MainMessage = MainMessage;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMainMessage() {
        return MainMessage;
    }

    public void setMainMessage(String mainMessage) {
        MainMessage = mainMessage;
    }
}
