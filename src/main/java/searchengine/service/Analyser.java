package searchengine.service;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Gireesh Gopinath
 * simple english analyzer
 */
public abstract class Analyser {
	protected List<String> bagofwords= Arrays.asList("the","is","i");
	public abstract String[] analyse(String document);
}
