package jobs.toolkit.support.zookeeper;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Test;

public class ZKTest {
	
	@Test
	public void aclTest(){
		ArrayList<ACL> acls = ZooDefs.Ids.OPEN_ACL_UNSAFE;
		ACL acl = new ACL();
		try {
			String digister = DigestAuthenticationProvider.generateDigest("qj:yidaiyidaiyao");
			System.out.println(digister);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
