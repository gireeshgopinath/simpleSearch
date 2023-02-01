package searchengine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import searchengine.TfIdfInvertedIndex;
import searchengine.respose.SearchResult;
/**
 *  Api for search and add document
 * @author Gireesh Gopinath
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/search")
public class SearchEngineApi {

	@Autowired
	TfIdfInvertedIndex tfIdfInvertedIndex;

	/**
	 * @param saerchQuery
	 * @return list of searchresult
	 * @throws Exception
	 */
	@GetMapping("/query/{searchQuery}")
	public List<SearchResult> getData(@PathVariable String searchQuery) throws Exception {
		return tfIdfInvertedIndex.search(searchQuery);
	}
	
	@PostMapping("/insertdock/{documentid}")
	public String  postData(@RequestBody String document,@PathVariable String documentid ) throws Exception {
		 tfIdfInvertedIndex.addDocument(document, documentid);
		 return "Success";
	}
}
