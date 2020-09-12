package cst438;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name="tickets")
public class Ticket {

    public enum Status {
        AWAITING_ADMINISTRATOR {
            public String toString() {
                return "Awaiting Administrator";
            }
        },
        AWAITING_USER {
            public String toString() {
                return "Awaiting User";
            }
        },
        CLOSED {
            public String toString() {
                return "Closed";
            }
        },
        OPEN {
            public String toString() {
                return "Open";
            }
        },
        RESOLVED {
            public String toString() {
                return "Resolved";
            }
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String createdAt;

    public long submittedUserId;

    private Status ticketStatus;

    @NotNull
    @Size(min=2, max=25)
    private String ticketSubject;

    @Size(min=1, max=140)
    private String initialMessage;

    public Ticket() {

        createdAt = new java.util.Date().toString();
        submittedUserId = 0;
        ticketStatus = Status.OPEN;
        ticketSubject = null;
        initialMessage = null;

    }

    public Ticket(String ticketSubject, String initialMessage, long userSubmittedId) {
        this.createdAt = new java.util.Date().toString();
        this.submittedUserId = userSubmittedId;
        this.ticketStatus = Status.OPEN;
        this.ticketSubject = ticketSubject;
        this.initialMessage = initialMessage;
    }

    public Ticket(long ticketId, String ticketSubject, String initialMessage, long userSubmittedId) {
        this.id = ticketId;
        this.createdAt = new java.util.Date().toString();
        this.submittedUserId = userSubmittedId;
        this.ticketStatus = Status.OPEN;
        this.ticketSubject = ticketSubject;
        this.initialMessage = initialMessage;
    }

    public long getTicketId() {
        return this.id;
    }

    public String getTicketSubject() {
        return this.ticketSubject;
    }

    public String getInitialMessage() {
        return this.initialMessage;
    }

    public String getTicketTime() {
        return this.createdAt;
    }

    public Status getTicketStatus() {
        return this.ticketStatus;
    }

    public long getSubmittedUserId() {
        return this.submittedUserId;
    }

    public void setTicketId(long ticketId) {
        this.id = ticketId;
    }

    public void setTicketSubject(String ticketSubject) {
        this.ticketSubject = ticketSubject;
    }

    public void setInitialMessage(String initialMessage) {
        this.initialMessage = initialMessage;
    }

    public void setTicketTime(String ticketTime) {
        this.createdAt = ticketTime;
    }

    public void setTicketStatus(Status ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public void setSubmittedUserId(long userId) {
        this.submittedUserId = userId;
    }
}
