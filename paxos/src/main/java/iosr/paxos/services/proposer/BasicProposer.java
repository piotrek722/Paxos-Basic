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
        bestPromisedData = new Data(sequenceNumber,new Entry("1",this.value));
        this.value="";
    }
    public  Boolean propose (SequenceNumber sequenceNumber){
        communicationService.sendPromiseToAll(sequenceNumber);
        return true;
    }
    public  Boolean propose (String value){
        SequenceNumber sequenceNumber=new SequenceNumber(serverName,0);
        bestPromisedData=new Data(sequenceNumber, new Entry("1", value));
        this.value=value;
        promises = communicationService.sendPromiseToAll(sequenceNumber);
        return true;
    }

    @Override
    public void commit(Data data) {
        handleCommit(data);
        if (isQuorum()) clear();
    }
    public void handleCommit(Data data){
        promises.add(data);
        bestPromisedData=comparePromiseSequenceNumber(promises);
        if (isQuorum()){
            communicationService.sendAcceptToAll(bestPromisedData);
        }
    }
    private Boolean isQuorum(){
        return promises.size()>=clusterSize/2+1;
    }

    private void clear(){
        sequenceNumber.setSeqNumber(0);
        promises.clear();
        value="";
        bestPromisedData=new Data(new SequenceNumber(serverName,0),new Entry("1",this.value));
    }
    private Data comparePromiseSequenceNumber (List<Data> promisesList){
        for (Data promise : promisesList){
            if(promise.getSequenceNumber().getSequenceNumber() > bestPromisedData.getSequenceNumber().getSequenceNumber()){
                bestPromisedData=promise;
            }
        }
        return bestPromisedData;
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




