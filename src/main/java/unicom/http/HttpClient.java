    package unicom.http;  
      
    import java.io.File;  
    import java.io.FileInputStream;  
    import java.io.IOException;  
    import java.io.UnsupportedEncodingException;  
    import java.security.KeyManagementException;  
    import java.security.KeyStore;  
    import java.security.KeyStoreException;  
    import java.security.NoSuchAlgorithmException;  
    import java.security.cert.CertificateException;  
import java.text.SimpleDateFormat;
    import java.util.ArrayList;  
import java.util.Date;
    import java.util.List;  
import java.util.Locale;
      
    import javax.net.ssl.SSLContext;  
      
    import org.apache.http.HttpEntity;  
    import org.apache.http.NameValuePair;  
    import org.apache.http.ParseException;  
    import org.apache.http.client.ClientProtocolException;  
    import org.apache.http.client.entity.UrlEncodedFormEntity;  
    import org.apache.http.client.methods.CloseableHttpResponse;  
    import org.apache.http.client.methods.HttpGet;  
    import org.apache.http.client.methods.HttpPost;  
    import org.apache.http.conn.ssl.SSLConnectionSocketFactory;  
    import org.apache.http.conn.ssl.SSLContexts;  
    import org.apache.http.conn.ssl.TrustSelfSignedStrategy;  
    import org.apache.http.entity.ContentType;  
    import org.apache.http.entity.mime.MultipartEntityBuilder;  
    import org.apache.http.entity.mime.content.FileBody;  
    import org.apache.http.entity.mime.content.StringBody;  
    import org.apache.http.impl.client.CloseableHttpClient;  
    import org.apache.http.impl.client.HttpClients;  
    import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;  

import urlencode.UrlTools;
    
    /*
     * httpclient封装 好用 ，使用 httpclient get方法访问微信
     * 
     * 
     * **/  
    public class HttpClient {  
    	//String postUrl="http://localhost:8200/Mobile/home/post.do";
    	String postUrl="http://61.148.212.67:7406/Mobile/home/post.do";
    	CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpPost httppost = new HttpPost(postUrl);  
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mmss",Locale.CHINESE);
        /** 
         * 发�? get请求 
         */  
        public void get(String message) {  
           
            try {  
                // 创建httpget.    
            	Date  da=new Date();
        	  	String st=df.format(da).toString();
                HttpGet httpget = new HttpGet(message);  
                System.out.println("time "+st+" executing request " + httpget.getURI());  
                // 执行get请求.    
                CloseableHttpResponse response = httpclient.execute(httpget);  
                try {  
                    // 获取响应实体    
                    HttpEntity entity = response.getEntity();  
                   // System.out.println("--------------------------------------");  
                    // 打印响应状�?    
                    System.out.println(response.getStatusLine());  
                    if (entity != null) {  
                        // 打印响应内容长度    
                        System.out.println("Response content length: " + entity.getContentLength());  
                        // 打印响应内容    
                        System.out.println("Response content: " + EntityUtils.toString(entity));  
                    }  
                    System.out.println("------------------------------------");  
                } finally {  
                    response.close();  
                }  
            } catch (ClientProtocolException e) {  
                e.printStackTrace();  
            } catch (ParseException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            } finally {  
                // 关闭连接,释放资源    
                try {  
                    httpclient.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
               
        }  
       
      
     
        /** 
         * 发�? post请求访问本地应用并根据传递参数不同返回不同结�?
         */  
        public void post(String sendMessage) {  
            // 创建默认的httpClient实例.    
          
            // 创建参数队列    
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
            formparams.add(new BasicNameValuePair("message", sendMessage));  
            UrlEncodedFormEntity uefEntity;  
            try {  
                uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
                httppost.setEntity(uefEntity);  
                System.out.println("executing request " + httppost.getURI());  
                CloseableHttpResponse response = httpclient.execute(httppost);  
                try {  
                    HttpEntity entity = response.getEntity();  
                    if (entity != null) {  
                        System.out.println("--------------------------------------");  
                        System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
                        System.out.println("--------------------------------------");  
                    }  
                } finally {  
                    response.close();  
                }  
            } catch (ClientProtocolException e) {  
                e.printStackTrace();  
            } catch (UnsupportedEncodingException e1) {  
                e1.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            } finally {  
                // 关闭连接,释放资源    
                try {  
                    httpclient.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
      
       
        /** 
         * post方式提交表单（模拟用户登录请求） 可以封装直接使用
         */  
        public void postForm() {  
            // 创建默认的httpClient实例.    
            CloseableHttpClient httpclient = HttpClients.createDefault();  
            // 创建httppost    
            HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceJ.action");  
            // 创建参数队列    
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
            formparams.add(new BasicNameValuePair("username", "admin"));  
            formparams.add(new BasicNameValuePair("password", "123456"));  
            UrlEncodedFormEntity uefEntity;  
            try {  
                uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
                httppost.setEntity(uefEntity);  
                System.out.println("executing request " + httppost.getURI());  
                CloseableHttpResponse response = httpclient.execute(httppost);  
                try {  
                    HttpEntity entity = response.getEntity();  
                    if (entity != null) {  
                        System.out.println("--------------------------------------");  
                        System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
                        System.out.println("--------------------------------------");  
                    }  
                } finally {  
                    response.close();  
                }  
            } catch (ClientProtocolException e) {  
                e.printStackTrace();  
            } catch (UnsupportedEncodingException e1) {  
                e1.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            } finally {  
                // 关闭连接,释放资源    
                try {  
                    httpclient.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
      
        /** 
         * HttpClient连接SSL 
         */  
      /*  public void ssl() {  
            CloseableHttpClient httpclient = null;  
            try {  
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
                FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore"));  
                try {  
                    // 加载keyStore d:\\tomcat.keystore    
                    trustStore.load(instream, "123456".toCharArray());  
                } catch (CertificateException e) {  
                    e.printStackTrace();  
                } finally {  
                    try {  
                        instream.close();  
                    } catch (Exception ignore) {  
                    }  
                }  
                // 相信自己的CA和所有自签名的证�? 
                SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();  
                // 只允许使用TLSv1协议  
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,  
                        SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);  
                httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();  
                // 创建http请求(get方式)  
                HttpGet httpget = new HttpGet("https://localhost:8443/myDemo/Ajax/serivceJ.action");  
                System.out.println("executing request" + httpget.getRequestLine());  
                CloseableHttpResponse response = httpclient.execute(httpget);  
                try {  
                    HttpEntity entity = response.getEntity();  
                    System.out.println("----------------------------------------");  
                    System.out.println(response.getStatusLine());  
                    if (entity != null) {  
                        System.out.println("Response content length: " + entity.getContentLength());  
                        System.out.println(EntityUtils.toString(entity));  
                        EntityUtils.consume(entity);  
                    }  
                } finally {  
                    response.close();  
                }  
            } catch (ParseException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            } catch (KeyManagementException e) {  
                e.printStackTrace();  
            } catch (NoSuchAlgorithmException e) {  
                e.printStackTrace();  
            } catch (KeyStoreException e) {  
                e.printStackTrace();  
            } finally {  
                if (httpclient != null) {  
                    try {  
                        httpclient.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        }  
      */
        /** 
         * 上传文件 
         */  
        
        public void upload() {  
            CloseableHttpClient httpclient = HttpClients.createDefault();  
            try {  
                HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceFile.action");  
      
                FileBody bin = new FileBody(new File("F:\\image\\sendpix0.jpg"));  
                StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);  
      
                HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin", bin).addPart("comment", comment).build();  
      
                httppost.setEntity(reqEntity);  
      
                System.out.println("executing request " + httppost.getRequestLine());  
                CloseableHttpResponse response = httpclient.execute(httppost);  
                try {  
                    System.out.println("----------------------------------------");  
                    System.out.println(response.getStatusLine());  
                    HttpEntity resEntity = response.getEntity();  
                    if (resEntity != null) {  
                        System.out.println("Response content length: " + resEntity.getContentLength());  
                    }  
                    EntityUtils.consume(resEntity);  
                } finally {  
                    response.close();  
                }  
            } catch (ClientProtocolException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    httpclient.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        
        public static void main(String args[]) { 
        	String message="2016042859226643|00 10|0|18518775040|1114012316040929|00|温馨提示：您的可用额度已不足50.00元，请尽快充值或去营业厅交费，以免影响您的正常使用。用联通手机营业厅，随时随地查流量查话费，充值专享9.85折，点击进入 wap.10010.com |04|20160428094648||||";
//        	String sendMessage="18611701625不足10";
//        	int  i=0;
//        	HttpClient client=new HttpClient();
//        	while(true)
//        	{
//        		
//        		//client.post(sendMessage+"|"+i);
//        		client.get(sendMessage+i);
//        		i++;
//        	}
        	UrlTools urltools=new UrlTools();//计算url类
        	HttpClient client=new HttpClient();
    		//client.post(sendMessage+"|"+i);
			
			//String url=urltools.createUrl(message,"50");
    		String url="http://chong.qq.com/tws/balanceremind/RemindBalance?sign=1ea049cf2e84032a4469e2607af8e9b6&timestamp=1465349738&random=32208&sp=ltjt&format=json&tel=15507586313&status=low_balance&effectetime=2016-06-08%2009:35&balances=50";
        	client.get(url);
        }  
    }