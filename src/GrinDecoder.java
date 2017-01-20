import java.io.IOException;
import java.util.HashMap;

/**
 * Decodes Huffman Codes from a .grin file
 * @author Linh Pham, Benjamin Wong
 *
 */
public class GrinDecoder {
	/**
	 * Decodes the .grin file denoted by infile and writes the output to the 
	 * .grin file denoted by outfile.
	 * @param infile, a string
	 * @param outfile, a string
	 * @throws IOException
	 */
	public static void decode (String infile, String outfile) throws IOException {
		BitInputStream in = new BitInputStream(infile);
		BitOutputStream out = new BitOutputStream(outfile);
		int magicNumber = in.readBits(32);
		if (magicNumber != 1846) {
			throw new IllegalArgumentException();
		} else {
			int numCode = in.readBits(32);
			HashMap<Short, Integer> map = new HashMap<Short, Integer>();
			for (int i = numCode; i > 0; i--) {
				short codeValue = (short) in.readBits(16);
				int occurence = in.readBits(32);
				map.put(codeValue, occurence);
			}
			HuffmanTree huffTree = new HuffmanTree(map);
			huffTree.decode(in, out);
			in.close();
			out.close();
		}
	}
}