/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.sina.test;

import org.junit.Before;
import org.junit.Test;

import cn.edu.bjtu.crawler.bean.sina.JsonDataBean;
import cn.edu.bjtu.crawler.bean.sina.SinaResponseBean;
import cn.edu.bjtu.crawler.network.sina.SinaHttpLoginManager;
import cn.edu.bjtu.crawler.parser.html.HtmlContentParser;
import cn.edu.bjtu.crawler.parser.sinadata.SinaDataParser;

/**
 * @author QiaoJian
 *
 */
public class SinaDataParserTest {
	
	HtmlContentParser htmlContentParser;
	
	SinaHttpLoginManager sinaHttpConnectioner;
	@Before
	public void init(){
		htmlContentParser = new HtmlContentParser();
		sinaHttpConnectioner = new SinaHttpLoginManager();
	}
	@Test
	public void htmlParserTest(){
		SinaResponseBean contentBean = (SinaResponseBean) sinaHttpConnectioner.loginSinaMicroblog();
		htmlContentParser.parserHtml(contentBean.getResult());
	}
	
	@Test
	public void predataParserTest(){
		SinaDataParser sinaDataParser = new SinaDataParser();
		String result = "sinaSSOController.preloginCallBack("
				+ "{\"retcode\":0,\"servertime\":1378778976,\"pcid\":\"xd-0b083b6a84dbee03a6627585a4a2f38f7413\","
				+ "\"nonce\":\"R9UFNV\",\"pubkey\":\"EB2A38568661887FA180BDDB5CABD5F21C7BFD59C090CB2D245A87AC253062882729293E5506350508E7F9AA3BB77F4333231490F915F6D63C55FE2F08A49B353F444AD3993CACC02DB784ABBB8E42A9B1BBFFFB38BE18D78E87A0E41B9B8F73A928EE0CCEE1F6739884B9777E4FE9E88A1BBE495927AC4A799B3181D6442443\","
				+ "\"rsakv\":\"1330428213\",\"exectime\":1})";
		JsonDataBean preLoginData = new JsonDataBean(result);
		sinaDataParser.parsePreLoginData(preLoginData);
		for(String key:preLoginData.keySet()){
			System.out.println(key+":"+preLoginData.get(key));
		}
	}
	
}
