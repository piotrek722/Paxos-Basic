package iosr.paxos.services.proposer;

import iosr.paxos.model.Entry;
import iosr.paxos.model.SequenceNumber;
import iosr.paxos.services.communication.ProposerCommunicationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import iosr.paxos.model.Data;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;

public class BasicProposerTest {
    BasicProposer basicProposer = new BasicProposer(new ProposerCommunicationService(new RestTemplate()));

    @Before
    public void setProposer()
    {
        basicProposer.setClusterSize(5);
    }



    @Test
    public void propose() throws Exception {
    }


    @Test
    public void shouldChooseBestSequenceNumberedData() throws Exception {
        Data data1 = new Data (new SequenceNumber("server",1),new Entry("",""));
        Data data2 = new Data (new SequenceNumber("server",2),new Entry("",""));
        Data data3 = new Data (new SequenceNumber("server",1),new Entry("",""));
        Data data4 = new Data (new SequenceNumber("server",7),new Entry("","cow jumps over the moon"));
        Data data5 = new Data (new SequenceNumber("server",2),new Entry("",""));
        Data data6 = new Data (new SequenceNumber("server",2),new Entry("",""));
        basicProposer.handleCommit(data1);
        basicProposer.handleCommit(data2);
        basicProposer.handleCommit(data3);
        basicProposer.handleCommit(data4);
        basicProposer.handleCommit(data5);
        basicProposer.handleCommit(data6);
        Assert.assertEquals(basicProposer.getBestPromisedData().getValue().getValue(), "cow jumps over the moon");
    }

    @Test
    public void shouldClearAfterSendingAccept() throws Exception{
        Data data4 = new Data (new SequenceNumber("server",7),new Entry("","cow jumps over the moon"));
        Data data5 = new Data (new SequenceNumber("server",2),new Entry("",""));
        Data data6 = new Data (new SequenceNumber("server",0),new Entry("",""));
        basicProposer.commit(data4);
        basicProposer.commit(data5);
        basicProposer.commit(data5);
        Assert.assertEquals(basicProposer.getBestPromisedData().getSequenceNumber().getSequenceNumber().toString(),"0");
        Assert.assertEquals(basicProposer.getPromises(), new LinkedList<Data>());
    }
    @Test
    public void shouldNotClearBeforeQuorum() throws Exception{
        Data data4 = new Data (new SequenceNumber("server",7),new Entry("","cow jumps over the moon"));
        basicProposer.commit(data4);
        Assert.assertEquals(basicProposer.getBestPromisedData().getSequenceNumber().getSequenceNumber().toString(),"7");
        LinkedList<Data> expected = new LinkedList<Data>();
        expected.add(data4);
        Assert.assertEquals(basicProposer.getPromises(),expected);
    }

}