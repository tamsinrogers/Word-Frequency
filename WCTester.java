/**
 * File: WCTester.java
 * Author: Tamsin Rogers
 * Date: 4/13/20
 */

/* tests the WordCounter read/write file methods */
public class WCTester
{
	/* tests the methods */
	public static void main( String[] argv ) 
    {
		WordCounter test = new WordCounter();			// create a new WordCounter
		test.analyze("counttest.txt");					// use analyze to load counttest.txt.
		test.writeWordCountFile("counts_ct.txt");		// use writeWordCount to write the results to counts_ct.txt.
		test.readWordCountFile("counts_ct.txt");		// use readWordCount to load counts_ct.txt.
		test.writeWordCountFile("counts_ct_v2.txt");	// use writeWordCount to write counts_ct_v2.txt.
	}
}