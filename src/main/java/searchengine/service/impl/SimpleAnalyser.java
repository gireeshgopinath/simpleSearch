package searchengine.service.impl;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import searchengine.service.Analyser;
import searchengine.service.StopwordRemover;
import searchengine.service.Tokenizer;

/**
 * 
 * @author Gireesh Gopinath
 *  simple analyser for nlp process
 */
public class SimpleAnalyser extends Analyser implements Tokenizer,StopwordRemover {

	
	/**
	 * based on space were able to split and convert to lower case letter
	 */
	@Override
	public String[] tokenize(String text) {
		return  text.toLowerCase().split("\\s+");
	}

	/**
	 *  remove standard stop word that we can remove 
	 */
	@Override
	public String removeStoWord(String document) {
		 ArrayList<String> wordList = Stream.of(document.split(" ")).
				 collect(Collectors.toCollection(ArrayList<String>::new));
		 		wordList.removeAll(bagofwords);
			    return wordList.stream().collect(Collectors.joining(" "));
	}
	
	
	/**
	 *  setting analyser for each documents and query
	 */
	@Override
	public String[] analyse(String document) {
		if(document!=null && !document.isBlank())
			return tokenize(removeStoWord(document));
		else
			return new String[0];
	}

}
