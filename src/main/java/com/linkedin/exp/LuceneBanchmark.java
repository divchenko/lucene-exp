package com.linkedin.exp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneBanchmark {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Directory dir = FSDirectory.open(new File("/Users/divchenk/projects/lucene-test/index"));
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_50);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_50, analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			
      IndexWriter writer = new IndexWriter(dir, iwc);
			
			BufferedReader reader = new BufferedReader(new FileReader("/Users/divchenk/projects/lucene-test/data/text.txt"));
			String line = null;
			try
			{
  			while (null != (line = reader.readLine()))
  			{
  				Document doc = new Document();
  				
          Field textField = new TextField("text", line, Field.Store.NO);
          doc.add(textField);
          
          Field longField = new LongField("id", (long)(Math.random() * 100000), Field.Store.NO);
          doc.add(longField);
          
          writer.addDocument(doc);
  			}
			} finally {
				reader.close();
			}
			
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
