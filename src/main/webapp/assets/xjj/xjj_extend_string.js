/**
 * 扩展一些js默认的方法
 * 1.string.contains
 * 2.string.endWith
 * 3.string.startWith
 * 4.string.inArray
 */
// string--------------------------------------------------------------------------------
/**
 * contains方法
 * @param s
 * @returns {Boolean}
 */
String.prototype.contains = function(s){
	return this.indexOf(s) != -1;
};

/**
 * endWith方法
 * @param s
 * @returns {Boolean}
 */
String.prototype.endWith=function(s){  
    if(this && s && this.length > s.length){
    	if(this.substring(this.length-s.length)==s){
    		return true;
    	}
    }
    return false;
};

/**
 * 判断string是否以s开头
 * @param s
 * @returns {Boolean}
 */
String.prototype.startWith=function(s){  
    if(this && s && this.length > s.length){
    	if(this.substr(0,s.length)==s){  
    		return true;
    	}  
    }
    return false;
};

/**
 * 判断string是否在一个array内
 * @param array
 * @returns {Boolean}
 */
String.prototype.inArray = function(array){
	if(this && array){
		for(var i=0; i<array.length; i++){
			if(this == array[i]){
				return true;
			}
		}
	}
	return false;
};