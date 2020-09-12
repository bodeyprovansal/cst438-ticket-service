package cst438;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(TicketController.class)
public class TicketControllerTest {
    @MockBean
    private TicketService ticketService;
    
    @Autowired
    private MockMvc mvc;
    
    private JacksonTester<Ticket> json;
    private JacksonTester<List<Ticket>> jsonTicketList;
    private JacksonTester<List<TicketMessage>> jsonMessageList;
    
    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }
    
    @Test
    public void contextLoads() {
        
    }
    
    @Test
    public void getTicketById() throws Exception {
        
        long testTicketId = 999;
        long testUserId = 998;
        
        Ticket attemptTicket = new Ticket(testTicketId, "Test Ticket Subject", "Test Initial Message", testUserId);
        Ticket expectedTicket = new Ticket(testTicketId, "Test Ticket Subject", "Test Initial Message", testUserId);
    
        given(ticketService.getTicket(attemptTicket.getTicketId())).willReturn(expectedTicket);
        
        MockHttpServletResponse response = mvc.perform(
                get("/api/ticket/").contentType(MediaType.APPLICATION_JSON).content(
                    "{\"id\"=\"" + attemptTicket.getTicketId() + "\"}"
                )
            ).andReturn().getResponse();
        
        assertThat(response.getContentAsString().compareTo(json.write(expectedTicket).getJson()) == 0);
        assertThat(response.getStatus() == HttpStatus.OK.value());
    }
    
    @Test
    public void getTicketsByUserId() throws Exception {
        long testUserId = 999;
        long testTicketId0 = 998;
        long testTicketId1 = 997;
        long testTicketId2 = 996;
        
        List<Ticket> attemptTicketList = new ArrayList<>();
        List<Ticket> expectedTicketList = new ArrayList<>();
        
        attemptTicketList.add(new Ticket(testTicketId0, "First Test Subject", "First Test Message", testUserId));
        attemptTicketList.add(new Ticket(testTicketId1, "Second Test Subject", "Second Test Message", testUserId));
        attemptTicketList.add(new Ticket(testTicketId2, "Third Test Subject", "Third Test Message", testUserId));
        
        expectedTicketList.add(new Ticket(testTicketId0, "First Test Subject", "First Test Message", testUserId));
        expectedTicketList.add(new Ticket(testTicketId1, "Second Test Subject", "Second Test Message", testUserId));
        expectedTicketList.add(new Ticket(testTicketId2, "Third Test Subject", "Third Test Message", testUserId));
        
        
        given(ticketService.getTicketsForUser(attemptTicketList.get(0).getSubmittedUserId())).willReturn(expectedTicketList);
    
        MockHttpServletResponse response = mvc.perform(
                get("/api/ticket/user/").contentType(MediaType.APPLICATION_JSON).content(
                        "{\"id\"=\"" + attemptTicketList.get(0).getSubmittedUserId() + "\"}"
                    )
                ).andReturn().getResponse();
        
        assertThat(response.getContentAsString().compareTo(jsonTicketList.write(expectedTicketList).getJson()) == 0);
        assertThat(response.getStatus() == HttpStatus.OK.value());
    }
    
    @Test
    public void getTicketMessagesByTicketId() throws Exception {
        long testTicketId = 999;
        long testUserId = 998;
        long testMessageId0 = 997;
        long testMessageId1 = 996;
        long testMessageId2 = 995;
        
        List<TicketMessage> attemptMessageList = new ArrayList<>();
        List<TicketMessage> expectedMessageList = new ArrayList<>();
        
        attemptMessageList.add(new TicketMessage(testMessageId0, testTicketId, testUserId, "First Test Message"));
        attemptMessageList.add(new TicketMessage(testMessageId1, testTicketId, testUserId, "Second Test Message"));
        attemptMessageList.add(new TicketMessage(testMessageId2, testTicketId, testUserId, "Third Test Message"));
        
        expectedMessageList.add(new TicketMessage(testMessageId0, testTicketId, testUserId, "First Test Message"));
        expectedMessageList.add(new TicketMessage(testMessageId1, testTicketId, testUserId, "Second Test Message"));
        expectedMessageList.add(new TicketMessage(testMessageId2, testTicketId, testUserId, "Third Test Message"));
        
        given(ticketService.getTicketMessages(attemptMessageList.get(0).getTicketId())).willReturn(expectedMessageList);
    
        MockHttpServletResponse response = mvc.perform(
                get("/api/ticket/").contentType(MediaType.APPLICATION_JSON).content(
                        "{\"id\"=\"" + attemptMessageList.get(0).getTicketId() + "\"}" + "/allMessages/"
                    )
                ).andReturn().getResponse();
        
        assertThat(response.getContentAsString().compareTo(jsonMessageList.write(expectedMessageList).getJson()) == 0);
        assertThat(response.getStatus() == HttpStatus.OK.value());
    }
    
}
