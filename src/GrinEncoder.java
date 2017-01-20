import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Constructing the frequency map and encoding to the .grin file
 * @author Linh Pham,Benjamin Wong
 *
 */
public class GrinEncoder {

	/**
	 * Create a frequency map from a given file
	 * @param file, a file
	 * @return a map that maps character to its frequency in the file
	 * @throws IOException
	 */
	public static Map<Short, Integer> createFrequencyMap(String file) throws IOException {
		BitInputStream in = new BitInputStream(file);
		Map<Short, Integer> frequencyMap = new HashMap<Short, Integer>();
		while (in.hasBits()) {
			short currentChar = (short) in.readBits(8);
			if (frequencyMap.containsKey(currentChar)) {			
				if (frequencyMap.get(currentChar)== null) {
					frequencyMap.put(currentChar,  2);
				}
				else {
					frequencyMap.put(currentChar, frequencyMap.get(currentChar) + 1);
				}
			} else {
				frequencyMap.put(currentChar, 1);
			}
		}
		return frequencyMap;
	}

	/**
	 * Encode the given file denoted by infile and writes the output to the 
	 * .grin file denoted by outfile.
	 * @param infile, a string
	 * @param outfile, a string
	 * @throws IOException
	 */
	public static void encode (String infile, String outfile) throws IOException {
		Map<Short, Integer> frequencyMap = createFrequencyMap(infile);
		BitInputStream in = new BitInputStream(infile);
		BitOutputStream out = new BitOutputStream(outfile);
		out.writeBits(1846, 32);
		out.writeBits(frequencyMap.size(), 32);
		for(Short key : frequencyMap.keySet()) {
			out.writeBits(key, 16);
			out.writeBits(frequencyMap.get(key), 32);
		}

		HuffmanTree huffTree = new HuffmanTree(frequencyMap);
		huffTree.encode(in, out);
		in.close();
		out.close();
	}
}
