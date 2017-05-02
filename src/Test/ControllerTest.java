package Test;

import static org.junit.Assert.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.BeforeClass;
import org.junit.Test;

import ChatSystem.Controller;
import ChatSystem.IHM;
import ChatSystem.IHMConnect;
import ChatSystem.Launcher;
import ChatSystem.LocalUser;

public class ControllerTest {

	static Controller c;
	static IHM ihm;
	
	@BeforeClass
	public static void setupBeforeClass() throws UnknownHostException{
		Launcher.setIhmCo(new IHMConnect());
		IHMConnect ihmco = Launcher.getIhmCo();
		Launcher.setController(new Controller(ihmco, 4567));
		ihmco.setUsername("a");
		ihmco.pressConnectionButton();
	}
	
	@Test
	public void testUpdateList() throws UnknownHostException {
		InetAddress adr = InetAddress.getByName("10.0.0.236");
		LocalUser user = new LocalUser("abc",adr,5000);
		c.addUser(user);
		assertEquals(c.getUsernameListe()[0],"b");
	}

//	@Test
//	public void testRemoveList() throws UnknownHostException {
//		InetAddress broadcast = InetAddress.getByName("10.202.1.9");
//		LocalUser user1 = new LocalUser("b",broadcast,3000);
//		LocalUser user2 = new LocalUser("c",broadcast,2000);
//		c.updateList(user1, "hello");
//		c.updateList(user2, "hello");
//		c.removeList(user1);
//		assertEquals(c.getUsernameListe()[0],"c");
//	}

//	@Test
//	public void testGetInfos() {
//		fail("Not yet implemented");
//	}

}
