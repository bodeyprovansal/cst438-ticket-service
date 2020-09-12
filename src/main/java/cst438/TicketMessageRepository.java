package cst438;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketMessageRepository extends JpaRepository<TicketMessage, Long> {

    public TicketMessage findById(long ticketMessageId);
    
    public List<TicketMessage> findByTicketId(long ticketId);

}
