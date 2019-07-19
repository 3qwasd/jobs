package jobs.toolkit.support.lucene.mmseg;

import com.chenlb.mmseg4j.ComplexSeg;
import com.chenlb.mmseg4j.Seg;

public class ComplexTokenizer extends MMSegTokenizer {

	@Override
	protected Seg initSeg() {
		return new ComplexSeg(MMSegAssistant.getDictionary());
	}

}
