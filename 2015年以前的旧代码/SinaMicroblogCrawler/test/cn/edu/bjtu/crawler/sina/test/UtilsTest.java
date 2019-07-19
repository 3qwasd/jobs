/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sina.test;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.junit.Test;

import cn.edu.bjtu.crawler.utils.IOUtils;
import cn.edu.bjtu.crawler.utils.PropertiesUtils;
import cn.edu.bjtu.crawler.utils.sina.SinaParamtersUtils;
import cn.edu.bjtu.crawler.utils.sina.SinaPasswordEncoder;

/**
 * @author QiaoJian
 *
 */
public class UtilsTest {
	
	@Test
	public void urlEndcodTest(){
		String url = "http://s.weibo.com/wb/";
		String searchStr = "【请用微博一起祈祷！】我们知道，航班失联";
		try {
			searchStr = URLEncoder.encode(searchStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(searchStr);
	}
	
	@Test
	public void transStr() throws Exception{
		String src = "\u4e2d\u65b9\u6392\u9664154\u540d\u4e2d\u56fd\u4e58\u5ba2\u6d89\u6050\u6016\u88ad\u2026";
		String target = new String(src.getBytes("UTF-8"),"UTF-8");
		System.out.println(target);
	}
	
	@Test
	public void testDate(){
		Date date = new Date(Long.valueOf("1395125221"));
		System.out.println(date.toLocaleString());
//		Date currDate = new Date();
//		long time = currDate.getTime();
//		System.out.println(time);
//		System.out.println(System.currentTimeMillis());
//		long newTime = time+1000*60*20;
//		Date date = new Date(newTime);
//		System.out.println(date.toLocaleString());
	}
	@Test
	public void propertiesUtilsTest() throws Exception{
		String value = PropertiesUtils.getProperties("Accept");
		System.out.println(value);
	}
	@Test
	public void IOUtilsTest(){
		System.out.println(IOUtils.getClassPath());
	}
	@Test
	public void PasswordEncoderTest(){
		String nonce="U473VB";
		String servetime="1378804272";
		String password="qj19842012";
		String pubkey="EB2A38568661887FA180BDDB5CABD5F21C7BFD59C090CB2D245A87AC253062882729293E5506350508E7F9AA3BB77F4333231490F915F6D63C55FE2F08A49B353F444AD3993CACC02DB784ABBB8E42A9B1BBFFFB38BE18D78E87A0E41B9B8F73A928EE0CCEE1F6739884B9777E4FE9E88A1BBE495927AC4A799B3181D6442443";
		String message =servetime+"\t"+nonce+"\n"+password;
		String pwd = SinaPasswordEncoder.rsaCrypt(pubkey, "10001", message);
		String pwdCode = "5415f4068cd83b5a74216284ef74387ff1b354ef9955958a885823841e8acda8e79dfc732aaa00eeb1e028c0fed3ab606072372d17d0daf0e46d4562bc8f0b87a91fa8e949db1815ac48cdd5e7ac0ea87bf8b1132ce44a8f88fa2598f860b35d850665eac8c97c6cce9c885ac96352c826ee39cb5ef698bce2c6bef8e51a98d1";
		System.out.println(pwd);
		System.out.println(pwd.length());
		System.out.println(pwdCode.length());
		if(pwd.equals(pwdCode))
			System.out.println("xxxxxxxxxxxx");
	}
	@Test
	public void usernameEncoderTest(){
		System.out.println(SinaParamtersUtils.getInstance().secretEncodingUserName(PropertiesUtils.getProperties("username")));
	}
}
