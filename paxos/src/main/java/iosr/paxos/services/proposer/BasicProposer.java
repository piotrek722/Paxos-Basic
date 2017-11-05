package iosr.paxos.services.proposer;

import com.sun.org.apache.xpath.internal.operations.Bool;
import iosr.paxos.model.Data;
import iosr.paxos.model.SequenceNumber;
import iosr.paxos.services.communication.CommunicationService;
import iosr.paxos.services.communication.ProposerCommunicationService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.LinkedList;
import java.util.List;
import iosr.paxos.model.Entry;
@Service
public class BasicProposer implements Proposer {

    private ProposerCommunicationService communicationService;

    private SequenceNumber sequenceNumber;
    private Data bestPromisedData;
    private String value;
    private List<Data> promises =new LinkedList<Data>();
    private String serverName;
    @Value("${cluster.size}")
    private int clusterSize;

    public BasicProposer (ProposerCommunicationService communicationService){
        this.communicationService=communicationService;
        this.serverName="serverName";
        sequenceNumber=new SequenceNumber(this.serverName, 0);
        bestPromisedData = new Data(sequenceNumber,new Entry("",""));
    }

    @Override
    public Boolean propose(Entry entry) {
        Data data = new Data(sequenceNumber, entry);
        communicationService.sendPromiseToAll(data);
        return null;
    }

    @Override
    public void commit(Data data) {
        handleCommit(data);
        if (isQuorum()) clear();
    }
    public void handleCommit(Data data){
        promises.add(data);
        bestPromisedData=comparePromiseSequenceNumber(data);
        if (isQuorum()){
            communicationService.sendAcceptToAll(data);
        }
    }
    private Boolean isQuorum(){
        return promises.size()>=clusterSize/2+1;
    }
    private void clear(){
        sequenceNumber.setSeqNumber(0);
        promises.clear();
        bestPromisedData=new Data(new SequenceNumber(serverName,0),new Entry("",""));
    }
    private Data comparePromiseSequenceNumber(Data data){
      return data.getSequenceNumber().getSequenceNumber() >bestPromisedData.getSequenceNumber().getSequenceNumber() ? data :bestPromisedData;
    }

    //for tests:
    public LinkedList<Data> getPromises(){
        return (LinkedList<Data>) promises;
    }

    public Data getBestPromisedData() {
        return bestPromisedData;
    }

    public void setClusterSize(int clusterSize) {
        this.clusterSize = clusterSize;
    }
}
