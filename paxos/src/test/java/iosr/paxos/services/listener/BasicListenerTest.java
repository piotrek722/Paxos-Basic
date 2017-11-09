package iosr.paxos.services.listener;

import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;
import iosr.paxos.model.SequenceNumber;
import iosr.paxos.services.acceptor.Acceptor;
import iosr.paxos.services.acceptor.BasicAcceptor;
import iosr.paxos.services.communication.AcceptorCommunicationService;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BasicListenerTest {

    private RestTemplate restTemplate = new RestTemplate();
    private AcceptorCommunicationService acceptorCommunicationService = new AcceptorCommunicationService(restTemplate);
    private Acceptor acceptor = new BasicAcceptor(acceptorCommunicationService);
    private BasicListener listener = new BasicListener(acceptor);

    @Test
    public void shouldReturnNull() throws Exception {
        //given
        String key = "emptyKey";

        //when
        String value = listener.get(key);

        //then
        assertNull(value);
    }

    @Test
    public void shouldReturnValue() throws Exception {
        //given
        String key = "key";
        String value = "value";
        listener.getEntryMap().put(key, value);

        //when
        String valueToCheck = listener.get(key);

        //then
        assertEquals(value, valueToCheck);
    }

    @Test
    public void proposeShouldAddNewValueToEntryMap() throws Exception {

        //given
        int clusterSize = 3;
        ReflectionTestUtils.setField(listener, "clusterSize", clusterSize);

        SequenceNumber sequenceNumber = new SequenceNumber("server", 1);
        String key = "key", value = "value";
        Entry entry = new Entry(key, value);
        Data data = new Data(sequenceNumber, entry);

        //when
        listener.handleAcceptedData(data);
        listener.handleAcceptedData(data);

        //then
        String valueSavedInKeyValueStore = listener.getEntryMap().get(key);
        assertEquals(value, valueSavedInKeyValueStore);
    }

    @Test
    public void proposeShouldNotAddNewValueToEntryMapNotEnoughProposal() throws Exception {
        //given
        int clusterSize = 3;
        ReflectionTestUtils.setField(listener, "clusterSize", clusterSize);

        SequenceNumber sequenceNumber = new SequenceNumber("server", 1);
        String key = "key", value = "value";
        Entry entry = new Entry(key, value);
        Data data = new Data(sequenceNumber, entry);

        //when
        listener.handleAcceptedData(data);

        //then
        int countInProposalMap = listener.getProposalMap().get(entry);
        assertEquals(1, countInProposalMap);

        String valueSavedInKeyValueStore = listener.getEntryMap().get(key);
        assertNull(valueSavedInKeyValueStore);
    }

}