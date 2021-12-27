/**
 * File: WordCounter.java
 * Author: Tamsin Rogers
 * Date: 4/7/20
 */
 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File; 
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*  analyzes, reads, and writes text files */
public class WordCounter
{
	BSTMap<String, Integer> map;												// the map of the binary search tree
	int count;																	// the total word count

	/* constructor, makes an empty BSTMap and sets the total word count to 0 */
	public WordCounter()
	{
		this.map = new BSTMap<String, Integer>( new AscendingString() );		// initialize the map
		this.count = 0;															// initialize the total word count
	}

	/* reads the file and builds the map */
	public void analyze(String filename)
	{
		try
		{
			FileReader fileReader = new FileReader(filename);				
			BufferedReader bufferedReader = new BufferedReader(fileReader);	
			String line = bufferedReader.readLine();						

			while(line != null)													// go through the line
			{
				String[] words = line.split("[^a-zA-Z0-9']");					// split the line into words
				
				for (int i = 0; i < words.length; i++) 							// for each word in the line
				{
					String word = words[i].trim().toLowerCase();				// the current word
					
					int value = 0;												// reset the value for each word
			
					if(word.length() != 0)										// don't process words of length 0
					{
						if(map.containsKey(word) == false)						// if this is the first occurrence of the word
						{
							value = 1;
							map.put(word, value);								// add the word to the map
						}
						else													// if the word is already in the map
						{	
							int newvalue = (map.get(word))+1;					// set the new value to the current value + 1
							map.put(word, newvalue);							// update the pair with the new value
						}
						count++;												// increment the total word count
					}
				}
				line = bufferedReader.readLine();								// read the next line
			}
			bufferedReader.close();
		}
		catch(FileNotFoundException ex) 
		{
			System.out.println("Board.read():: unable to open file " + filename );
		}
		catch(IOException ex) 
		{
			System.out.println("Board.read():: error reading file " + filename);
		}
	}
	
	/* return the total word count */
	public int getTotalWordCount()
	{
		return this.count;
	}
	
	/* return the number of unique words */
	public int getUniqueWordCount()
	{
		return map.size();														// return the size of the BSTMap
	}
	
	/* return the frequency value of the given word */
	public int getCount(String word)
	{
		return map.get(word);													// get the value at the given key
	}
	
	/* return the value of the given word divided by the total word count */
	public double getFrequency(String word)
	{
		float frequency = (float)(this.getCount(word));							// cast the frequency of the word to a float
		float count = (float)(this.getTotalWordCount());						// cast the total word count to a float
		float result = frequency/count;											
		return result;
	}
	
	/* returns a string of the structure of the tree */
    public String toString()
    {
    	return null;
    }
    
    /* writes the contents of the BSTMap to a word count file */
    public void writeWordCountFile( String filename )
    {
    	try 
    	{
      		File file = new File(filename);											// create the file
      		FileWriter writer = new FileWriter(filename);							// the file writer
      		
      		String s = "totalWordCount: " + this.getTotalWordCount() + "\n";		// the header (total word count of the file)
      		writer.write(s);														// write the header
      		
      		ArrayList<KeyValuePair<String,Integer>> entries = map.entrySet();		// the list of key value pairs
      		
      		for(KeyValuePair<String, Integer> entry : entries)						// for each pair in the list
    		{
    			s = entry.getKey() + " " + + entry.getValue() + "\n";				// space out the keys and values
    			writer.write(s);													// write each line
    		}
    		writer.close();															// close the file
      	} 
      	catch (IOException e) 
      	{
      		System.out.println("error");
    	}
	}
	
	/* reads the contents of a word count file and builds the map */
	public void readWordCountFile( String filename )
	{
		try
		{
			FileReader fileReader = new FileReader(filename);				
			BufferedReader bufferedReader = new BufferedReader(fileReader);	
			String line = bufferedReader.readLine();							
			
			line = bufferedReader.readLine();									// read the first line (header)

			while(line != null)													// go through the line
			{
				String[] values = line.split(" ");								// split the line into values (split at the space)
				String key = values[0];											// the key is the first value in the line
				int value = Integer.parseInt(values[1]);						// the value is the second value in the line (cast to an int)
				map.put(key,value);												// put the pair in the map
				line = bufferedReader.readLine();								// read the next line
			}
			bufferedReader.close();
		}
		catch(FileNotFoundException ex) 
		{
			System.out.println("Board.read():: unable to open file " + filename );
		}
		catch(IOException ex) 
		{
			System.out.println("Board.read():: error reading file " + filename);
		}
	}

    public static void main( String[] argv ) 
    {
    	WordCounter counter = new WordCounter();
    	
// 		counter.analyze("counttest.txt");
//     	System.out.println(counter.map.entrySet());
//     	System.out.println("Total word count: " + counter.getTotalWordCount());
//     	System.out.println("Unique word count: " + counter.getUniqueWordCount());
//     	System.out.println("getCount(it) (should be 4): " + counter.getCount("it"));
//     	System.out.println("getFrequency(it): " + counter.getFrequency("it"));
//    	counter.writeWordCountFile("testWrite.txt");							// writes the word count file
//    	counter.readWordCountFile("testWrite.txt");								// reads the word count file
    
    	System.out.println("Analyze all Reddit comment files (enter 1) or choose a specific year (enter 2)");
    	Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt(); 
		
		System.out.println("Enter an output file name (no need for .txt):");
		Scanner scan2 = new Scanner(System.in);
		String output = scan2.nextLine(); 
		
		if(choice == 1)
		{
			String year = "2008";
			String name = "reddit_comments_" + year + ".txt";						// get the right file								
			System.out.println("Analyzing comments from " + year);
			double start = System.currentTimeMillis();								// start the timer
			counter.analyze(name);													// analyze the file
			double stop = System.currentTimeMillis();								// start the timer
			double time = stop-start;			
			counter.writeWordCountFile(output + "(" + year + ").txt");				// generate a word count file
			System.out.println("Time to process: " + time + "ms");
			System.out.println("Total word count: " + counter.getTotalWordCount());
			System.out.println("Unique word count: " + counter.getUniqueWordCount());
			
			year = "2009";
			name = "reddit_comments_" + year + ".txt";						// get the right file								
			System.out.println("Analyzing comments from " + year);
			start = System.currentTimeMillis();								// start the timer
			counter.analyze(name);													// analyze the file
			stop = System.currentTimeMillis();								// start the timer
			time = stop-start;			
			counter.writeWordCountFile(output + "(" + year + ").txt");				// generate a word count file
			System.out.println("Time to process: " + time + "ms");
			System.out.println("Total word count: " + counter.getTotalWordCount());
			System.out.println("Unique word count: " + counter.getUniqueWordCount());
			
			year = "2010";
			name = "reddit_comments_" + year + ".txt";						// get the right file								
			System.out.println("Analyzing comments from " + year);
			start = System.currentTimeMillis();								// start the timer
			counter.analyze(name);													// analyze the file
			stop = System.currentTimeMillis();								// start the timer
			time = stop-start;			
			counter.writeWordCountFile(output + "(" + year + ").txt");				// generate a word count file
			System.out.println("Time to process: " + time + "ms");
			System.out.println("Total word count: " + counter.getTotalWordCount());
			System.out.println("Unique word count: " + counter.getUniqueWordCount());
			
			year = "2011";
			name = "reddit_comments_" + year + ".txt";						// get the right file								
			System.out.println("Analyzing comments from " + year);
			start = System.currentTimeMillis();								// start the timer
			counter.analyze(name);													// analyze the file
			stop = System.currentTimeMillis();								// start the timer
			time = stop-start;			
			counter.writeWordCountFile(output + "(" + year + ").txt");			// generate a word count file
			System.out.println("Time to process: " + time + "ms");
			System.out.println("Total word count: " + counter.getTotalWordCount());
			System.out.println("Unique word count: " + counter.getUniqueWordCount());
			
			year = "2012";
			name = "reddit_comments_" + year + ".txt";						// get the right file								
			System.out.println("Analyzing comments from " + year);
			start = System.currentTimeMillis();								// start the timer
			counter.analyze(name);													// analyze the file
			stop = System.currentTimeMillis();								// start the timer
			time = stop-start;			
			counter.writeWordCountFile(output + "(" + year + ").txt");				// generate a word count file
			System.out.println("Time to process: " + time + "ms");
			System.out.println("Total word count: " + counter.getTotalWordCount());
			System.out.println("Unique word count: " + counter.getUniqueWordCount());
			
			year = "2013";
			name = "reddit_comments_" + year + ".txt";						// get the right file								
			System.out.println("Analyzing comments from " + year);
			start = System.currentTimeMillis();								// start the timer
			counter.analyze(name);													// analyze the file
			stop = System.currentTimeMillis();								// start the timer
			time = stop-start;			
			counter.writeWordCountFile(output + "(" + year + ").txt");				// generate a word count file
			System.out.println("Time to process: " + time + "ms");
			System.out.println("Total word count: " + counter.getTotalWordCount());
			System.out.println("Unique word count: " + counter.getUniqueWordCount());
			
			year = "2014";
			name = "reddit_comments_" + year + ".txt";						// get the right file								
			System.out.println("Analyzing comments from " + year);
			start = System.currentTimeMillis();								// start the timer
			counter.analyze(name);													// analyze the file
			stop = System.currentTimeMillis();								// start the timer
			time = stop-start;			
			counter.writeWordCountFile(output + "(" + year + ").txt");			// generate a word count file
			System.out.println("Time to process: " + time + "ms");
			System.out.println("Total word count: " + counter.getTotalWordCount());
			System.out.println("Unique word count: " + counter.getUniqueWordCount());
			
			year = "2015";
			name = "reddit_comments_" + year + ".txt";						// get the right file								
			System.out.println("Analyzing comments from " + year);
			start = System.currentTimeMillis();								// start the timer
			counter.analyze(name);													// analyze the file
			stop = System.currentTimeMillis();								// start the timer
			time = stop-start;			
			counter.writeWordCountFile(output + "(" + year + ").txt");			// generate a word count file
			System.out.println("Time to process: " + time + "ms");
			System.out.println("Total word count: " + counter.getTotalWordCount());
			System.out.println("Unique word count: " + counter.getUniqueWordCount());
		}
		
		if(choice == 2)
		{
			System.out.println("Enter the year of the file you would like to analyze (2008-2015):");
			Scanner scan1 = new Scanner(System.in);
			String year = scan1.nextLine(); 
			String name = "reddit_comments_" + year + ".txt";						// get the right file								
			System.out.println("Analyzing comments from " + year);
			double start = System.currentTimeMillis();								// start the timer
			counter.analyze(name);													// analyze the file
			double stop = System.currentTimeMillis();								// start the timer
			double time = stop-start;			
			counter.writeWordCountFile(output + "(" + year + ").txt");			// generate a word count file
			System.out.println("Time to process: " + time + "ms");
			System.out.println("Total word count: " + counter.getTotalWordCount());
			System.out.println("Unique word count: " + counter.getUniqueWordCount());
		}
		
		else
		{
			return;
		}
    }
}