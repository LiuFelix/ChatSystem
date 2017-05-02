package Test;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.BeforeClass;
import org.junit.Test;

import ChatSystem.Controller;
import ChatSystem.IHM;
import ChatSystem.IHMConnect;
import ChatSystem.LocalUser;

public class ControllerTest {

	static Controller c;
	static IHM ihm;
	
	@BeforeClass 
	public static void setupBeforeClass() throws UnknownHostException{
		IHMConnect ihmco = new IHMConnect();
		c = new Controller(ihmco, 4567);
		ihm = c.getIhm();
	}
	
	
	@Test
	public void testUpdateList() throws UnknownHostException {
		LocalUser user = new LocalUser("a",c.getBroadcast(),5000);
		c.updateList(user,"hello");
		assertEquals(c.getUsernameListe()[0],"a");
	}

//	@Test
//	public void testRemoveList() throws UnknownHostException {
//		InetAddress broadcast = InetAddress.getByName("10.202.1.9");
//		LocalUser user1 = new LocalUser("a",broadcast,3000);
//		LocalUser user2 = new LocalUser("b",broadcast,2000);
//		c.updateList(user1, "hello");
//		c.updateList(user2, "hello");
//		c.removeList(user1);
//		assertEquals(c.getUsernameListe()[0],"b");
//	}

//	@Test
//	public void testGetInfos() {
//		fail("Not yet implemented");
//	}

}
