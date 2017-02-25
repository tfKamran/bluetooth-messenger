package com.tf.bluetoothmessenger.model;

/**
 * Created by kamran on 26/02/17.
 */

public class Message {

    private BTDevice sender;
    private String message;

    public Message(BTDevice sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public BTDevice getSender() {
        return sender;
    }

    public void setSender(BTDevice sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
