
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Constructor of the HuffmanTree and encode/decode implementations for the HuffmanTree
 * @author Linh Pham, Benjamin Wong
 *
 */
public class HuffmanTree {
	
	public HashMap<Short, String> characterCode;
	public PriorityQueue<Node> queueMap;
	public Node root;

	/**
	 * Constructor for the HuffmanTree
	 * @param m, a frequency map of character and frequency
	 *	Creates a HuffmanTree containing one node after combining every node from the map
	 */
	public HuffmanTree(Map<Short, Integer> m) {
		this.queueMap = new PriorityQueue<Node>();
		this.characterCode = new HashMap<Short, String>();
		Set<Short> setKey = m.keySet();
		for (Short x : setKey) {
			Node temp = new Node(null, null, x, m.get(x));
			queueMap.add(temp);
		}
		queueMap.add(new Node (null, null, (short) 256, 1));
		this.root = combineTree(queueMap);
		getHuffmanCode(root, "");
	}

	/**
	 * Combine two nodes 
	 * @param firstNode
	 * @param secondNode
	 * @return a new node
	 */
	public Node combine (Node firstNode, Node secondNode) {
		return new Node (firstNode, secondNode, (short) -1, 
				firstNode.frequency + secondNode.frequency);
	}

	/**
	 * Combine all nodes in the queue
	 * @param queue, a priority queue containing nodes
	 * @return one combined node
	 */
	public Node combineTree (PriorityQueue<Node> queue) {
		if(queue.size() == 1) {
			return queue.poll();
		} else {
			Node firstPriority = queue.poll();
			Node secondPriority = queue.poll();
			queue.add(combine(firstPriority, secondPriority));		 	
			return combineTree(queue);
		}
	}

	/**
	 * Getting the Huffman code by traversing through the node
	 * @param curNode, a node
	 * @param code, a Huffman code represented by string
	 */
	public void getHuffmanCode(Node curNode, String code) {
		if (curNode.character == -1) {			
			getHuffmanCode(curNode.left, code + "0");
			getHuffmanCode(curNode.right, code + "1");
		} else {
			characterCode.put(curNode.character, code);
		}
	}

	/**
	 * Encodes the file given as a stream of bits into a compressed format using this Huffman tree
	 * and writes to the out file
	 * @param in, a BitInputStream
	 * @param out, a BitOutputStream
	 */
	public void encode(BitInputStream in, BitOutputStream out) {
		while (in.hasBits()) {
			short code = (short) in.readBits(8);
			String temp = characterCode.get(code);
			if	 (temp!= null) {
				for (int i = 0; i < temp.length(); i++) {
					out.writeBit(temp.charAt(i) - 48);
				}		
			}
		}
		String eof = characterCode.get((short) 256);
		for (int i = 0; i < eof.length(); i++) {
			out.writeBit(eof.charAt(i) - 48);
		}
		in.close();
		out.close();
	}

	/**
	 * Helper method that gets the character corresponding to the Huffman code
	 * @param code, a Huffman code represented by string
	 * @return a character value (short)
	 */
	public short decodeHelper(String code) {
		for (short key : characterCode.keySet()) {
			if (characterCode.get(key).equals(code)) {
				return key;
			}			
		}
		return (short) -1;
	}

	/**
	 * Decodes a stream of huffman codes from a file into their uncompressed form and writes to the
	 * out file
	 * @param in, a BitInputStream
	 * @param out, a BitOutputStream
	 */
	public void decode(BitInputStream in, BitOutputStream out) {
		String code = "";
		while (in.hasBits()) {
			code += in.readBit();		
			if(characterCode.containsValue(code)) {			
				short temp = decodeHelper(code);
				if (temp == (short) 256) {
					return;
				}
				out.writeBits(temp, 8);
				code = "";
			}
		}
		in.close();
		out.close();
	}
}
