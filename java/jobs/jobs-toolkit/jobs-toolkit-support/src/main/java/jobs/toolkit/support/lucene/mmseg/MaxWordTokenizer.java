package jobs.toolkit.support.lucene.mmseg;

import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.Seg;

public class MaxWordTokenizer extends MMSegTokenizer {

	@Override
	protected Seg initSeg() {
		return new MaxWordSeg(MMSegAssistant.getDictionary());
	}
}
