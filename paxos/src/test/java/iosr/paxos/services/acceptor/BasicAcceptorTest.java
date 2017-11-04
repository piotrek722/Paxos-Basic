package iosr.paxos.services.acceptor;

import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;
import iosr.paxos.model.SequenceNumber;
import iosr.paxos.services.communication.AcceptorCommunicationService;
import org.springframework.web.client.RestTemplate;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BasicAcceptorTest {

    private AcceptorCommunicationService communicationService = new AcceptorCommunicationService(new RestTemplate());
    private BasicAcceptor acceptor = new BasicAcceptor(communicationService);

    @Test
    public void shouldReturnSameSequenceNumber() throws Exception {
        //given
        SequenceNumber sequenceNumber = new SequenceNumber("server1", 1);

        //when
        Data data = acceptor.handlePrepareRequest(sequenceNumber);

        //then
        assertEquals(data.getSequenceNumber(), sequenceNumber);
    }

    @Test
    public void shouldReturnMaxSequenceNumber() throws Exception {
        //given
        SequenceNumber higlerSequenceNumber = new SequenceNumber("server1", 2);
        SequenceNumber lowerSequenceNumber = new SequenceNumber("server1", 1);

        //when
        acceptor.handlePrepareRequest(higlerSequenceNumber);
        Data data = acceptor.handlePrepareRequest(lowerSequenceNumber);

        //then
        assertEquals(data.getSequenceNumber(), higlerSequenceNumber);
    }

    @Test
    public void shouldAcceptWhenSequenceNumbersMatch() throws Exception {
        //given
        SequenceNumber sequenceNumber = new SequenceNumber("server1", 1);

        //when
        Data data = acceptor.handlePrepareRequest(sequenceNumber);
        acceptor.accept(data);

        //then
        assertEquals(data.getValue(), acceptor.getAccepted());
    }

    @Test
    public void shouldNotAcceptWhenSequenceNumbersDoNotMatch() throws Exception {
        //given
        SequenceNumber sequenceNumber = new SequenceNumber("server1", 1);
        SequenceNumber otherSequenceNumber = new SequenceNumber("server2", 2);
        Data otherData = new Data(otherSequenceNumber, new Entry("key", "value"));

        //when
        acceptor.handlePrepareRequest(sequenceNumber);
        acceptor.accept(otherData);

        //then
        assertNull(acceptor.getAccepted());
    }
}
