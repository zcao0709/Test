package myZookeeper;

import org.apache.zookeeper.KeeperException;

import java.util.List;

public class DeleteGroup extends ConnectionWatcher {

	public void delete(String groupName) throws KeeperException, InterruptedException {
		String path = "/" + groupName;
		
		try {
			 List<String> children = zk.getChildren(path, null);
			 for (String child : children) {
				 zk.delete(path + "/" + child, -1);
			 }
			 zk.delete(path, -1);
		} catch (KeeperException.NoNodeException e) {
			System.out.printf("Group %s does not exist\n", groupName);
			System.exit(1);
		}
	}
	
	public static void main(String[] args) throws Exception {
		DeleteGroup dg = new DeleteGroup();
		dg.connect(args[0]);
		dg.delete(args[1]);
		dg.close();
	}

}
