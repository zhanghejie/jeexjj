package com.xjj.common.passport.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xjj.common.XJJConstants;
import com.xjj.framework.exception.DataAccessException;
import com.xjj.framework.json.XjjJson;
import com.xjj.framework.json.XjjJson.MessageType;
import com.xjj.framework.utils.EncryptUtils;
import com.xjj.framework.utils.StringUtils;
import com.xjj.framework.web.ManagerInfo;
import com.xjj.framework.web.SpringControllerSupport;
import com.xjj.framework.web.support.XJJParameter;
import com.xjj.sec.entity.XjjUser;
import com.xjj.sec.service.UserService;

@Controller
@RequestMapping("/passport/manager") 
public class PassportController extends SpringControllerSupport{
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login")
	public String managerLogin() {
		
		return "/passport/manager/login";
	}
	
	@RequestMapping(value = "/logout")
	public String managerLogout() {
		this.getRequest().getSession().invalidate();
		return "/passport/manager/login";
	}
	
	
	@RequestMapping(value = "/logon")
	public @ResponseBody XjjJson managerLogon(@RequestParam String loginName,
			@RequestParam String password,
			Model model) throws DataAccessException {
		
		String msg=validateLogin(loginName,password);
		model.addAttribute("loginName",loginName);
		if(msg!=null){
			return XjjJson.error(msg);
		}
		password = EncryptUtils.MD5Encode(password);//MD5密码加密
		
		
		//查询数据库
		XJJParameter param = new XJJParameter();
		param.addQuery("query.loginName@eq@s", loginName);
		param.addQuery("query.userType@eq@s",XJJConstants.USER_TYPE_ADMIN );
		XjjUser user = userService.getByParam(param);
		
		XjjJson json = new XjjJson();
		json.setType(MessageType.error);
		if(null==user)
		{
			json.setMessage("账号不存在");
			json.setItem("loginName");
			return json;
		}
		
		if(XJJConstants.COMMON_STATUS_INVALID.equals(user.getStatus()))
		{
			json.setMessage("账号已被禁用");
			json.setItem("loginName");
			return json;
		}
		
		if(!password.equals(user.getPassword()))
		{
			json.setMessage("用户密码不正确");
			json.setItem("password");
			return json;
		}
		
		ManagerInfo managerInfo = new ManagerInfo();
		managerInfo.setLoginName(user.getLoginName());
		managerInfo.setUserId(user.getId());
		managerInfo.setUserName(user.getUserName());
		managerInfo.setUserType(user.getUserType());
		
		this.getRequest().getSession().setAttribute(XJJConstants.SESSION_MANAGER_INFO_KEY, managerInfo);
		json.setType(MessageType.success);
		return json;
	}
	
	/**
	 * 验证登录信息
	 * @param base
	 */
	private String validateLogin(String loginname,String password){
		if(loginname==null||loginname.trim().length()==0){
			return "登录名不能为空";
		}
		if(password==null||password.trim().length()==0){
			return "密码不能为空";
		}
		return null;
	}
	
	
	
	/**
	 * 进入用户密码修改的页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/mdypwd")
	public String password(Model model){
		return this.getViewPath("mdypwd");
	}
	/**
	 * 进入用户基本信息修改的页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/mdyinfo")
	public   String infor(Model model){
		ManagerInfo loginInfo = this.getManagerInfo();
    	XjjUser user = userService.getById(loginInfo.getUserId());
    	model.addAttribute("user", user);
		return this.getViewPath("mdyinfo");
	}
	/**
	 * 密码修改
	 * @param oldpassword
	 * @param newpassword
	 * @param session
	 * @return
	 */
	 @RequestMapping("/mdypwd/save")
	  public @ResponseBody XjjJson passwordModify(@RequestParam("oldpassword") String oldpassword,@RequestParam("newpassword") String newpassword,HttpSession session){
	    	if(StringUtils.isBlank(oldpassword)) return XjjJson.error("修改失败,请输入原密码!");
	    	if(StringUtils.isBlank(newpassword)) return XjjJson.error("修改失败,请输入新密码!");
	    	ManagerInfo loginInfo = this.getManagerInfo();
	    	XjjUser loginUser = userService.getById(loginInfo.getUserId());
	    	if(!loginUser.getPassword().equals(EncryptUtils.MD5Encode(oldpassword))){
	    		return XjjJson.error("修改失败,您输入的原密码不正确!");
	    	}else{
		    	loginUser.setPassword(EncryptUtils.MD5Encode(newpassword));
		    	userService.update(loginUser);
	    		return XjjJson.success("密码修改成功!");
	    	}
	   	}
	 /**
	  * 登录用户基本信息修改
	  * @param baseUser
	  * @return
	  */
	 @RequestMapping("/mdyinfo/save")
	  public @ResponseBody XjjJson personalInfoModify(@ModelAttribute XjjUser baseUser){
		 XjjUser _baseUser = new XjjUser();
		_baseUser = userService.getById(baseUser.getId());
		_baseUser.setUserName(baseUser.getUserName());
		_baseUser.setBirthday(baseUser.getBirthday());
		_baseUser.setMobile(baseUser.getMobile());
		_baseUser.setEmail(baseUser.getEmail());
		_baseUser.setAddress(baseUser.getAddress());
		userService.update(_baseUser);
		return XjjJson.success("保存成功");
	   	}
	
}
