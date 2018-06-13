package com.xjj.sec.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjj.common.XJJConstants;
import com.xjj.framework.dao.CommonDao;
import com.xjj.framework.dao.XjjDAO;
import com.xjj.framework.exception.ValidationException;
import com.xjj.framework.service.XjjServiceSupport;
import com.xjj.framework.utils.DateTimeUtils;
import com.xjj.framework.utils.StringTools;
import com.xjj.framework.utils.StringUtils;
import com.xjj.framework.utils.ValidatorUtils;
import com.xjj.framework.web.support.Pagination;
import com.xjj.framework.web.support.XJJParameter;
import com.xjj.sec.dao.UserDao;
import com.xjj.sec.entity.XjjUser;
import com.xjj.sec.service.UserService;
import com.xjj.sys.xfile.dao.XfileDao;
import com.xjj.sys.xfile.entity.XfileEntity;

@Service
public class UserServiceImpl extends XjjServiceSupport<XjjUser> implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private XfileDao xfileDao;

	@Override
	public XjjDAO<XjjUser> getDao() {
		
		return userDao;
	}
	
	public Pagination findPage (XJJParameter query, Pagination page)
	{
		int totalRecord = getDao().getCount(query.getQueryMap());
		page.setTotalRecord(totalRecord);
			
		int limit  = page.getPageSize();
		int currentPage = page.getCurrentPage();
		int offset = (currentPage-1)*limit;
		
		if(XJJConstants.USER_TYPE_ADMIN.equals(query.getQuery("userType@eq")))
		{
			page.setItems(userDao.managerPage(query.getQueryMap(),offset,limit));
		}else
		{
			page.setItems(userDao.findPage(query.getQueryMap(),offset,limit));
		}
		return page;
	}
	
	
	/**
	 * 导入用户
	 * @param fileId
	 * @param orgId
	 * @param loginInfo
	 */
    public Map<String,Object> saveImportUser(Long fileId) throws ValidationException{
		//校验数据并返回合法数据
		List<XjjUser> users = this.validImportUser(fileId,fileId);
		if(users==null||users.size()==0){
			throw new ValidationException("文件用户数据为空，请重新上传");
		}

		int troubleUserCnt = 0;
		int okCnt = 0;
		//保存用户
		for (int i = 0; i < users.size(); i++) {
			try {
				XjjUser user = users.get(i);
				
				System.out.println("====user.mobile======"+user.getMobile());
				
				userDao.save(user);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ValidationException(e.getMessage());
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("allCnt", users.size());
		map.put("troubleUserCnt", troubleUserCnt);
		map.put("okCnt", okCnt);
		return map;
    }
    
    private List<XjjUser> validImportUser(Long fileId,Long orgId) throws ValidationException{
		//获得上传文件
		XfileEntity xfile=xfileDao.getById(fileId);
		if(xfile==null){
			throw new ValidationException("未找到上传文件");
		}
		/**
	     * 1.验证上传文件是否为空并且文件格式是否是xls
	     */
		String fileName=xfile.getFileRealname();       
		String prefix=fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
		if(!prefix.equals("xls") && !prefix.equals("xlsx")){
			throw new ValidationException("请上传xls或者xlsx格式的文件");
		}
		
		String[] content = null;
		Workbook workbook = null;
		Sheet sheet = null;
		int length = 0;
		try {
			File file = new File((xfile.getFilePath()));
			workbook = Workbook.getWorkbook(file);
			sheet = workbook.getSheet(0);
			length = sheet.getRows();
		} catch (Exception ex) {
			throw new ValidationException("文件格式转换异常");
		}
		/**
	     * 2.验证上传文件中的Excel是否是6列
	     */
		
		StringBuilder validationMsg = new StringBuilder();;
		
		
		Cell cell = null;
		int columns = 5;
		if(sheet != null && sheet.getColumns() < columns){
			validationMsg.append("上传失败：上传文件中的Excel列数必须是"+columns+"列。<br/>");
		}
		List<XjjUser> users=new ArrayList<XjjUser>();
		/**
	     * 3.验证上传文件中的Excel每一行每一列都不为空
	     */
		String loginName = null;//登陆账号
		String userNameZh = null;//姓名
		String gender = null;//性别
		String mobile = null;//手机
		String email = null;//邮箱
		for (int i = 2; i < length; i++) {// 第2行开始
			content = new String[sheet.getColumns()];
			
			for (int j = 0; j < columns; j++) {
				cell = sheet.getCell(j, i);
				content[j] = cell.getContents().trim();
			}
			
			if(content[0] ==null || StringUtils.isBlank(content[0]))
			{
				validationMsg.append("●文件中第" + (i+1) + "行没有填写账号。<br/>");
				continue;
			}
			
			loginName = content[0].trim();//登陆账号
			
			if("auto".equals(loginName))
			{
				loginName="xjj"+StringTools.getRandomString(4)+DateTimeUtils.getCurrentDateTimeString("yyyy-MM-dd HH:mm:ss");
			}
			
			userNameZh = content[1].trim();//姓名
			mobile= content[2].trim();//手机
			email = content[3].trim();//邮箱
			gender = content[4].trim();//性别
			
			if(userNameZh.equals("") && gender.equals("") && mobile.equals("") && email.equals("")){
				validationMsg.append("●文件中第" + (i+1) + "行为空行，请检查并删除。<br/>");
			}
			if(StringUtils.isBlank(userNameZh)){
				validationMsg.append("●文件中第" + (i+1) + "行姓名为空。<br/>");
			}else{
				if(userNameZh.length() > 20){
					validationMsg.append("●文件中第" + (i+1) + "行姓名长度不能超过20个字符。<br/>");
				}
			}
			if(!StringUtils.isBlank(gender)){
				if(!gender.equals("男") && !gender.equals("女")){
					validationMsg.append("●文件中第" + (i+1) + "行性别有误。<br/>");
				}
			}
			
			if (!StringUtils.isBlank(mobile)) {// 验证邮箱是否填写
				if(!ValidatorUtils.isPhotoNumber(mobile)){
					validationMsg.append("●文件中第"+ (i+1) +"行的手机号格式不正确。<br/>");
				}
				Boolean checkMobile = checkMobile(mobile,null);
				if (!checkMobile) { // 验证用户名的唯一性
					validationMsg.append("●文件中第"+ (i+1) +"行的手机号已经存在。<br/>");
				}
			}
			
			if (!StringUtils.isBlank(email)) {// 验证邮箱是否填写
				if(email.length() > 60){
					validationMsg.append("●文件中第"+ (i+1) +"行的邮箱不能超过60个字符。<br/>");
				}
				Boolean checkEmail = checkEmail(email,null);
				if (!checkEmail) { // 验证邮箱的唯一性
					validationMsg.append("●文件中第"+ (i+1) +"行的邮箱已经存在。<br/>");
				}
			}
		
		
			if(!StringUtils.isBlank(validationMsg.toString()))
			{
				continue;
			}
			
			XjjUser baseUser = new XjjUser();
			baseUser.setLoginName(loginName);
			baseUser.setUserName(userNameZh.replace(" ", "").replace("	", ""));
			
			baseUser.setGender(gender);
			if(!StringUtils.isBlank(mobile))
			{
				baseUser.setMobile(mobile);
			}
			if(!StringUtils.isBlank(email))
			{
				baseUser.setEmail(email);
			}
			
			baseUser.setPassword(XJJConstants.USER_INIT_PASSWORD);
			
			baseUser.setCreateDate(DateTimeUtils.getCurrentDate());
			baseUser.setStatus(XJJConstants.COMMON_STATUS_VALID);
			baseUser.setUserType(XJJConstants.USER_TYPE_USER);
			users.add(baseUser);
		}
		
		if(!StringUtils.isBlank(validationMsg.toString()))
		{
			throw new ValidationException(validationMsg.toString());
		}
	    return users;
	}
    
    
    
    
    private Boolean checkEmail(String email, Long id) {
    	int count = commonDao.checkUniqueVal("t_sec_user", "email", email,null);
    	
    	System.out.println("email=count="+count);
		if(count>0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	private Boolean checkLoginName(String loginName, Long id) {
		if(commonDao.checkUniqueVal("t_sec_user", "login_name", loginName,null)>0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * 校验手机号是否重复
	 * @param mobile
	 * @param id
	 * @return
	 */
	private Boolean checkMobile(String mobile, Long id) {
		int count = commonDao.checkUniqueVal("t_sec_user", "mobile", mobile,null);
		System.out.println("mobile=count="+count);
		if(count>0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}