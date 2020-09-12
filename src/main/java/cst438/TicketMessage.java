package cst438;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ticket_messages")
public class TicketMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ticketMessageId;
    
    private String createdAt;
    
    private long ticketId;
    private long userId;
    
    private String message;
    
    public TicketMessage() {
        createdAt = new java.util.Date().toString();
        message = null;
    }
    
    public TicketMessage(long ticketId, long userId, String message) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.message = message;
        this.createdAt = new java.util.Date().toString();
    }
    
    public TicketMessage(long ticketMessageId, long ticketId, long userId, String message) {
        this.ticketMessageId = ticketMessageId;
        this.ticketId = ticketId;
        this.userId = userId;
        this.message = message;
        this.createdAt = new java.util.Date().toString();
    }
    
    public long getTicketMessageId() {
        return this.ticketMessageId;
    }
    
    public long getTicketId() {
        return this.ticketId;
    }
    
    public long getUserId() {
        return this.userId;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public String getMessageTime() {
        return this.createdAt;
    }
    
    public void setTicketMessageId(long ticketMessageId) {
        this.ticketMessageId = ticketMessageId;
    }
    
    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }
    
    public void setUserId(long userId) {
        this.userId = userId;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
