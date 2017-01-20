/**
 * Constructing the Node class
 * @author Benjamin wong, Linh Pham
 *
 */
public class Node {
	public Node left;
	public Node right;
	public short character;
	public int frequency;
	 /**
	  * Constructor for Node
	  * @param left, a Node
	  * @param right, a Node
	  * @param character, character value (short)
	  * @param frequency, number of times each character shows up in the file
	  */
	public Node(Node left, Node right, short character, int frequency) {
		this.left = left;
		this.right = right;
		this.character = character;
		this.frequency = frequency;
	}

}

