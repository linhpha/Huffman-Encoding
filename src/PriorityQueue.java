import java.util.LinkedList;
import java.util.List;

/**
 * Priority Queue class implementation
 * @author Linh Pham, Benjamin Wong
 *
 */
public class PriorityQueue<T> {
	public List<Node> nodeList;

	/**
	 * Constructor for PriorityQueue
	 */
	public PriorityQueue(){
		nodeList = new LinkedList<>();
	}

	/**
	 * Adding node into the queue based on the increase of frequency
	 * @param Node addNode
	 */
	public void add(Node addNode) {
		if (addNode == null) {
			throw new IllegalStateException();
		} else { 
			for (int i = 0; i < nodeList.size(); i++) {
				if (addNode.frequency < nodeList.get(i).frequency) {
					nodeList.add(i, addNode);
					return;
				}
			}
			nodeList.add(addNode);
		}
	}

	/**
	 * Get the first element of the queue and return it
	 * @return the element got removed from the queue
	 */
	public Node poll() {
		return nodeList.remove(0);
	}
	/**
	 * Get the size of the queue
	 * @return the size of the queue
	 */

	public int size() {
		return nodeList.size();
	}
}


