package jobs.toolkit.job;

public enum JobState {
	START,      //开始, 标志着作业生命周期开始
	WAIT, 		//等待
	SCHEDULE,   //调度
	DISPATCH,   //派发
	EVALUATE,   //执行
	FINISH,     //完成(表示一次执行成功完成, 作业包括周期作业, 周期作业会多次, 一次执行完是FINISH)
	FAIL,       //失败(表示一次执行失败)
	COMPLETE;   //结束(表示作业结束, 作业执行完需要执行的所有次数), 标志着作业生命周期结束
	
	public final static boolean MARITX[][] = {
	/*JobState*  | START |  WAIT  | SCHEDULE | DISPATCH | EVALUATE | FINISH |  FAIL  | COMPLETE */
	/* START  */ { true,    true,    true,      false,     false,    false,   false,    false },
	/*  WAIT  */ { false,   true,    true,      false,     false,    false,   false,    false },
	/*SCHEDULE*/ { false,   false,   true,      true,      false,    false,   false,    false },
	/*DISPATCH*/ { false,   false,   false,     true,      true,     false,   false,    false },
	/*EVALUATE*/ { false,   false,   false,     false,     true,     true,    true,     false },
	/* FINISH */ { false,   true,    true,      false,     false,    true,    false,    true  },
	/*  FAIL  */ { false,   true,    true,      false,     false,    false,   true,     true  },
	/*COMPLETE*/ { true,    false,   false,     false,     false,    false,   false,    true  },
	};
}
