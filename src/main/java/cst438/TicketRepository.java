package cst438;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import cst438.Ticket.Status;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    public Ticket findById(long id);

    public List<Ticket> findBySubmittedUserId(long submittedUserId);

    public List<Ticket> findByTicketStatus(Status ticketStatus);
    
}
