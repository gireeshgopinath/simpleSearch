package searchengine;

/***
 * 
 * @author Gireesh Gopinath
 * search and implement
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import searchengine.respose.SearchResult;
import searchengine.service.Analyser;
import searchengine.service.impl.SimpleAnalyser;

public class TfIdfInvertedIndex {
    private Map<String, List<Posting>> index;
    private volatile  int N; // number of documents
    private double augFrq;  // augmented frequency can be set while initialize
    private Analyser analyser;
    
    /**
     * initialize with in-memory doc and set analayser
     *      
     **/
    public void init() {
    	 this.index = new ConcurrentHashMap<>();
         this.N = 0;
         this.augFrq=0.0;
         this.analyser= new SimpleAnalyser();
         
         this.addDocument("the brown fox jumped over the brown dog", "Document 1");
         this.addDocument("the lazy brown dog sat in the corner", "Document 2");
         this.addDocument("the red fox bit the lazy dog", "Document 3");
         
    }
   
    
    public void addDocument(String data, String documentId) {
        N++;
        
        String [] words= this.analyser.analyse(data);
        /**
         *  Calculate the frequency of each word
         */
        Map<String, Integer> wordFrequencies = new HashMap<>();
        for (String word : words) {
            wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
          }
        
        
        for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
        	
        	 List<Posting> postingList = index.getOrDefault(entry.getKey(),new ArrayList<>());
            
             /**
              *  tf of each word in document  
              */
             double tf= augFrq + ((double) entry.getValue() / words.length);
             
             Posting posting = new Posting(documentId,tf);
             postingList.add(posting);
             index.put(entry.getKey(), postingList);
        }
    }
    
    
/**
 * search query api for doc index
 * @param data
 * @return
 */
	public List<SearchResult> search(String data) {
		String [] queries= this.analyser.analyse(data);
    	 Map<String, SearchResult> documentResult = new HashMap<>();
			for (String query : queries) {

				List<Posting> postingList = index.get(query);
				if (postingList == null) {
					return new ArrayList<>();
				}

				int df = postingList.size();
				double idf = Math.log(N / (double) df);
				for (Posting posting : postingList) {
					posting.setTfIdf(posting.getTf() * idf);
				}

				for (Posting posting : postingList) {
					
					
					/**
					 * for multiple search term match the same document sum up the ranking
					 */
					if (documentResult.containsKey(posting.getDocumentId())) {
						SearchResult docresult = documentResult.get(posting.getDocumentId());
						docresult.setTfIdf(docresult.getTfIdf() + posting.getTfIdf());
					} else {
						documentResult.put(posting.getDocumentId(),
								new SearchResult(posting.getDocumentId(), posting.getTfIdf()));
					}

				}
			}
    	
    
        return documentResult.values().stream().sorted().collect(
                Collectors.toCollection(ArrayList::new));
    }
	
	
	
	

    private class Posting {
        private String documentId;
       
        private double tf;
        private double tfIdf;

        public Posting(String documentId,double tf) {
            this.documentId = documentId;
           
            this.tf=tf;
        }

        public String getDocumentId() {
            return documentId;
        }

        public double getTf() {
            return tf;
        }

       

        public double getTfIdf() {
            return tfIdf;
        }

        public void setTfIdf(double tfIdf) {
            this.tfIdf = tfIdf;
        }

		

    }


}