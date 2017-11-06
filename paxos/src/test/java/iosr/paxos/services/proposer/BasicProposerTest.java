package iosr.paxos.services.proposer;

import iosr.paxos.model.Entry;
import iosr.paxos.model.SequenceNumber;
import iosr.paxos.services.communication.ProposerCommunicationService;
import org.junit.Before;
import org.junit.Test;

import iosr.paxos.model.Data;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class BasicProposerTest {

    BasicProposer basicProposer = new BasicProposer(new ProposerCommunicationService(new RestTemplate()));
    private static final Data data = new Data(new SequenceNumber("serverName", 0), new Entry("",""));
    private static final Data data1 = new Data(new SequenceNumber("serverName", 3), new Entry("",""));
    private static final Data data2 = new Data(new SequenceNumber("serverName", 5) ,new Entry("",""));
    private static final Data data3 = new Data(new SequenceNumber("serverName", 6), new Entry("did you know that","cow jumps over the moon"));
    private static final Data data4 = new Data(new SequenceNumber("serverName", 1), new Entry("",""));
    @Before
    public void setProposer()
    {
        basicProposer.setClusterSize(5);
    }

    @Test
    public void propose() throws Exception {
    }


    @Test
    public void shouldAssignDataWithBestSequenceNumber () throws Exception {

        basicProposer.handleCommit(data);
        basicProposer.handleCommit(data1);
        basicProposer.handleCommit(data2);
        basicProposer.handleCommit(data3);
        basicProposer.handleCommit(data4);
        assertEquals(6, (int)basicProposer.getBestPromisedData().getSequenceNumber().getSeqNumber() );
        assertEquals("cow jumps over the moon", basicProposer.getBestPromisedData().getValue().getValue());
        assertEquals("did you know that", basicProposer.getBestPromisedData().getValue().getKey());

    }
    @Test
    public void shouldClearAfterQuorumCommit(){
        basicProposer.commit(data);
        basicProposer.commit(data1);
        basicProposer.commit(data2);
        assertEquals("", basicProposer.getValue());
        assertEquals(0, basicProposer.getPromises().size());
    }
    @Test
    public void shouldNotClearBeforeQuorum(){
        basicProposer.propose("1","2");
        basicProposer.commit(data1);
        basicProposer.commit(data3);
        assertNotEquals("", basicProposer.getValue());
        assertNotEquals(0, basicProposer.getPromises().size());
    }


}