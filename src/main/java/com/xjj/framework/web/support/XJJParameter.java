package com.xjj.framework.web.support;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;

import com.xjj.framework.utils.DateTimeUtils;
import com.xjj.framework.utils.StringUtils;

/**
 * 记录页面传递过来的查询信息，其中key的含义：属性名@操作@类型  
 * 格式：propName@operation@type      
 *                                                          
 * 支持的操作：                                              
 * 	eq = EQUAL(=)——默认，如果没有操作则为等于                 
 * 	lk = LIKE(like '%keyword%')                             
 *  kl= LIKE(like '%keyword')                               
 *  kr= LIKE(like 'keyword%')                               
 * 	gt = GREAT(>)                                           
 * 	lt = LESS(<)                                            
 * 	ge = GREAT_EQUAL(>=)                                    
 *	le = LESS_EQUAL(<=)                                     
 *	nu = IS_NULL(is null)                                   
 *	ne = NOT_EQUAL(!=)                                      
 *	nn = NOT_NULL(is not null)                              
 *                                                          
 * 支持的类型：                                              
 * s = 字符串string：String ——默认，如果没有类型，则为字符串   
 * b = 逻辑boolean：Boolean                                  
 * i = 整数int：Integer                                      
 * l = 长整数long：Long                                      
 * f = 浮点float：Float                                      
 * d = 双浮点double：Double                                  
 * D = 日期date：Date(格式：yyyy-MM-dd)                      
 * t = 时间datetime：Date(格式：yyyy-MM-dd hh:mm:ss)         
 *                                                          
 * 例如：                                                   
 * query.user_id@eq@l=1    对应sql is : and user_id = 1        
 * query.name@eq@s=tom     对应sql is : and name = 'tom'       
 * query.name@lk@s=tom     对应sql is : and name like '%tom%'  
 * @author zhanghejie
 */
public class XJJParameter{

	private static String PARAM_FIX = "query.";
	private static String PARAM_PATTERN = "[a-z_A-Z0-9]+@[a-z]{2}(@[sbilfDdt]{1})?";
	/**
	 * Map存储示例 
	 * {
	 * 	user_mame:{like:"%张三%","<=":"张三丰"}
	 * 	age:{">":33,"<="50}
	 * 	orderBy:{id:asc,age:desc}
	 * }
	 */
	private HashMap<String,HashMap<String,Object>> paramMap = new HashMap<String,HashMap<String,Object>>();
	
	
	public HashMap<String,HashMap<String,Object>> getQueryMap()
	{
		return paramMap;
	}
	public void clear() {
		paramMap.clear();
	}
	
	public boolean containsKey(String key) {
		 key = StringUtils.toUnderScoreCase(key);
		return paramMap.containsKey(key);
	}
	
	public HashMap<String,Object> get(String key) {
		
		 key = StringUtils.toUnderScoreCase(key);
		 
		return paramMap.get(key);
	}
	
	/**
	 * 获得查询
	 * @param query 例：
	 * @return
	 */
	public Object getQuery(String query) {
		
		if(StringUtils.isBlank(query))
		{
			return null;
		}
		query=query.replace(PARAM_FIX, "");
		String[] propArr = query.split("@");
		if(propArr.length<2)
		{
			return null;
		}
	    String propName = StringUtils.toUnderScoreCase(propArr[0].replace(PARAM_FIX,""));
	    String propOper = propArr[1];
	    propName = StringUtils.toUnderScoreCase(propName);
		HashMap<String,Object> operMap = paramMap.get(propName);
			
		if(null==operMap)
		{
			return null;
		}
		return operMap.get(oper2sql(propOper));
	}
	
	/**
	 * 是否包含
	 * @param query
	 * @return
	 */
	public boolean hasQuery(String query) {
		Object obj = getQuery(query);
		if(null==obj)
		{
			return false;
		}else
		{
			return true;
		}
	}
	
	
	public boolean isEmpty() {
		return paramMap.isEmpty();
	}
	
	public Set<String> keySet() {
		return paramMap.keySet();
	}

	public void addQuery(String key, String val) {
		
		if(StringUtils.isBlank(key) || StringUtils.isBlank(val) || !key.startsWith(PARAM_FIX))
		{
			return;
		}
		key=key.replace(PARAM_FIX, "");
		 
	    boolean isMatch = Pattern.matches(PARAM_PATTERN, key);
	    
	    if(!isMatch)
	    {
	    	//丢弃or抛异常？
	    	return ;
	    }
	    
		String propType =null;
		String[] propArr = key.split("@");
	    String propName = StringUtils.toUnderScoreCase(propArr[0].replace(PARAM_FIX,""));
	    String propOper = propArr[1];
		if(propArr.length==3)
		{
			propType = propArr[2];
		}
	    
		HashMap<String,Object> param = paramMap.get(propName);
		if(null==param)
		{
			param = new HashMap<String,Object>();
		}
		Object obj = type2obj(propOper,propType,val);
		System.out.println(propName+"=="+oper2sql(propOper)+" "+obj);
		param.put(oper2sql(propOper),obj);
	    paramMap.put(propName, param);
	}
	
	public void addQuery(String sqlKey, Number val) {
		addQuery(sqlKey, String.valueOf(val));
	}
	
	public void addQuery(String sqlKey, Date val) {
		addQuery(sqlKey, DateTimeUtils.formatDateTime(val));
	}
	
	/**
	 * 添加升序字段
	 * @param propName
	 */
    public void addOrderByAsc(String propName) {
    	propName=StringUtils.toUnderScoreCase(propName);
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put(propName,  "asc");
    	paramMap.put("orderBy",map);
		
	}
    
    /**
	 * 添加降序字段
	 * @param propName
	 */
    public void addOrderByDesc(String propName) {
    	propName=StringUtils.toUnderScoreCase(propName);
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put(propName,  "desc");
    	paramMap.put("orderBy",map);
	}


	
	public HashMap<String,Object> remove(String arg0) {
		return paramMap.remove(arg0);
	}

	
	public int size() {
		return paramMap.size();
	}

	
	public Collection<HashMap<String,Object>> values() {
		return paramMap.values();
	}
	
		
	public String getSqlCondition() {
		
		StringBuilder sqlCondition = new StringBuilder();
		Object obj = null;
		for (String key : paramMap.keySet()) { 
			obj = null;
			if(!"orderBy".equals(key))
			{
				
				for (String oper : paramMap.get(key).keySet()) { 
					sqlCondition.append(" and ");
					sqlCondition.append(key);
					sqlCondition.append(" ");
					sqlCondition.append(oper);
					
					obj = paramMap.get(key).get(oper);
					if(null != obj)
					{
						sqlCondition.append(" ");
						if(obj instanceof String)
						{
							sqlCondition.append("'"+obj+"'");
						}else if(obj instanceof Date)
						{
							sqlCondition.append("str_to_date('"+DateTimeUtils.formatDateTime((Date)obj)+"','%Y-%m-%d %H:%i:%s')");
						}else
						{
							sqlCondition.append(obj);
						}
					}
				}
				
				
				
			}
		}
		
		//拼装排序
		
		HashMap<String,Object> orderMap = paramMap.get("orderBy");
		if(null!=orderMap && !orderMap.isEmpty())
		{
			sqlCondition.append(" order by ");
			int i = 0;
			for (String key : orderMap.keySet()) { 
				sqlCondition.append(key);
				sqlCondition.append(" ");
				sqlCondition.append(orderMap.get(key));
				i++;
				if(i!=orderMap.size())
				{
					sqlCondition.append(",");
				}
			}
		}
		
		System.out.println("sql=="+sqlCondition.toString());
		return sqlCondition.toString();
	}
	
	/**
	 * 将操作字符串转化为sql属性
	 * @param operator
	 * @return
	 */
	private String oper2sql(String operator){
		if(operator == null || operator.equals("")){
			return SqlOperEnum.EQUAL.getSql();
		}else if(operator.equals("lk")){
			return SqlOperEnum.LIKE.getSql();
		}else if(operator.equals("kl")){
			return SqlOperEnum.LIKE_LEFT.getSql();
		}else if(operator.equals("kr")){
			return SqlOperEnum.LIKE_RIGHT.getSql();
		}else if(operator.equals("gt")){
			return SqlOperEnum.GREAT_THAN.getSql();
		}else if(operator.equals("ge")){
			return SqlOperEnum.GREAT_EQUAL.getSql();
		}else if(operator.equals("lt")){
			return SqlOperEnum.LESS_THAN.getSql();
		}else if(operator.equals("le")){
			return SqlOperEnum.LESS_EQUAL.getSql();
		}else if(operator.equals("nu")){
			return SqlOperEnum.IS_NULL.getSql();
		}else if(operator.equals("ne")){
			return SqlOperEnum.NOT_EQUAL.getSql();
		}else if(operator.equals("nn")){
			return SqlOperEnum.NOT_NULL.getSql();
		}else{
			return SqlOperEnum.EQUAL.getSql();
		}
	}
	
	
	//数据类型 
	private static enum Type {s, b, i,l,f,d,D,t} ;
	
	private Object type2obj(String operator, String propType,String val){
		
		if(Type.D.toString().equals(propType))
		{
			System.out.println(operator+"=="+propType+"--"+val);
		}
		
		Object obj = null;
		if(Type.D.toString().equals(propType))
		{
			obj= DateTimeUtils.parseShort(val);
			return obj;
		}
		if(Type.t.toString().equals(propType))
		{
			obj= DateTimeUtils.parse(val);
			return obj;
		}
		
		if(operator.equals(SqlOperEnum.LIKE.getOper())){
			val= "%"+val+"%";
			return val;
		}else if(operator.equals(SqlOperEnum.LIKE_LEFT.getOper())){
			val= "%"+val;
			return val;
		}else if(operator.equals(SqlOperEnum.LIKE_RIGHT.getOper())){
			val= val+"%";
			return val;
		}
		if(Type.s.toString().equals(propType))
		{
			return val;
		}
		
		if(operator.equals(SqlOperEnum.NOT_NULL.getOper()) || operator.equals(SqlOperEnum.IS_NULL.getOper()))
		{
			return null;
		}
		
		//b, i,l,f,d,
		
		if(Type.b.toString().equals(propType))
		{
			return Boolean.valueOf(val);
		}
		
		if(Type.i.toString().equals(propType))
		{
			return Integer.valueOf(val);
		}
		
		if(Type.l.toString().equals(propType))
		{
			return Long.valueOf(val);
		}
		
		if(Type.f.toString().equals(propType))
		{
			return Float.valueOf(val);
		}
		
		if(Type.d.toString().equals(propType))
		{
			return Double.valueOf(val);
		}
		
		return val;
	}
	
	public static void main(String[] args) {
		 boolean isMatch = Pattern.matches("[a-z_A-Z0-9]+@[a-z]{2}(@[sbilfDdt]{1})?", "name@lk");
		 System.out.println(isMatch);
	}
}
