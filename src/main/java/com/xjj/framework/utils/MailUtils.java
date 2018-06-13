package com.xjj.framework.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xjj.framework.configuration.ConfigUtils;


public class MailUtils {
	
	protected final static Log logger = LogFactory.getLog(MailUtils.class);

	protected static final String DEFAULT_MAIL_PORT ="25";//默认端口号
	protected static final String DEFAULT_MAIL_SERVER = "";//默认邮件服务器
	protected static final String DEFAULT_MAIL_FROM = "";//默认发送者
	protected static final String DEFAULT_MAIL_USER = "";//默认发送者地址
	protected static final String DEFAULT_MAIL_PASSWORD = "";//默认密码
	protected static final long DEFAULT_MAIL_INTERVAL = 1000;//默认两次发送间隔的毫秒数
	protected static final int DEFAULT_MAIL_MAX_SEND_COUNT = 3;//如果发送出现错误，最大的重试次数
	
	private static final MailUtils instance = new MailUtils();
	
    private BlockingQueue<MailMessage> mailMessages = new LinkedBlockingQueue<MailMessage>();
    private BlockingQueue<MailMessage> mailInstantMessages = new LinkedBlockingQueue<MailMessage>();
    
    private MailUtils(){
    	new SendEmailThread(this,mailMessages).start();
    	new SendEmailThread(this,mailInstantMessages).start();
    }
    
    /**
     * 返回唯一实例
     * @return
     */
    public static final MailUtils getInstance(){
    	return instance;
    }
    
    /**
     * 发送邮件
     * @param to 发送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     * @param instant 重要邮件，立即发送
     */
    public static boolean sendMail(String to, String subject,String body){
    	return sendHtmlMail(to, null, null, subject,body, false);
    }
    /**
     * 发送文本邮件
     * @param to 发送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     * @param instant 重要邮件，立即发送
     */
    public static boolean sendTextMail(String to, String subject,String body){
    	return sendTextMail(to, null, null, subject,body, false);
    }
    /**
     * 发送邮件
     * @param to 发送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     */
    public static boolean sendMail(String to, String subject,String body, boolean instant){
    	return sendHtmlMail(to, null, null, subject,body, instant);
    }
    /**
     * 发送文本邮件
     * @param to 发送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     */
    public static boolean sendTestMail(String to, String subject,String body, boolean instant){
    	return sendTextMail(to, null, null, subject,body, instant);
    }
    /**
     * 发送邮件
     * @param to 发送地址
     * @param cc 抄送地址
     * @param bcc 暗送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     */
    public static boolean sendMail(String to, String cc, String bcc, String subject,String body){
    	return sendHtmlMail(to, cc, bcc, subject, body, false);
    }
    /**
     * 发送文本邮件
     * @param to 发送地址
     * @param cc 抄送地址
     * @param bcc 暗送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     */
    public static boolean sendTextMail(String to, String cc, String bcc, String subject,String body){
    	return sendTextMail(to, cc, bcc, subject, body, false);
    }
    /**
     * 发送邮件
     * @param to 发送地址
     * @param cc 抄送地址
     * @param bcc 暗送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     * @param instant 重要邮件，立即发送
     */
    public static boolean sendTextMail(String to, String cc, String bcc, String subject,String body, boolean instant){
    	if(to == null || to.equals("") || subject == null || subject.equals("") || body == null || body.equals("")){
    		if(logger.isDebugEnabled()){
    			logger.debug("添加邮件失败，接收地址、主题、内容不能为空!");
    		}
    		return false;
    	}
    	return sendMail(instance.new TextMailMessage(to, cc, bcc, subject,body), instant);
    }
    
    /**
     * 发送邮件
     * @param to 发送地址
     * @param cc 抄送地址
     * @param bcc 暗送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     * @param instant 重要邮件，立即发送
     */
    public static boolean sendHtmlMail(String to, String cc, String bcc, String subject,String body, boolean instant){
    	if(to == null || to.equals("") || subject == null || subject.equals("") || body == null || body.equals("")){
    		if(logger.isDebugEnabled()){
    			logger.debug("添加邮件失败，接收地址、主题、内容不能为空!");
    		}
    		return false;
    	}
    	return sendMail(instance.new HTMLMailMessage(to, cc, bcc, subject,body), instant);
    }
    
    /**
     * 发送邮件
     * @param message 邮件内容对象
     */
    public static boolean sendMail(MailMessage message, boolean instant){
    	try {
	    	if(instant){
				instance.mailInstantMessages.put(message);
	    	}else{
	    		instance.mailMessages.put(message);
	    	}
    	} catch (InterruptedException e) {
			e.printStackTrace();
			if(logger.isDebugEnabled()){
		   		logger.debug("添加邮件失败，发送线程已经停止！");
		   	}
			return false;
		}
    	if(logger.isDebugEnabled()){
    		logger.debug("添加邮件成功");
    	}
    	return true;
    }
    
    /**
     * 发送邮件
     * @param messages 邮件内容对象列表
     */
    public int sendMail(Collection<MailMessage> messages){
    	int count = 0;
    	for(MailMessage msg : messages){
    		if(sendMail(msg,false)){
    			count++;
    		}
    	}
    	if(logger.isDebugEnabled()){
    		logger.debug("成功添加"+count+"条邮件");
    	}
    	return count;
    }
    
    /**
     * 创建文本邮件内容
     * @return
     */
    public static TextMailMessage newTextMail(){
    	return instance.new TextMailMessage();
    }
    /**
     * 创建网页格式邮件内容
     * @return
     */
    public static HTMLMailMessage newHtmlMail(){
    	return instance.new HTMLMailMessage();
    }
    
    /**
     * 得到所有的未发送邮件内容，可以供给页面查看
     * @return 未发送邮件内容
     */
    public Collection<MailMessage> getAllMessages(){
    	return mailMessages;
    }
    
    /**
     * 得到所有需要立即发送的未发送邮件内容，可以供给页面查看
     * @return 未发送邮件内容
     */
    public Collection<MailMessage> getAllInstantMessages(){
    	return mailInstantMessages;
    }
    
    /**
     * 判断是否还有未发送邮件
     * @return 是否还有未发送邮件
     */
    public boolean hasMessages(){
    	return !mailMessages.isEmpty() && !mailInstantMessages.isEmpty();
    }
    
    public static void main(String[] args) {
		if(logger.isDebugEnabled()){
			logger.debug("开始发送");
		}
		HTMLMailMessage m = MailUtils.newHtmlMail();
		m.setTo("jlsdzhj@126.com");
		m.setSubject("测试中文附件");
		m.setBody("测试中文附件");
		m.addAttach("f:/测试.txt");
		MailUtils.sendMail(m,true);
	}
    
    /**
     * **************************************************************************************
	 * 内部线程类，负责发送邮件
	 * @author 曹新龙
	 */
	private class SendEmailThread extends Thread {
		
		private MailUtils mailUtils;//对工具类的引用
		private boolean isrun = true;//是否正在运行
		
		/////////////////////////////////////////////////////
		//邮件的信息
		String port = DEFAULT_MAIL_PORT;
	    String server = DEFAULT_MAIL_SERVER;//邮件服务器
	    String from = DEFAULT_MAIL_FROM;//发送者
	    String user = DEFAULT_MAIL_USER;//发送者地址
	    String password =  DEFAULT_MAIL_PASSWORD;//密码
	    long interval = DEFAULT_MAIL_INTERVAL;//两次发送间隔的毫秒数
		int maxRetryCount = DEFAULT_MAIL_MAX_SEND_COUNT;//如果发送出现错误，最大的重试次数
		
		BlockingQueue<MailMessage> messages;
		
		public SendEmailThread(MailUtils mailUtils,BlockingQueue<MailMessage> mailMessages){
			this.mailUtils = mailUtils;
			this.messages = mailMessages;
			if(logger.isDebugEnabled()){
				logger.debug("邮件发送线程初始化完成。 MailUtils is " + mailUtils);
				logger.debug("                       mailMessages is " + mailMessages);
			}
		}
		
		@SuppressWarnings("unused")
		public void stopSend(){
			this.isrun = false;
		}
		
		/**
		 * 发送邮件
		 */
		public void run() {
			MailMessage message;
			
			while(mailUtils!=null && isrun){
				
				if(logger.isDebugEnabled()){
					logger.debug("线程启动，开始执行");
				}
				
				port = ConfigUtils.get("mail.MAIL_PORT", DEFAULT_MAIL_PORT);
			    server = ConfigUtils.get("mail.MAIL_SERVER", DEFAULT_MAIL_SERVER);//邮件服务器
			    from = ConfigUtils.get("mail.MAIL_FROM", DEFAULT_MAIL_FROM);//发送者
			    user = ConfigUtils.get("mail.MAIL_USER", DEFAULT_MAIL_USER);//发送者地址
			    password = ConfigUtils.get("mail.MAIL_PASSWORD", DEFAULT_MAIL_PASSWORD);//密码
			    interval = ConfigUtils.getLong("mail.MAIL_INTERVAL", DEFAULT_MAIL_INTERVAL);//两次发送间隔的毫秒数
				maxRetryCount = ConfigUtils.getInt("mail.MAIL_MAX_SEND_COUNT", DEFAULT_MAIL_MAX_SEND_COUNT);//如果发送出现错误，最大的重试次数
			    
				//得到一条需要立即发送的邮件发送，取出以后将从待发送列表中删除，注意次放回如果没有内容，会发生阻塞
			    try {
					message = messages.take();
				} catch (InterruptedException e1) {
					message = null;
					if(logger.isDebugEnabled()){
						logger.debug("读取邮件失败！");
					}
				}
			    if(message != null){
			    	if(sendMail(message)){
			    		if(logger.isDebugEnabled()){
							logger.debug("发送完成["+message.toString()+"]，休息"+ (1.0*interval/1000)+"秒继续执行");
						}
			    	}
			    	try {
						Thread.sleep(interval);
					} catch (InterruptedException e) {
						e.printStackTrace();
						if(logger.isDebugEnabled()){
							logger.debug("线程睡眠失败！");
						}
					}
			    }
			}
		}
		
		
		private boolean sendMail(MailMessage message){
			String msgSuject="";
			try {
				Properties props = new Properties();
	            props.put("mail.smtp.host", server);
	            props.put("mail.smtp.port", port);
	            props.put("mail.smtp.auth", "true");
	            //props.put( "mail.smtp.quitwait ", "false"); 
	            Transport transport = null;
	            //Session session = Session.getDefaultInstance(props, null);
	            Session session = Session.getInstance(props, null);
	            transport = session.getTransport("smtp");
	            transport.connect(server, user, password);
				MimeMessage msg = message.getMimeMessage(session, from);
	            transport.sendMessage(msg, msg.getAllRecipients());
	            
	            msgSuject=msg.getSubject();
	            if(logger.isDebugEnabled()){
					logger.debug("邮件发送成功"+msgSuject);
				}
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	            if(logger.isDebugEnabled()){
					logger.debug("邮件发送失败！"+msgSuject);
				}
	            message.addSendCount();
	            if(!message.isMaxSendCount(maxRetryCount)){
	            	if(logger.isDebugEnabled()){
						logger.debug("再次尝试发送！"+msgSuject);
					}
	            	try {
						messages.put(message);
					} catch (InterruptedException e1) {
						if(logger.isDebugEnabled()){
							logger.debug("失败邮件加入列表失败！");
						}
					}
	            }else{
	            	if(logger.isDebugEnabled()){
						logger.debug("已经达到最大次数，不再发送！"+msgSuject);
					}
	            }
	        }
	        return false;
		}
		
	}
	
	
    
	/**
     * 内部接口，邮件内容
     * @author xjj
     */
    public interface MailMessage{
    	public void addSendCount();
        public boolean isMaxSendCount(int maxRetryCount);
    	public MimeMessage getMimeMessage(Session session,String from) throws MessagingException, UnsupportedEncodingException;
    }
    /**
     * 内部类，实现了邮件内容的公共方法，包括地址和发送次数
     * @author xjj
     */
    public abstract class MailMessageSupport implements MailMessage{
    	private List<String> toList; 
    	private List<String> ccList;
    	private List<String> bccList;
    	int sendCount = 0;
    	
    	/**
		 * 返回邮件的发送地址，支持多个，用空格或者逗号或者分号隔开
		 * @return
		 */
		public InternetAddress[] getToAddress(){
			if(!hasTo()){
				return null;
			}
			InternetAddress[] address = new InternetAddress[toList.size()];
			String a;
			for(int i=0;i<toList.size();i++){
				a = toList.get(i);
				try {
					address[i] = new InternetAddress(a);
				} catch (AddressException e) {
					logger.error(e);
				}
			}
            return address;
		}
		/**
		 * 返回邮件的抄送地址，支持多个，用空格或者逗号或者分号隔开
		 * @return
		 */
		public InternetAddress[] getCCAddress(){
			if(!hasCc()){
				return null;
			}
			InternetAddress[] address = new InternetAddress[ccList.size()];
			String a;
			for(int i=0;i<ccList.size();i++){
				a = ccList.get(i);
				try {
					address[i] = new InternetAddress(a);
				} catch (AddressException e) {
					logger.error(e);
				}
			}
            return address;
		}
		/**
		 * 返回邮件的暗送地址，支持多个，用空格或者逗号或者分号隔开
		 * @return
		 */
		public InternetAddress[] getBCCAddress(){
			if(!hasBcc()){
				return null;
			}
			InternetAddress[] address = new InternetAddress[bccList.size()];
			String a;
			for(int i=0;i<bccList.size();i++){
				a = bccList.get(i);
				try {
					address[i] = new InternetAddress(a);
				} catch (AddressException e) {
					logger.error(e);
				}
			}
            return address;
		}

		/**
		 * @return 发送对象email地址
		 */
		public String getTo() {
			if(!hasTo()){
				return null;
			}
			StringBuffer to = new StringBuffer();
			for(String a : toList){
				to.append(a).append(" ");
			}
			return to.toString();
		}
		
		/**
		 * @param 发送对象email地址
		 */
		public void setTo(String to) {
			String[] addressList = splitMailAddress(to);
			if(addressList==null || addressList.length==0){
				return;
			}
			//判断集合是否为空
			if(toList == null){
				toList = new ArrayList<String>();
			}
			//将拆分的地址加入集合中
			for(String a : addressList){
				toList.add(a);
			}
		}
		/**
		 * @return the 抄送对象email地址
		 */
		public String getCc() {
			if(!hasCc()){
				return null;
			}
			StringBuffer cc = new StringBuffer();
			for(String a : ccList){
				cc.append(a).append(" ");
			}
			return cc.toString();
		}
		/**
		 * @param 抄送对象email地址
		 */
		public void setCc(String cc) {
			String[] addressList = splitMailAddress(cc);
			if(addressList==null || addressList.length==0){
				return;
			}
			//判断集合是否为空
			if(ccList == null){
				ccList = new ArrayList<String>();
			}
			//将拆分的地址加入集合中
			for(String a : addressList){
				ccList.add(a);
			}
		}
		/**
		 * @return 暗送对象email地址
		 */
		
		public String getBcc() {
			if(!hasBcc()){
				return null;
			}
			StringBuffer bcc = new StringBuffer();
			for(String a : bccList){
				bcc.append(a).append(" ");
			}
			return bcc.toString();
		}
		/**
		 * @param 暗送对象email地址
		 */
		public void setBcc(String bcc) {
			String[] addressList = splitMailAddress(bcc);
			if(addressList==null || addressList.length==0){
				return;
			}
			//判断集合是否为空
			if(bccList == null){
				bccList = new ArrayList<String>();
			}
			//将拆分的地址加入集合中
			for(String a : addressList){
				bccList.add(a);
			}
		}
		/**
		 * 返回是否含有发送地址
		 * @return
		 */
		public boolean hasTo(){
			if(logger.isDebugEnabled()){
				logger.debug("hasTo="+toList);
			}
			return (toList != null && !toList.isEmpty());
		}
		/**
		 * 返回是否含有抄送地址
		 * @return
		 */
		public boolean hasCc(){
			return (ccList != null && !ccList.isEmpty());
		}
		/**
		 * 返回是否含有暗送地址
		 * @return
		 */
		public boolean hasBcc(){
			return (bccList != null && !bccList.isEmpty());
		}
    	/**
		 * @return 发送次数
		 */
		public int getSendCount() {
			return sendCount;
		}
		/**
		 * @param 发送次数
		 */
		public void setSendCount(int sendCount) {
			this.sendCount = sendCount;
		}
    	
		/**
		 * 增加一次发送次数
		 */
		public void addSendCount(){
			sendCount++;
		}
		
		/**
		 * 判断是否大于最大发送次数
		 * @param maxCount 最大发送次数
		 * @return 是否大于最大发送次数
		 */
		public boolean isMaxSendCount(int maxCount){
			return sendCount>maxCount;
		}
		
		/**
		 * 将邮件地址拆分成List
		 * @param address 地址，多个地址中间用空格、逗号或者分号分隔
		 * @return 
		 */
		private String[] splitMailAddress(String address){
			if(address == null || address.trim().equals("")){
				return new String[0];
			}
			//拆分地址
			String[] addressList = null;
			if(address.indexOf(" ") > -1){
				addressList = address.split(" ");
			}else if(address.indexOf(",") > -1){
				addressList = address.split(",");
			}else  if(address.indexOf(";") > -1){
				addressList = address.split(";");
			}else {
				addressList = new String[1];
				addressList[0] = address;
			}
			return addressList;
		}
    }
    
	/**
     * 内部类，文本邮件内容
     * @author 曹新龙
     */
    public class TextMailMessage extends MailMessageSupport implements MailMessage{
    	
    	private String subject; 
    	private String body;
    	
    	public TextMailMessage() {
			super();
		}
    	
		/**
		 * @param to 发送对象email地址
		 * @param subject 邮件标题
		 * @param body 邮件内容
		 */
		public TextMailMessage(String to, String subject, String body) {
			this(to, null, null, subject,body);
		}
		/**
		 * @param to 发送对象email地址
		 * @param cc 抄送对象email地址
		 * @param bcc 暗送对象email地址
		 * @param subject 邮件标题
		 * @param body 邮件内容
		 */
		public TextMailMessage(String to, String cc, String bcc, String subject,String body) {
			super();
			setTo(to);
			setCc(cc);
			setBcc(bcc);
			setSubject(subject);
			setBody(body);
		}
		
		/**
		 * @return 邮件标题
		 */
		public String getSubject() {
			return subject;
		}
		/**
		 * @param 邮件标题
		 */
		public TextMailMessage setSubject(String subject) {
			this.subject = subject;
			return this;
		}
		/**
		 * @return 邮件内容
		 */
		public String getBody() {
			return body;
		}
		/**
		 * @param 邮件内容
		 */
		public TextMailMessage setBody(String body) {
			this.body = body;
			return this;
		}
		
		public TextMailMessage addTo(String to){
			setTo(to);
			return this;
		}
		public TextMailMessage addCc(String cc){
			setCc(cc);
			return this;
		}
		public TextMailMessage addBcc(String bcc){
			setBcc(bcc);
			return this;
		}
		
		public MimeMessage getMimeMessage(Session session,String from) throws MessagingException ,UnsupportedEncodingException{
			if(!hasTo() || subject == null){
				if(logger.isDebugEnabled()){
					logger.debug("to="+getTo()+",subject="+subject);
				}
				if(logger.isDebugEnabled()){
					logger.debug("没有发送地址或者没有主题，不能发送邮件！");
				}
				return null;
			}
			
			MimeMessage msg = new MimeMessage(session);
			msg.setSentDate(new Date());
			InternetAddress fromAddress = new InternetAddress(from);
			msg.setFrom(fromAddress);
			// 设定发送地址
			msg.setRecipients(Message.RecipientType.TO, getToAddress());
			// 设定抄送地址
			if (hasCc()) {
				msg.setRecipients(Message.RecipientType.CC, getCCAddress());
			}
			// 设定暗送地址
			if (hasBcc()) {
				msg.setRecipients(Message.RecipientType.BCC, getBCCAddress());
			}

			msg.setSubject(this.getSubject(), "UTF-8");
			msg.setText(StringUtils.replace(getBody(),"<br>","\r\n"), "UTF-8");
			msg.saveChanges();

			return msg;
		}

    }
    
    
    
    /**
     * 内部类，网页格式邮件内容
     * @author 曹新龙
     */
    public class HTMLMailMessage extends MailMessageSupport implements MailMessage{
    	
    	private String subject; 
    	private String body;
    	private List<String> attachList;
    	
    	public HTMLMailMessage() {
			super();
		}
    	
		/**
		 * @param to 发送对象email地址
		 * @param subject 邮件标题
		 * @param body 邮件内容
		 */
		public HTMLMailMessage(String to, String subject, String body) {
			this(to, null, null, subject,body);
		}
		/**
		 * @param to 发送对象email地址
		 * @param cc 抄送对象email地址
		 * @param bcc 暗送对象email地址
		 * @param subject 邮件标题
		 * @param body 邮件内容
		 * @param attachs 邮件附件
		 */
		public HTMLMailMessage(String to, String cc, String bcc, String subject,String body,String... attachs) {
			this(to, cc, bcc, subject,body);
			if(attachs != null && attachs.length>0){
				for(int i=0;i<attachs.length;i++){
					addAttach(attachs[i]);
				}
			}
		}
		/**
		 * @param to 发送对象email地址
		 * @param cc 抄送对象email地址
		 * @param bcc 暗送对象email地址
		 * @param subject 邮件标题
		 * @param body 邮件内容
		 */
		public HTMLMailMessage(String to, String cc, String bcc, String subject,String body) {
			super();
			setTo(to);
			setCc(cc);
			setBcc(bcc);
			setSubject(subject);
			setBody(body);
		}
		
		
		
		/**
		 * @return 邮件标题
		 */
		public String getSubject() {
			return subject;
		}
		/**
		 * @param 邮件标题
		 */
		public HTMLMailMessage setSubject(String subject) {
			this.subject = subject;
			return this;
		}
		/**
		 * @return 邮件内容
		 */
		public String getBody() {
			return body;
		}
		/**
		 * @return 邮件附件列表
		 */
		public List<String> getAttachs(){
			return attachList;
		}
		/**
		 * @param 邮件附件名称
		 */
		public HTMLMailMessage addAttach(String fileName){
			if(attachList==null){
				attachList = new ArrayList<String>();
			}
			attachList.add(fileName);
			return this;
		}
		/**
		 * @param 邮件内容
		 */
		public HTMLMailMessage setBody(String body) {
			this.body = body;
			return this;
		}
		
		public HTMLMailMessage addTo(String to){
			setTo(to);
			return this;
		}
		public HTMLMailMessage addCc(String cc){
			setCc(cc);
			return this;
		}
		public HTMLMailMessage addBcc(String bcc){
			setBcc(bcc);
			return this;
		}
		
		public MimeMessage getMimeMessage(Session session,String from) throws MessagingException, UnsupportedEncodingException{
			if(!hasTo() || subject == null || body == null){
				if(logger.isDebugEnabled()){
					logger.debug("to="+getTo()+",subject="+subject);
				}
				if(logger.isDebugEnabled()){
					logger.debug("没有发送地址或者没有主题，不能发送邮件！");
				}
				return null;
			}
			
			MimeMessage msg = new MimeMessage(session);
			msg.setSentDate(new Date());
			InternetAddress fromAddress = new InternetAddress(from);
			msg.setFrom(fromAddress);
			// 设定发送地址
			msg.setRecipients(Message.RecipientType.TO, getToAddress());
			// 设定抄送地址
			if (hasCc()) {
				msg.setRecipients(Message.RecipientType.CC, getCCAddress());
			}
			// 设定暗送地址
			if (hasBcc()) {
				msg.setRecipients(Message.RecipientType.BCC, getBCCAddress());
			}

			msg.setSubject(this.getSubject(), "UTF-8");
			
            Multipart mp = new MimeMultipart();

            MimeBodyPart mbpContent = new MimeBodyPart();
            //mbpContent.setText(this.getBody(), "UTF-8");
            mbpContent.setContent(StringUtils.replace(getBody(),"\r\n", "<br>"),"text/html;charset=UTF-8");    
            
            mp.addBodyPart(mbpContent);

            //添加附件
            if(attachList != null && !attachList.isEmpty()){
            	for(String fileName : attachList){
	                MimeBodyPart mbpFile = new MimeBodyPart();
	                FileDataSource fds = new FileDataSource(fileName);
	                mbpFile.setDataHandler(new DataHandler(fds));
	                mbpFile.setFileName(MimeUtility.encodeWord(fds.getName()));
	                //向MimeMessage添加（Multipart代表附件）
	                mp.addBodyPart(mbpFile);
            	}

            }
            //向Multipart添加MimeMessage
            msg.setContent(mp);
            msg.saveChanges();
			
			return msg;
		}

    }
}
