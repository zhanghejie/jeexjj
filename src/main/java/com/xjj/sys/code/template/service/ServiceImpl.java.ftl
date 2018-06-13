/****************************************************
 * Description: ServiceImpl for ${model.label}
 * Copyright:   Copyright (c) ${model.year}
 * Company:     ${model.company}
 * @author      ${model.author}
 * @version     ${model.version}
 * @see
	HISTORY
    *  ${model.date} ${model.author} Create File
**************************************************/

package ${model.packageForServiceImpl};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xjj.framework.dao.XjjDAO;
import com.xjj.framework.service.XjjServiceSupport;
import ${model.packageForModel}.${model.name?cap_first}Entity;
import ${model.packageForDAO}.${model.name?cap_first}Dao;
import ${model.packageForService}.${model.name?cap_first}Service;

@Service
public class ${model.name?cap_first}ServiceImpl extends XjjServiceSupport<${model.name?cap_first}Entity> implements ${model.name?cap_first}Service {

	@Autowired
	private ${model.name?cap_first}Dao ${model.name?uncap_first}Dao;

	@Override
	public XjjDAO<${model.name?cap_first}Entity> getDao() {
		
		return ${model.name?uncap_first}Dao;
	}
}