package jobs.toolkit.service;

/**
 * 服务接口
 * @author jobs
 *
 */
public interface Service{
	
	/**
	 * 服务状态，服务只有两种状态，可用和不可用
	 * @author jobs
	 *
	 */
	public enum Status{
		INVALID(0, "invalid"), VALID(1, "valid");
		private int code;
		private String name;
		private Status(int code, String name){
			this.code = code;
			this.name = name;
		}
		public int code(){
			return this.code;
		}
		@Override
		public String toString() {
			return this.name;
		}
		/**
		 * 根据code返回枚举值, 如果code==0 返回INVALID, 如果非0 则返回VALID
		 * @param code
		 * @return
		 */
		public static Status valueOf(int code){
			if(code == 0) return INVALID;
			else return VALID;
		}
	}
	
	/**
	 * 同步等待服务请求
	 */
	public void syncWait() throws Exception;
	/**
	 * 判断服务是否可用
	 * @return
	 */
	public boolean isValid();
	
	/**
	 * 服务状态转为可用
	 * @throws Exception 
	 */
	void valid() throws Exception;
	/**
	 * 服务状态转为不可用
	 * @throws Exception 
	 */
	void inValid() throws Exception;
}
