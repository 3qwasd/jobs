package jobs.toolkit.support.lucene.mmseg;

import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.SimpleSeg;

public class SimpleTokenizer extends MMSegTokenizer {

	@Override
	protected Seg initSeg() {
		return new SimpleSeg(MMSegAssistant.getDictionary());
	}

}
