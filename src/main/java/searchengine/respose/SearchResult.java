package searchengine.respose;

/**
 * respose object for api
 * @author Gireesh Gopinath
 *
 */

public class SearchResult implements Comparable<SearchResult>{
	String documentId;
	

	double tfIdf;
	public SearchResult(String documentId,double tfIdf ) {
		this.documentId=documentId;
		this.tfIdf=tfIdf;
	}
	
	@Override
	public int compareTo(SearchResult o) {
		if(tfIdf==o.tfIdf)  
		return 0;  
		else if(tfIdf<o.tfIdf)  
		return 1;  
		else  
		return -1;  
	}

	public double getTfIdf() {
		return tfIdf;
	}

	public void setTfIdf(double tfIdf) {
		this.tfIdf = tfIdf;
	}
	
	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
}