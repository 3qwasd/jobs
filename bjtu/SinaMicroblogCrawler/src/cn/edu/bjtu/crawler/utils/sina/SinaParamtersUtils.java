/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.utils.sina;


import cn.edu.bjtu.crawler.utils.Base64Encoder;
import cn.edu.bjtu.crawler.utils.ParametersSecretEncoding;
import cn.edu.bjtu.crawler.utils.ParametersUtils;

/**
 * @author QiaoJian
 *
 */
public class SinaParamtersUtils extends ParametersUtils implements ParametersSecretEncoding {
	
	private static SinaParamtersUtils instance;
	
	private static String defaultCharSet = "UTF-8";
	
	private SinaParamtersUtils(){};
	
	public static SinaParamtersUtils getInstance(){
		if(instance == null){
			instance = new SinaParamtersUtils();
		}
		return instance;
	}
	
	public static void setCharSet(String charSet){
		defaultCharSet = charSet;
	}
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.crawler.utils.ParametersSecretEncoding#secretEncodingUserName(java.lang.String)
	 */
	@Override
	public String secretEncodingUserName(String userName) {
		// TODO Auto-generated method stub
		String secretStr = Base64Encoder.encode(this.encodingParameter(userName, defaultCharSet).getBytes());
		return secretStr;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.crawler.utils.ParametersSecretEncoding#secretEncodingParameters(java.lang.String)
	 */
	@Override
	public String secretEncodingParameters(String paramStr) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.crawler.utils.ParametersSecretEncoding#secretEncodingPassword(java.lang.String)
	 */
	@Override
	public String secretEncodingPassword(String password) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.crawler.utils.ParametersSecretEncoding#secretEncodingUrl(java.lang.String)
	 */
	@Override
	public String secretEncodingUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
