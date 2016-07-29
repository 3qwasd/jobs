package jobs.toolkit.lifecycle;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.lifecycle.LifeCycleStateModel;
import jobs.toolkit.lifecycle.LifeCycleStateModel.STATE;

public class LifeCycleStateModelTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsInState() {
		for(int i = 0; i < 4; i++){
			STATE state_i = LifeCycleStateModel.getStateByValue(i);
			LifeCycleStateModel stateModel = new LifeCycleStateModel(state_i);
			for(int j = 0; j < 4; j++){
				STATE state_j = LifeCycleStateModel.getStateByValue(j);
				if(i == j){
					assertTrue(stateModel.isInState(state_j));	
				}else{
					assertFalse(stateModel.isInState(state_j));
				}
			}
		}
	}
	@Test
	public void testEnsureCurrentState(){
		for(int i = 0; i < 4; i++){
			STATE state_i = LifeCycleStateModel.getStateByValue(i);
			LifeCycleStateModel stateModel = new LifeCycleStateModel(state_i);
			for(int j = 0; j < 4; j++){
				STATE state_j = LifeCycleStateModel.getStateByValue(j);
				if(i == j){
					try{
						stateModel.ensureCurrentState(state_j);
					}catch(Exception e){
						fail();
					}
				}else{
					try{
						stateModel.ensureCurrentState(state_j);
						fail("must exception");
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}
	@Test
	public void testStateTransferNotInited(){
		LifeCycleStateModel stateModel = new LifeCycleStateModel(STATE.NOTINITED);
		try{
			stateModel.stateTransfer(STATE.NOTINITED);
			fail();
		}catch(Exception e){
			assertTrue(stateModel.isInState(STATE.NOTINITED));
			e.printStackTrace();
		}
		
		stateModel = new LifeCycleStateModel(STATE.NOTINITED);
		try{
			stateModel.stateTransfer(STATE.INITED);
			assertTrue(stateModel.isInState(STATE.INITED));
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
		stateModel = new LifeCycleStateModel(STATE.NOTINITED);
		try{
			stateModel.stateTransfer(STATE.STARTED);
			fail();
		}catch(Exception e){
			assertTrue(stateModel.isInState(STATE.NOTINITED));
			e.printStackTrace();
		}
		
		stateModel = new LifeCycleStateModel(STATE.NOTINITED);
		try{
			stateModel.stateTransfer(STATE.STOPPED);
			assertTrue(stateModel.isInState(STATE.STOPPED));
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	@Test
	public void testStateTransferInited(){
		LifeCycleStateModel stateModel = new LifeCycleStateModel(STATE.INITED);
		try{
			stateModel.stateTransfer(STATE.NOTINITED);
			fail();
		}catch(Exception e){
			assertTrue(stateModel.isInState(STATE.INITED));
			e.printStackTrace();
		}
		
		stateModel = new LifeCycleStateModel(STATE.INITED);
		try{
			stateModel.stateTransfer(STATE.INITED);
			assertTrue(stateModel.isInState(STATE.INITED));
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
		stateModel = new LifeCycleStateModel(STATE.INITED);
		try{
			stateModel.stateTransfer(STATE.STARTED);
			assertTrue(stateModel.isInState(STATE.STARTED));
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
		stateModel = new LifeCycleStateModel(STATE.INITED);
		try{
			stateModel.stateTransfer(STATE.STOPPED);
			assertTrue(stateModel.isInState(STATE.STOPPED));
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testStateTransferStarted(){
		LifeCycleStateModel stateModel = new LifeCycleStateModel(STATE.STARTED);
		try{
			stateModel.stateTransfer(STATE.NOTINITED);
			fail();
		}catch(Exception e){
			assertTrue(stateModel.isInState(STATE.STARTED));
			e.printStackTrace();
		}
		
		stateModel = new LifeCycleStateModel(STATE.STARTED);
		try{
			stateModel.stateTransfer(STATE.INITED);
			fail();
		}catch(Exception e){
			assertTrue(stateModel.isInState(STATE.STARTED));
			e.printStackTrace();
		}
		
		stateModel = new LifeCycleStateModel(STATE.STARTED);
		try{
			stateModel.stateTransfer(STATE.STARTED);
			assertTrue(stateModel.isInState(STATE.STARTED));
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
		stateModel = new LifeCycleStateModel(STATE.STARTED);
		try{
			stateModel.stateTransfer(STATE.STOPPED);
			assertTrue(stateModel.isInState(STATE.STOPPED));
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testStateTransferStopped(){
		//
		LifeCycleStateModel stateModel = new LifeCycleStateModel(STATE.STOPPED);
		try{
			stateModel.stateTransfer(STATE.NOTINITED);
			fail();
		}catch(Exception e){
			assertTrue(stateModel.isInState(STATE.STOPPED));
			e.printStackTrace();
		}
		
		stateModel = new LifeCycleStateModel(STATE.STOPPED);
		try{
			stateModel.stateTransfer(STATE.INITED);
			fail();
		}catch(Exception e){
			assertTrue(stateModel.isInState(STATE.STOPPED));
			e.printStackTrace();
		}
		
		stateModel = new LifeCycleStateModel(STATE.STOPPED);
		try{
			stateModel.stateTransfer(STATE.STARTED);
			fail();
		}catch(Exception e){
			assertTrue(stateModel.isInState(STATE.STOPPED));
			e.printStackTrace();
		}
		
		stateModel = new LifeCycleStateModel(STATE.STOPPED);
		try{
			stateModel.stateTransfer(STATE.STOPPED);
			assertTrue(stateModel.isInState(STATE.STOPPED));
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
}
