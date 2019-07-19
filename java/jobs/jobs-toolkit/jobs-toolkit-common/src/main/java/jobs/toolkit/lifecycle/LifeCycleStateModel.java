package jobs.toolkit.lifecycle;

/**
 * 生命周期状态模型, 注意该类不是线程安全的, 线程安全由生命周期组件进行负责
 * @author jobs
 *
 */
class LifeCycleStateModel {
	
	/*状态转移矩阵*/
	private static final boolean stateMatrix[][] = {
	              /*NOTINITED  INITED  STARTED  STOPPED*/
	/*NOTINITED*/   { false,    true,   false,   true }, 
	/* INITED  */   { false,    true,   true,    true },
	/* STARTED */   { false,    false,  true,    true }, 
	/* STOPPED */   { false,    false,  false,   true }
	};
	/*当前生命周期状态模型的状态*/
	private volatile STATE state;
	
	/**
	 * 创建一个指定状态的生命周期状态模型
	 * @param state
	 */
	LifeCycleStateModel(STATE state) {
		this.state = state;
	}
	/**
	 * 创建一个新的生命周期状态模型, 初始化状态为NOTINITED
	 */
	LifeCycleStateModel() {
		this(STATE.NOTINITED);
	}
	/**
	 * 判断当前状态是否是参数给定的状态
	 * @param proposed
	 * @return
	 */
	boolean isInState(STATE proposed){
		return this.state == proposed;
	}
	/**
	 * 确保目前的状态是否是参数proposed表示的状态,如果不是则会抛出运行时异常
	 * 参数proposed给出的状态表示该服务目前应当处于的正确状态,如果不是则表示
	 * 发生了错误
	 * @param proposed
	 */
	void ensureCurrentState(STATE proposed){
		if(!isInState(proposed)){
			throw new LifeCycleStateException(String.format("Current state must be [%1$s] instead of [%2$s]", proposed, this.state));
		}
	}
	/**
	 * 状态转移, 从当前的状态转移至proposed表示的状态, 如果状态转移不合法, 则抛出运行时异常
	 * 返回旧状态
	 * @param proposed
	 * @return 
	 */
	STATE stateTransfer(STATE proposed){
		checkStateTransfer(this.state, proposed);
		STATE old = this.state;
		this.state = proposed;
		return old;
	}
	
	STATE getState() {
		return this.state;
	}

	/**
	 * 生命周期状态类, 共四种状态
	 * @author jobs
	 *
	 */
	enum STATE{
		NOTINITED(0, "NOTINITED"),/*未初始化*/

		INITED   (1, "INITED"),      /*初始化*/

		STARTED  (2, "STARTED"),    /*启动*/

		STOPPED  (3, "STOPPED");    /*停止*/

		private final int value;

		private final String statename;

		private STATE(int value, String name) {
			this.value = value;
			this.statename = name;
		}

		public int getValue() {
			return value;
		}

		@Override
		public String toString() {
			return statename;
		}
	}
	/**
	 * 通过value获取state枚举值, 该方法用于junit测试
	 * @return
	 */
	static STATE getStateByValue(int value){
		STATE[] states = {STATE.NOTINITED, STATE.INITED, STATE.STARTED, STATE.STOPPED};
		return states[value];
	}
	
	/**
	 * 判断从from到to的状态转换是否合法，如果不合法则抛出运行时异常
	 * @param from
	 * @param to
	 */
	private static void checkStateTransfer(STATE from, STATE to){
		if(!canTransfer(from, to)){
			throw new LifeCycleStateException(String.format("Life cycle can not transfer to state [%1$s] from state [%2$s]", to, from));
		}
	}
	/**
	 * 判断从状态from到to能否转移
	 * @param from
	 * @param to
	 * @return
	 */
	private static boolean canTransfer(STATE from, STATE to){
		return stateMatrix[from.getValue()][to.getValue()];
	}
}
