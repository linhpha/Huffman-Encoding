import java.io.IOException;


/**
 * A driver for encoding and decoding a Huffman Tree
 * @author Linh Pham, Benjamin Wong
 *
 */
public class Grin {
	public static void main (String[] args) throws IOException {
		if (args[0].equals("encode")) {
			GrinEncoder.encode(args[1], args[2]);
		} else if (args[0].equals("decode")) {
			GrinDecoder.decode(args[1], args[2]);
		} else {
			System.out.println("Invalid command. Input is in form "
					+ "encode|decode infile outfile");
		}
	}
}
