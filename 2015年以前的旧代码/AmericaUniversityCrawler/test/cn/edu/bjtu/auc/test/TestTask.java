/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import cn.edu.bjtu.auc.HttpManager;
import cn.edu.bjtu.auc.task.AlabamaTask;
import cn.edu.bjtu.auc.task.FiveZeroSevenTask;
import cn.edu.bjtu.auc.task.service.UCIrvineService;
import cn.edu.bjtu.auc.ui.utils.ResourceManager;
import cn.edu.bjtu.joe.system.thread.JoeThreadService;

/**
 * @author QiaoJian
 *
 */
public class TestTask {
	
	public void testSack(){
		
	}
	
	@Test
	public void testJSON(){
		String regx = "12312312312312313{发热}";
		//String regx = "(d){0,1}(i){0,1}(g){0,1}(i){0,1}(t){0,1}";
//		String regx1 = "(digi(t){0,1})|(dig(i){0,1})|(di(g){0,1})|(d(i){0,1})";
//		String regx2 = "(igi(t){0,1})|(ig(i){0,1})|(i(g){0,1})";
//		String regx3 = "(gi(t){0,1})|(g(i){0,1})";
//		String regx4 = "i(t){0,1}";
//		String regx5 = "t";
//		String regx = "("+regx1+")|"+"("+regx2+")|"+"("+regx3+")|"+"("+regx4+")|"+"("+regx5+")";
		String content = "git";
		System.out.println(regx.indexOf("{发热}"));
//		System.out.println(content.matches(regx));;
	}
	
	@Test
	public void testRegx(){
		String regx = "var_sj_[0-9]+\\=\\{([0-9a-zA-Z]|\\:|\"|\\@|\\.|\\+|\\-|\\&|\\,)+\\}";
		String content = "var _sj_123330568 = {affiliation:\"\", email:\"rkw27@nau.edu\", fax:\"\", internalPhone:\"\", lastName:\"Wang\", name:\"Rachel Kathleen\", orgUnit:\"\", personalTitle:\"\", phone:\"\", postalAddress:\"\", secondaryPhone:\"\", title:\"\", webPage:\"\"}";
		String str = content.replace(" ", "");
		System.out.println(str);
		System.out.println(str.replace(" ", "").matches(regx));
	}
	@Test
	public void testData(){
		Date date = new Date(1392275411637L);
		System.out.println(date);
	}
	
	@Test
	public void testCeras() throws Exception{
		//获得httpclient对象
	      HttpClient httpclient = new DefaultHttpClient();
	      //获得密匙库
	      KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
	      FileInputStream instream = new FileInputStream(new File("I:\\cers\\GeorgiaTech.keystore"));
	      //密匙库的密码
	      trustStore.load(instream, "123456".toCharArray());
	      //注册密匙库
	      SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
	      //不校验域名
	      socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	      Scheme sch = new Scheme("https", 443, socketFactory);
	      httpclient.getConnectionManager().getSchemeRegistry().register(sch);
	      //获得HttpGet对象
	      HttpGet httpGet = null;
	      httpGet = new HttpGet("https://www.directory.gatech.edu/directory/results//wang");
	      //发送请求
	      HttpResponse response = httpclient.execute(httpGet);
	      //输出返回值
	      InputStream is = response.getEntity().getContent();
	      BufferedReader br = new BufferedReader(new InputStreamReader(is));
	      String line = "";
	      while((line = br.readLine())!=null){
	          System.out.println(line);
	      }
	}
	
	@Test
	public void testGetmailssl() throws Exception{
		SSLContext ctx = SSLContext.getInstance("TLS");
		SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());
	    X509TrustManager tm = new X509TrustManager() {
	        public void checkClientTrusted(X509Certificate[] xcs,
	                String string) {
	        }
	 
	        public void checkServerTrusted(X509Certificate[] xcs,
	                String string) {
	        }
	 
	        public X509Certificate[] getAcceptedIssuers() {
	            return null;
	        }
	    };
	    ctx.init(null, new TrustManager[] { tm }, null);
	    SSLSocketFactory ssf = new SSLSocketFactory(ctx);
	 
	    SchemeRegistry schemeRegistry = new SchemeRegistry();
	    schemeRegistry.register(
	             new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
	    schemeRegistry.register(
	             new Scheme("https", 443, ssf));
	 
	    PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
	     
	    cm.setMaxTotal(200);
	 
	    cm.setDefaultMaxPerRoute(20);
	 
	    BasicHttpParams params = new BasicHttpParams();
	 
	    params.setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true);
	 
	    params.setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 64 * 1024);
	 
	    DefaultHttpClient client = new DefaultHttpClient(cm, params);
	    
	    HttpPost post = new HttpPost("https://172.16.1.2/cgi/maincgi.cgi?Url=Index");
	    List<NameValuePair> nvPairs = null;
	    Map<String,String> postParams = new HashMap<String,String>();
	    postParams.put("passwd", "talent");
		postParams.put("username", "superman");
		if(params!=null&&postParams.size()>0){
			nvPairs = new ArrayList<NameValuePair>();
			for(String key:postParams.keySet()){
				BasicNameValuePair nameValuePair = new BasicNameValuePair(key, postParams.get(key));
				nvPairs.add(nameValuePair);
			}
		}
		post.setEntity(new UrlEncodedFormEntity(nvPairs,HTTP.UTF_8));
		
		post.setHeader("Host", "172.16.1.2");
	    HttpGet get = new HttpGet("https://test.api.quadapp.com/");
	     
	    try {
	        HttpResponse response = client.execute(post);
	        int status = response.getStatusLine().getStatusCode();
	        if (status == HttpStatus.SC_OK) {
	            HttpEntity entity = response.getEntity();
	            String body = EntityUtils.toString(entity);
	            System.out.println(body);
	        }
	        get.releaseConnection();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	@Test
	public void decodeTest(){
		String tpyrcne = "#657;429&0183";
		String fixed = "&#;0987654321";	
		String decoded = ""; 
		String str = "5&6095&34295&34;95&6695&34;95&6095&37795&#895&33095&6695&34;95&8#95&34395&34495&3309";
		for (int i=0; i<str.length(); i++) {
			decoded += tpyrcne.charAt(fixed.indexOf(str.charAt(i)));
		}
		Pattern pattern = Pattern.compile("(?<s>)[0-9]+");
		Matcher matcher = pattern.matcher(decoded);
		String email = "";
		System.out.println(decoded);
		while (matcher.find()) {
			String num = matcher.group(0);
			System.out.println(num);
			int acc = Integer.valueOf(num);
			char c = (char) acc;
			email+=c;
		}
		System.out.println(email);
	}
	
	@Test
	public void testPath(){
		String path = this.getClass().getClassLoader().getResource(".").getPath();
		path = path.substring(0,path.indexOf("bin"));
		System.out.println(path);
	}
	@Test
	public void testResx(){
		String regx = "[a-zA-Z]+\\u0020wang";
		System.out.println("Dan tong Wang".toLowerCase().matches(regx));
	}
	@Test
	public void testRestFetch(){
		String html = "<td class=\"toCenter\"><script language=javascript><!--"+
				"document.write(\"<a href=\" + \"mail\" + \"to:\" + \"andrew.wang\" + \"@\" + \"utah.edu\" + \">\" + "+
				"<img src='/uWho/uWho-static/images/spotImages/email.png' width='21' height='14' alt='' />\" +"+
				"</a>\");//--></script></td>";
//		aStr = aStr.replace("\"", "").replace("+", "").replace("mailto:", "").replace(" ", "");
//		System.out.println(aStr.trim());
//		//Pattern pattern = Pattern.compile("(?<s>)<a[\\x00-\\xff]*</a>");
		Pattern pattern = Pattern.compile("(?<s>)[a-zA-z.]+@[a-zA-z.]+");
		Matcher matcher = pattern.matcher(html.replace("\"", "").replace("+", "").replace(" ", ""));
		String aStr = "";
		if(matcher.find()){
			aStr = matcher.group(0);
		}
		System.out.println(aStr);
	}
	
	@Test
	public void matchTest(){
		String name = "Li, Stephen";
		String lastName = "Li";
		String regx = ""+lastName.toLowerCase()+",[\\x00-\\xff]*";
		System.out.println(name.toLowerCase());
		System.out.println(regx);
		System.out.println(name.toLowerCase().matches(regx));
	}
	@Test
	public void testTasks() throws Exception{
		JoeThreadService joeThreadService = JoeThreadService.getThreadService();
		HttpManager httpManager = HttpManager.getInstance();
		httpManager.createHttpConnection();

		FiveZeroSevenTask alabamaTask = new FiveZeroSevenTask("alabama");
		alabamaTask.run();
	}
	@Test
	public void newLastName() throws Exception{
		File file = new File("I:\\lastname.txt");
		File newDic = new File("I:\\newLastName.dic");
		if(!newDic.exists()){

			newDic.createNewFile();

		}
		List<String> sts = new ArrayList<String>();
		FileOutputStream outputStream = new FileOutputStream(newDic,true);
		RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
		String text = null;
		text = randomAccessFile.readLine();
		while ((text = randomAccessFile.readLine())!=null) {
			/*RandomAccessFile以ISO-8859-1编码读取文件，需转成UTF-8*/
			text = text.trim();
			if(sts.contains(text)){
				continue;
			}else{
				sts.add(text);
				outputStream.write((text+"\n").getBytes());
			}
		}
		randomAccessFile.close();
		outputStream.close();
	} 
	@Test
	public void resourceTest(){
		ResourceManager manager = ResourceManager.getInstance();
		System.out.println(manager.getClassPath());
		System.out.println(manager.getUniversitiyTaskName("University_Of_Alabama"));
		System.out.println(manager.getLastNames());
	}
}
