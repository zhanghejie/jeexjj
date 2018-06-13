/****************************************************
 * Description: DAO for ${model.label}
 * Copyright:   Copyright (c) ${model.year}
 * Company:     ${model.company}
 * @author      ${model.author}
 * @version     ${model.version}
 * @see
	HISTORY
    *  ${model.date} ${model.author} Create File
**************************************************/
package ${model.packageForDAO};

import ${model.packageForModel}.${model.name?cap_first}Entity;
import com.xjj.framework.dao.XjjDAO;

public interface ${model.name?cap_first}Dao  extends XjjDAO<${model.name?cap_first}Entity> {
	
}

