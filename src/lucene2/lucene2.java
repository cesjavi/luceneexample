package lucene2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.MutablePointValues;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DocumentStoredFieldVisitor;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.StoredFieldVisitor;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.BaseDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RandomAccessInput;

public class lucene2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
				
		Path path = Files.createTempDirectory("tempIndex");
		Analyzer analyzer = new StandardAnalyzer();
		
		Directory directory = FSDirectory.open(path);
		
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = new IndexWriter(directory,config);
		
		Document doc = new Document();
		String text = "esto es una prueba de lucene. espero funciones";
		doc.add(new TextField("fieldname", text, Field.Store.YES));
		indexWriter.addDocument(doc);
		
		Document doc2 = new Document();
		String text2 = "asdfasdfasdfasdfasdf asd fsad fsad fsad fsad fsad";
		doc2.add(new TextField("fieldname", text2, Field.Store.YES));
		indexWriter.addDocument(doc2);
		
		indexWriter.close();
		
		IndexReader indexReader = DirectoryReader.open(directory);
		
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		
		QueryParser parser = new QueryParser("fieldname", analyzer);
		Query query = null;
		try {
			query = parser.parse("fsad");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int hitsPerPage = 10;
		
		TopDocs docs = indexSearcher.search(query, hitsPerPage);
		ScoreDoc[] hits = docs.scoreDocs;
		//StoredFieldVisitor sfv = docs.scoreDocs[];
		
		int end = (int) Math.min(docs.totalHits.value, hitsPerPage);
		
		System.out.println("Total Hits: " + docs.totalHits);
		System.out.println("results: ");
		
		for (ScoreDoc sd: hits) {
			Document doc3 = indexSearcher.doc(sd.doc);
			System.out.println("Content: " + doc3.get("fieldname"));
		}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
