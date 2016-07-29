package jobs.toolkit.support.lucene.mmseg;

import java.io.IOException;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.Word;

public abstract class MMSegTokenizer extends Tokenizer {
	
	
	private MMSeg mmSeg;

	private CharTermAttribute termAtt;
	private OffsetAttribute offsetAtt;
	private TypeAttribute typeAtt;

	private final Seg seg;
	
	
	
	public MMSegTokenizer() {
		super();
		this.seg = this.initSeg();
		termAtt = addAttribute(CharTermAttribute.class);
		offsetAtt = addAttribute(OffsetAttribute.class);
		typeAtt = addAttribute(TypeAttribute.class);
	}
	
	protected abstract Seg initSeg();

	@Override
	public boolean incrementToken() throws IOException {
		this.clearAttributes();
		Word w = this.mmSeg.next();
		if(w != null){
			this.termAtt.copyBuffer(w.getSen(), w.getWordOffset(), w.getLength());
			this.offsetAtt.setOffset(w.getStartOffset(), w.getEndOffset());
			this.typeAtt.setType(w.getType());
			return true;
		}
		return false;
	}

	@Override
	public void reset() throws IOException {
		super.reset();
		if(this.mmSeg == null) this.mmSeg = new MMSeg(this.input, this.seg);
		this.mmSeg.reset(this.input);
	}
	
	
}
