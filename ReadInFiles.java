package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.jeremybrooks.knicker.dto.Related;
import net.jeremybrooks.knicker.dto.Word;
import net.jeremybrooks.knicker.Knicker.RelationshipType;
import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordApi;

public class ReadInFiles {
	public final static boolean SEMANTIC = false;
	public final static boolean BIGRAMSONLY = true;
	public final static boolean INCLUDEBIGRAMS= true;
	public static void main(String args[]) {
		ReadInFiles RIF = new ReadInFiles();
		returnClass posts = RIF.readAll("src/main/RedditFiles/");
		System.out.println("Loaded all data");
		posts = RIF.preProcess(posts);
		RIF.writeBasicFile("AllData.csv",posts);
		Process p =null;
		System.out.println("Preprocessing with python...");
		try {
			p = Runtime.getRuntime().exec("python SemanticAnalysis.py");
			while(p.isAlive()) {
				
			}
			System.out.println("Preprocess with python Finished");
			System.out.println("loading from python...");
			posts = RIF.ReadCSV("SentimentAnalysis.csv");
			System.out.println("Done loading from python");
			
			RIF.writeFile("OutputBigramOnly.csv", posts );
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(p==null) {
			System.out.println("failed to start the python script");
		}
		else {
			
		}
		
		
	
		
		/*posts = new returnClass(new ArrayList<String>(), new ArrayList<String>());
		posts.post.add("aborted");
		posts.post.add("abort");
		posts.post.add("abortionist");
		posts.post.add("cat");
		posts.post.add("cats");
		posts = RIF.fullThesaurus(posts);
		for(int i =0;i<posts.post.size();i++) {
			System.out.println(posts.post.get(i));
		}*/
	}
	

	
	private void writeFile(String filename, returnClass input ) {
		System.out.println("Creating Bag of Words");
		bagOfWords BOW = createBagOfWords(input.post);
		System.out.println("Converting to Bag of Words format");
		input = BOW.computeCSVfromBag(input);
		System.out.println("Writing to file");
		try {
        	
            BufferedWriter w = new BufferedWriter((new FileWriter(filename)));
            String temp= "unique,"+BOW.bag.get(0);
            for(int i=1;i<BOW.bag.size();i++) {
            	temp = temp +","+BOW.bag.get(i);
            }
            temp=temp+" , subreddit";
            w.write(temp);
            w.newLine();
            w.flush();
            for(int i =0;i< input.post.size();i++) {
                w.write(input.post.get(i));//+" , " +input.subreddit.get(i));
                w.newLine();
                w.flush();
            }
            w.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	private void writeBasicFile(String filename, returnClass input ) {
		//bagOfWords BOW = createBagOfWords(input.post);
		//input = BOW.computeCSVfromBag(input);
		try {
        	
            BufferedWriter w = new BufferedWriter((new FileWriter(filename)));
            String temp ="";//"unique,"+BOW.bag.get(0);
           /* for(int i=1;i<BOW.bag.size();i++) {
            	temp = temp +","+BOW.bag.get(i);
            }*/ temp = "post";
            temp=temp+" , subreddit";
            w.write(temp);
            w.newLine();
            w.flush();
            for(int i =0;i< input.post.size();i++) {
                w.write(input.post.get(i)+"," +input.subreddit.get(i));
                w.newLine();
                w.flush();
            }
            w.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	public returnClass preProcess(returnClass input) {
		returnClass output = new returnClass(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<Double>());
		//TODO create bag of words
		ArrayList<String> tempSubs = new ArrayList<String>();
		for(int i =0;i<12;i++) {
			ArrayList<String> temp = new ArrayList<String>();
			for(int j =0;j<input.post.size();j++) {
				if(!tempSubs.contains(input.subreddit.get(j))) {
					tempSubs.add(input.subreddit.get(j));
				}
				if(i==tempSubs.indexOf(input.subreddit.get(j))) {
					if(!temp.contains(input.post.get(j)) && input.post.get(j).length()>0) {
						
						temp.add(input.post.get(j));
						output.post.add(input.post.get(j).replaceAll("[^a-zA-Z0-9]", " "));
						output.subreddit.add(input.subreddit.get(j));
						//output.semantic.add(input.semantic.get(j));
					}
				}
			}
			//TODO get rid of duplicates in the same subreddit group
			//TODO convert to bag of words
		}
		
		return output;
	}
	public bagOfWords createBagOfWords(ArrayList<String> input){
		ArrayList<Integer> count = new ArrayList<Integer>();
		ArrayList<String> words = new ArrayList<String>();
		if(!BIGRAMSONLY) {
			for(int i=0;i<input.size();i++) {
				String[] array = input.get(i).replaceAll("[^a-zA-Z0-9]", " ").toLowerCase().split("\\ ");
				for(int j =0;j<array.length;j++) {
					if(words.contains(array[j])) {
						int temp = words.indexOf(array[j]);
						//count.set(temp, count.get(temp)+1);
					}
					else {
						words.add(array[j]);
						count.add(1);
					}
				}
			}
		}
		if(INCLUDEBIGRAMS) {
			for(int i=0;i<input.size();i++) {
				String[] array = input.get(i).replaceAll("[^a-zA-Z0-9]", " ").toLowerCase().split("\\ ");
				for(int j =1;j<array.length;j++) {
					if(words.contains(array[j-1]+""+array[j])) {
						int temp = words.indexOf(array[j-1]+""+array[j]);
						count.set(temp, count.get(temp)+1);
					}
					else {
						words.add(array[j]);
						count.add(1);
					}
				}
			}
		}
		int temp =0;
		while(temp<words.size()) {
			if(count.get(temp)<=1 || words.get(temp).length()<=1) {
				words.remove(temp);
				count.remove(temp);
			}
			else {
				temp++;
			}
		}
		return new bagOfWords(words);
	}
	public returnClass readAll(String directory) {
		ArrayList<String> results = new ArrayList<String>();
		ArrayList<String> origin = new ArrayList<String>();

		File[] files = new File(directory).listFiles();
		//System.out.println(files.length);
		//If this pathname does not denote a directory, then listFiles() returns null. 
		//int temp=0;
		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		    else if(file.isDirectory()) {
		    	File[] subFiles = new File(file.getPath()).listFiles();
		    	for(File subFile : subFiles) {
		    		if(subFile.isFile()) {
		    			if(subFile.getName().split("\\d").length==1 || !(subFile.getName().split("\\ ").length==1)) {
		    				results.add(subFile.getName().substring(0,subFile.getName().length()-4));
		    				origin.add(file.getName());
		    				
		    				
		    			}
		    		}
		    	}
		    }
		}
		returnClass returned = new returnClass(results,origin, null);
		return returned;
	}
	protected returnClass ReadCSV(String s) throws IOException {
		File file = new File(s);
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line;
		ArrayList<String> posts = new ArrayList<String>();
		ArrayList<String> subreddits = new ArrayList<String>();
		ArrayList<Double> semantic = new ArrayList<Double>();
		while((line = in.readLine())!=null) {
			String[] vals = line.split(",");
			posts.add(vals[0]);
			subreddits.add(vals[1]);
			semantic.add(Double.parseDouble(vals[2].substring(1, vals[2].length()-2)));
			//System.out.println(semantic.get(semantic.size()-1));
		}
		in.close();
		return new returnClass(posts,subreddits,semantic);
	}
}
class returnClass{
	ArrayList<String> post;
	ArrayList<String> subreddit;
	ArrayList<Double> semantic;
	protected returnClass(ArrayList<String> results, ArrayList<String> origin, ArrayList<Double> semantic) {
		this.post=results;
		this.subreddit=origin;
		this.semantic=semantic;
	}
}
class bagOfWords{
	ArrayList<String> bag;
	protected bagOfWords(ArrayList<String> input) {
		bag = new ArrayList<String>();
		for(int i =0;i<input.size();i++) {
			bag.add(input.get(i));
		}
		
	}
	private int[] bagValue(String[] input, double semantic) {
		int[] output = new int[bag.size()+1];
		if(!ReadInFiles.BIGRAMSONLY) {
			for(int i =0;i<input.length;i++) {
				if(semantic>=0 || !ReadInFiles.SEMANTIC) {
					if(bag.contains(input[i])) {
						//System.out.println(input[i]);
						output[bag.indexOf(input[i])+1]++;
					}
					else {
						if(input[i].length()>1) {
							output[1]++;
						}
					}
				}
				else {
					if(bag.contains(input[i])) {
						//System.out.println(input[i]);
						output[bag.indexOf(input[i])+1]--;
					}
					else {
						if(input[i].length()>1) {
							output[1]--;
						}
					}
				}
			}
		}
		if(ReadInFiles.INCLUDEBIGRAMS) {
			for(int i =1;i<input.length;i++) {
				
				if(bag.contains(input[i-1]+""+input[i])) {
					//System.out.println(input[i]);
					output[bag.indexOf(input[i-1]+""+input[i])+1]++;
				}
				else {
					if(input[i].length()>1) {
						output[0]++;
					}
				}
			}
		}
		return output;
	}
	private String bagToString(String sentence, double semantic) {
		String[] vals = sentence.replaceAll("[^a-zA-Z0-9]", " ").toLowerCase().split("\\ ");
		
		int[] bag = bagValue(vals, semantic);
		String returned ="";
		for(int i=0;i<bag.length;i++) {
			returned=returned+bag[i]+",";
		}
		return returned;
	}
	protected returnClass computeCSVfromBag(returnClass input) {
		for(int i =0;i<input.post.size();i++) {
			
			input.post.set(i, bagToString(input.post.get(i), input.semantic.get(i))+""+input.subreddit.get(i));
		}
		return input;
	}

}
