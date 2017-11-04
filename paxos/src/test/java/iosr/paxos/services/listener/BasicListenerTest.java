package iosr.paxos.services.listener;

import iosr.paxos.model.Entry;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BasicListenerTest {

    private BasicListener listener = new BasicListener();

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

        String key = "key", value = "value";
        Entry entry = new Entry(key, value);

        //when
        listener.proposeValue(entry);
        listener.proposeValue(entry);

        //then
        String valueSavedInKeyValueStore = listener.getEntryMap().get(key);
        assertEquals(value, valueSavedInKeyValueStore);
    }

    @Test
    public void proposeShouldNotAddNewValueToEntryMapNotEnoughProposal() throws Exception {
        //given
        int clusterSize = 3;
        ReflectionTestUtils.setField(listener, "clusterSize", clusterSize);

        String key = "key", value = "value";
        Entry entry = new Entry(key, value);

        //when
        listener.proposeValue(entry);

        //then
        int countInProposalMap = listener.getProposalMap().get(entry);
        assertEquals(1, countInProposalMap);

        String valueSavedInKeyValueStore = listener.getEntryMap().get(key);
        assertNull(valueSavedInKeyValueStore);
    }

}