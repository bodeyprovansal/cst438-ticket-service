package cst438;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import cst438.Ticket.Status;

@SpringBootTest
public class TicketServiceTest {

    
    @Autowired
    private TicketService ticketService;
    
    @MockBean
    private TicketRepository ticketRepository;
    
    @MockBean
    private TicketMessageRepository ticketMessageRepository;
    
    @Test
    public void contextLoads() {}
    
    @Test
    public void getTicketById() throws Exception {
        long testTicketId = 0;
        long testUserId = 1;
        
        Ticket expectedTicket = new Ticket(testTicketId, "Test Ticket Subject", "Test Ticket Message", testUserId);
        
        given(ticketRepository.findById(testTicketId)).willReturn(expectedTicket);
        
        Ticket actualTicket = ticketRepository.findById(testTicketId);
        
        assertThat(actualTicket.getTicketId() == testTicketId);
        assertThat(actualTicket.getTicketSubject().compareTo("Test Ticket Subject") == 0);
        assertThat(actualTicket.getInitialMessage().compareTo("Test Ticket Message") == 0);
        assertThat(actualTicket.getSubmittedUserId() == testUserId);
        assertThat(actualTicket.getTicketStatus() == Status.OPEN);
    }
    
    @Test 
    public void getTicketMessageById() throws Exception {
        long testTicketId = 999;
        long testUserId = 998;
        long testMessageId = 997;
        
        TicketMessage expectedMessage = new TicketMessage(testMessageId, testTicketId, testUserId, "Test Ticket Message");
        
        given(ticketMessageRepository.findById(testMessageId)).willReturn(expectedMessage);
        
        TicketMessage actualMessage = ticketMessageRepository.findById(testMessageId);
        
        assertThat(actualMessage.getTicketMessageId() == testMessageId);
        assertThat(actualMessage.getTicketId() == testTicketId);
        assertThat(actualMessage.getUserId() == testUserId);
        assertThat(actualMessage.getMessage().compareTo("Test Ticket Message"));
    }
    
    @Test
    public void getTicketsByUserId() throws Exception {
        long testUserId = 999;
        long testTicketId0 = 998;
        long testTicketId1 = 997;
        long testTicketId2 = 996;
        
        List<Ticket> expectedTicketList = new ArrayList<>();
        
        expectedTicketList.add(new Ticket(testTicketId0, "First Test Subject", "First Test Message", testUserId));
        expectedTicketList.add(new Ticket(testTicketId1, "Second Test Subject", "Second Test Message", testUserId));
        expectedTicketList.add(new Ticket(testTicketId2, "Third Test Subject", "Third Test Message", testUserId));
    
        given(ticketRepository.findBySubmittedUserId(testUserId)).willReturn(expectedTicketList);
        
        List<Ticket> actualTicketList = ticketRepository.findBySubmittedUserId(testUserId);

        assertThat(actualTicketList.size() == expectedTicketList.size());
        
        assertThat(actualTicketList.get(0).getTicketId() == testTicketId0);
        assertThat(actualTicketList.get(0).getTicketSubject().compareTo("First Test Subject"));
        assertThat(actualTicketList.get(0).getInitialMessage().compareTo("First Test Message"));
        assertThat(actualTicketList.get(0).getSubmittedUserId() == testUserId);
        
        assertThat(actualTicketList.get(1).getTicketId() == testTicketId1);
        assertThat(actualTicketList.get(1).getTicketSubject().compareTo("Second Test Subject"));
        assertThat(actualTicketList.get(1).getInitialMessage().compareTo("Second Test Message"));
        assertThat(actualTicketList.get(1).getSubmittedUserId() == testUserId);
        
        assertThat(actualTicketList.get(2).getTicketId() == testTicketId2);
        assertThat(actualTicketList.get(2).getTicketSubject().compareTo("Third Test Subject"));
        assertThat(actualTicketList.get(2).getInitialMessage().compareTo("Second Test Message"));
        assertThat(actualTicketList.get(2).getSubmittedUserId() == testUserId);
        
    }
    
    @Test
    public void getTicketMessagesByTicketId() throws Exception {
        long testTicketId = 999;
        long testUserId = 998;
        long testMessageId0 = 997;
        long testMessageId1 = 996;
        long testMessageId2 = 995;
        
        List<TicketMessage> expectedMessageList = new ArrayList<>();
        
        expectedMessageList.add(new TicketMessage(testMessageId0, testTicketId, testUserId, "First Test Message"));
        expectedMessageList.add(new TicketMessage(testMessageId1, testTicketId, testUserId, "Second Test Message"));
        expectedMessageList.add(new TicketMessage(testMessageId2, testTicketId, testUserId, "Third Test Message"));
        
        given(ticketMessageRepository.findByTicketId(testTicketId)).willReturn(expectedMessageList);
        
        List<TicketMessage> actualMessageList = ticketMessageRepository.findByTicketId(testTicketId);
        
        assertThat(actualMessageList.get(0).getTicketMessageId() == testMessageId0);
        assertThat(actualMessageList.get(0).getTicketId() == testTicketId);
        assertThat(actualMessageList.get(0).getUserId() == testUserId);
        assertThat(actualMessageList.get(0).getMessage().compareTo("First Test Message"));
        
        assertThat(actualMessageList.get(1).getTicketMessageId() == testMessageId1);
        assertThat(actualMessageList.get(1).getTicketId() == testTicketId);
        assertThat(actualMessageList.get(1).getUserId() == testUserId);
        assertThat(actualMessageList.get(1).getMessage().compareTo("Second Test Message"));
        
        assertThat(actualMessageList.get(2).getTicketMessageId() == testMessageId2);
        assertThat(actualMessageList.get(2).getTicketId() == testTicketId);
        assertThat(actualMessageList.get(2).getUserId() == testUserId);
        assertThat(actualMessageList.get(2).getMessage().compareTo("Third Test Message"));
        
    }

}
