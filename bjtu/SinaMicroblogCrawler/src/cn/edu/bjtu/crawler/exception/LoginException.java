/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.exception;

/**
 * @author QiaoJian
 *
 */
public class LoginException extends Exception {
	
	/*登陆异常类型 P是预登陆异常，C代表登陆异常，*/
	String type;
	
	String message;
	
	public LoginException(Exception e,String type, String message) {
		super(e);
		this.type = type;
		this.message = message;
	}
	public LoginException(String type, String message) {
		super();
		this.type = type;
		this.message = message;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public void showMessage(){
		System.out.println(message);
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
