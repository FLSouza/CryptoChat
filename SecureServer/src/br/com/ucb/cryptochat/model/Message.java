package br.com.ucb.cryptochat.model;

import java.io.Serializable;

/**
 * Created by jonathan on 6/9/16.
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 955090081746452325L;

    private String message;
    private Client recipient;
    private Client sender;

    public Message(String message, Client recipient, Client sender) {
        this.message = message;
        this.recipient = recipient;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Client getRecipient() {
        return recipient;
    }

    public void setRecipient(Client recipient) {
        this.recipient = recipient;
    }

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (message != null ? !message.equals(message1.message) : message1.message != null) return false;
        if (recipient != null ? !recipient.equals(message1.recipient) : message1.recipient != null) return false;
        return sender != null ? sender.equals(message1.sender) : message1.sender == null;

    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", recipient=" + recipient +
                ", sender=" + sender +
                '}';
    }
}
