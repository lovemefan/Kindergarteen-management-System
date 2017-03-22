package sms;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import windows.Window;
public class Sender {
	public static void sendSms(String number,String text) throws Exception{
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn"); 
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
		NameValuePair[] data ={ new NameValuePair("Uid", "南航软件2"),
				new NameValuePair("Key", "b5190d608ccaeed72c89"),
				new NameValuePair("smsMob",number),
				new NameValuePair("smsText",text)};
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:"+statusCode);
		for(Header h : headers)
		{
		System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("gbk")); 
		System.out.println(result);
		switch(Integer.parseInt(result)){
		case -1:windows.Window.messageWindow("", "没有该用户账户");break;
		case -2:windows.Window.messageWindow("", "接口密钥不正确\n不是账户登陆密码");break;
		case -21:windows.Window.messageWindow("", "MD5接口密钥加密不正确");break;
		case -3:windows.Window.messageWindow("", "短信数量不足");break;
		case -11:windows.Window.messageWindow("", "该用户被禁用");break;
		case -14:windows.Window.messageWindow("", "短信内容出现非法字符");;break;
		case -4:windows.Window.messageWindow("", "手机格式不正确");break;
		case -41:windows.Window.messageWindow("", "手机号码为空");break;
		case -42:windows.Window.messageWindow("", "短信内容为空");break;
		case -51:windows.Window.messageWindow("", "短信签名格式不正确\n接口签名格式为：签名内容");;break;
		case -6:windows.Window.messageWindow("", "ip限制");break;
		default :windows.Window.messageWindow("", "已发送成功"+Integer.parseInt(result)+"条短信");
		
		}
	

		post.releaseConnection();

	}
	
}
