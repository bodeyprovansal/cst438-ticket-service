package cst438;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cst438.Ticket.Status;

@Service
public class TicketService {
    
    
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private TicketMessageRepository ticketMessageRepository;
    
    public TicketService(TicketRepository ticketRepository, TicketMessageRepository ticketMessageRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketMessageRepository = ticketMessageRepository;
    }
    
    public void createTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }
    
    public void createTicketMessage(TicketMessage ticketMessage) {
        ticketMessageRepository.save(ticketMessage);
    }
    
    public void updateTicketStatus(long ticketId, String statusString) {
        Ticket ticket = ticketRepository.findById(ticketId);
        Ticket.Status status = Ticket.Status.valueOf(statusString);
        ticket.setTicketStatus(status);
        ticketRepository.save(ticket);
    }
    
    public List<Ticket> getAllTickets() {
        List<Ticket> allTickets = ticketRepository.findAll();
        return allTickets;
    }
    
    public Ticket getTicket(long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId);
        return ticket;
    }
    
    public TicketMessage getTicketMessage(long ticketMessageId) {
        TicketMessage ticketMessage = ticketMessageRepository.findById(ticketMessageId);
        return ticketMessage;
    }
    
    public List<Ticket> getTicketsForUser(long userId) {
        List<Ticket> tickets = ticketRepository.findBySubmittedUserId(userId);
        return tickets;
    }
    
    public List<TicketMessage> getTicketMessages(long ticketId) {
        List<TicketMessage> ticketMessages = ticketMessageRepository.findByTicketId(ticketId);
        return ticketMessages;
    }

    public List<Ticket> getAllTicketsByStatus(Status ticketStatus) {
        List<Ticket> allTicketsByStatus = ticketRepository.findByTicketStatus(ticketStatus);
        return allTicketsByStatus;
    }
    
    
}
