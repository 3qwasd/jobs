package jobs.toolkit.support.lucene.mmseg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MMSegTokenizerTest {
	
	BufferedReader reader;
	private CharTermAttribute termAtt;
	private OffsetAttribute offsetAtt;
	private TypeAttribute typeAtt;
	MMSegTokenizer tokenizer;
	@Before
	public void setUp() throws Exception {
		String path = Thread.currentThread().getContextClassLoader().getResource(".").getPath();
		MMSegAssistant.init(path + "/dics");
		reader = new BufferedReader(new FileReader(path + "/text/test.text"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void simpleTest() throws IOException {
		tokenizer = new SimpleTokenizer();
		tokenizer.setReader(reader);
		tokenizer.reset();
		termAtt = tokenizer.addAttribute(CharTermAttribute.class);
		offsetAtt = tokenizer.addAttribute(OffsetAttribute.class);
		typeAtt = tokenizer.addAttribute(TypeAttribute.class);
		while(tokenizer.incrementToken()){
			System.out.print(termAtt.toString() + ";");
			System.out.print(offsetAtt.startOffset() + "----" + offsetAtt.endOffset() + ";");
			System.out.println(typeAtt.type());
		}
		tokenizer.close();
	}
	@Test
	public void complexTest() throws IOException {
		tokenizer = new ComplexTokenizer();
		tokenizer.setReader(reader);
		tokenizer.reset();
		termAtt = tokenizer.addAttribute(CharTermAttribute.class);
		offsetAtt = tokenizer.addAttribute(OffsetAttribute.class);
		typeAtt = tokenizer.addAttribute(TypeAttribute.class);
		while(tokenizer.incrementToken()){
			System.out.print(termAtt.toString() + ";");
			System.out.print(offsetAtt.startOffset() + "----" + offsetAtt.endOffset() + ";");
			System.out.println(typeAtt.type());
		}
		tokenizer.close();
	}
	@Test
	public void maxWordTest() throws IOException {
		tokenizer = new MaxWordTokenizer();
		tokenizer.setReader(reader);
		tokenizer.reset();
		termAtt = tokenizer.addAttribute(CharTermAttribute.class);
		offsetAtt = tokenizer.addAttribute(OffsetAttribute.class);
		typeAtt = tokenizer.addAttribute(TypeAttribute.class);
		while(tokenizer.incrementToken()){
			System.out.print(termAtt.toString() + ";");
			System.out.print(offsetAtt.startOffset() + "----" + offsetAtt.endOffset() + ";");
			System.out.println(typeAtt.type());
		}
		tokenizer.close();
	}
}
