package cst438;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    
    @Autowired
    private TicketService ticketService;
    
    @PostMapping("/create")
    public ResponseEntity<String> createTicket(@RequestBody Map<String, String> request) {
        
        Ticket newTicket = new Ticket(
                request.get("subject"),
                request.get("initial_message"),
                Long.parseLong(request.get("userId"))
        );
        ticketService.createTicket(newTicket);
        
        TicketMessage newTicketInitialMessage = new TicketMessage(
                newTicket.getTicketId(), 
                newTicket.getSubmittedUserId(), 
                request.get("initial_message")
        );
        ticketService.createTicketMessage(newTicketInitialMessage);
        return ResponseEntity.status(HttpStatus.OK).body("New ticket created for userId: " + request.get("userId"));
    }
    
    @PostMapping("/update/newStatus")
    public ResponseEntity<String> updateTicket(@RequestBody Map<String, String> request) {
        long requestedTicketId = Long.parseLong(request.get("ticketId"));
        //System.out.println("requestedTicketId: " + requestedTicketId);
        Ticket requestedTicket = ticketService.getTicket(requestedTicketId);
        if(requestedTicket != null) {
            if(requestedTicket.getTicketStatus() != Ticket.Status.CLOSED) {
                ticketService.updateTicketStatus(requestedTicketId, request.get("status"));
                return ResponseEntity.status(HttpStatus.OK).body("Good");
            }
            return ResponseEntity.status(HttpStatus.OK).body("Ticket already closed");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Ticket Not found");
    }
    
    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicket(@PathVariable("ticketId") long ticketId) {
        Ticket thisTicket = ticketService.getTicket(ticketId);
        
        if(thisTicket != null) {
            return new ResponseEntity<Ticket>(thisTicket, HttpStatus.OK);
        }
        return new ResponseEntity<Ticket>(thisTicket, HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("/update/newMessage")
    public ResponseEntity<String> createTicketMessage(@RequestBody Map<String, String> request) {
        System.out.println(request.get("ticketId"));
        Ticket ticket = ticketService.getTicket(Long.parseLong(request.get("ticketId")));
        
        TicketMessage ticketMessage = new TicketMessage(
                ticket.getTicketId(),
                ticket.getSubmittedUserId(),
                request.get("message")
        );
        ticketService.createTicketMessage(ticketMessage);
        return ResponseEntity.status(HttpStatus.OK).body("New message added to ticketId:" + request.get("ticketId"));
    }
    
    @GetMapping("/{ticketId}/allMessages")
    public ResponseEntity<List<TicketMessage>> getMessagesForTicket(@PathVariable("ticketId") long ticketId) {
        List<TicketMessage> messages = ticketService.getTicketMessages(ticketId);
        
        if(messages != null) {
            return new ResponseEntity<List<TicketMessage>>(messages, HttpStatus.OK);
        }
        
        return new ResponseEntity<List<TicketMessage>>(messages, HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/users/{userId}") 
    public ResponseEntity<List<Ticket>> getTicketsForUser(@PathVariable("userId") long userId){
        List<Ticket> tickets = ticketService.getTicketsForUser(userId);
        
        if(tickets != null) {
            return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
        }
        
        return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/allTickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> allTickets = ticketService.getAllTickets();
        
        if(allTickets != null) {
            return new ResponseEntity<List<Ticket>>(allTickets, HttpStatus.OK);
        }
        return new ResponseEntity<List<Ticket>>(allTickets, HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/allTickets/{ticketStatus}")
    public ResponseEntity<List<Ticket>> getAllTicketsByStatus(@PathVariable("ticketStatus") Ticket.Status ticketStatus) {
        List<Ticket> allTicketsByStatus = ticketService.getAllTicketsByStatus(ticketStatus);
        
        if(allTicketsByStatus != null) {
            return new ResponseEntity<List<Ticket>>(allTicketsByStatus, HttpStatus.OK);
        }
        return new ResponseEntity<List<Ticket>>(allTicketsByStatus, HttpStatus.NOT_FOUND);
        
    }
}
