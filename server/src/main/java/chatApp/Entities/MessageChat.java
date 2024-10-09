package chatApp.Entities;


public class MessageChat {
    private User sender;
    private User receiver;
    private String message;


    public User getSender() {
        return sender;
    }


    public void setSender(User sender) {
        this.sender = sender;
    }


    public User getReceiver() {
        return receiver;
    }


    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageChat{");
        sb.append("sender=").append(sender);
        sb.append(", receiver=").append(receiver);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}