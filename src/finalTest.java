import java.util.PriorityQueue;
import java.util.Stack;

public class finalTest {
	public static class Node {
		String data;
		Node left;
		Node right;
	}
	private Node root;

	public void preorder() {
		if (root == null)
			return;
		Stack<Node> nodes = new Stack<>();
		nodes.push(root);
		while (nodes.size() >= 0) {
			Node n = nodes.pop();
			System.out.println(n.data);
			if (n.right != null)
				nodes.push(n.right);
			if (n.left != null)
				nodes.push(n.left);
		} 
	}
}