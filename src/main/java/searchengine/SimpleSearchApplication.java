package searchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
/**
 * 
 * @author Gireesh Gopinath
 * simple search engine app with nlp
 */
@SpringBootApplication
public class SimpleSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleSearchApplication.class, args);
	}
	
	@Bean(initMethod="init")
	@Scope("singleton")
	   public TfIdfInvertedIndex getTfIdfInvertedIndex() {
	      return new TfIdfInvertedIndex();
	   }


}
