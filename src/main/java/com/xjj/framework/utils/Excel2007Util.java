package com.xjj.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.xjj.framework.exception.BusinessException;

public class Excel2007Util {
	private static final String dateFormat = "yyyy-MM-dd";
	//static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");SimeDateFormat是非线性安全的

	/**
	 * 读excel
	 * 返回List<String[]>
	 * 都转化为字符串
	 * @param filePath
	 *  excel路径
	 */
	public static List<String[]> readExcel(String filePath) {
		SimpleDateFormat sFormat = new SimpleDateFormat(dateFormat);
		Workbook book = null;
		List<String[]> list = new ArrayList<String[]>();
		try {
			book = getExcelWorkbook(filePath);
			Sheet sheet = getSheetByNum(book, 1);

			int lastRowNum = sheet.getLastRowNum();

			Row row = null;
			for (int i = 1; i <= lastRowNum; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					int lastCellNum = row.getLastCellNum();
					Cell cell = null;
					String[] strs = new String[lastCellNum];
					for (int j = 0; j < lastCellNum; j++) {
						cell = row.getCell(j);
						if (cell != null) {
							String type_cn = null;
							int type = cell.getCellType();
							String value = "";
							switch (type) {
							case 0:
								if (DateUtil.isCellDateFormatted(cell)) {
									type_cn = "NUMBER-DATE";
									Date date = cell.getDateCellValue();
									value = sFormat.format(date);
								} else {
									type_cn = "NUMBER";
									double tempValue = cell.getNumericCellValue();
									DecimalFormat df = new DecimalFormat("#");
									value = String.valueOf(df.format(tempValue));
									if(value.endsWith(".0")){
										value = value.substring(0,value.lastIndexOf(".0"));
									}
								}
								break;
							case 1:
								type_cn = "STRING";
								value = cell.getStringCellValue();
								break;
							case 2:
								type_cn = "FORMULA";
								value = cell.getCellFormula();
								break;
							case 3:
								type_cn = "BLANK";
								value = cell.getStringCellValue();
								break;
							case 4:
								type_cn = "BOOLEAN";
								boolean tempValue = cell.getBooleanCellValue();
								value = String.valueOf(tempValue);
								break;
							case 5:
								type_cn = "ERROR";
								byte b = cell.getErrorCellValue();
								value = String.valueOf(b);
							default:
								break;
							}
							if (null!=type_cn && type_cn.equals("ERROR")) {
								throw new BusinessException("第" + (i + 1)
										+ "行,第" + (j + 1) + "列的单元格内容是：" + value
										+ ",格式不正确");
							}
							strs[j] = value;
						}
					}
					list.add(strs);
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 写内容到excel中
	 * 1、dataList 数据
	 * 2、response下载处理
	 * 3、columns  
	 * key：对应数据实体的显示属性名
	 * value：导出excel后title（首行）的名字
	 * 如  BaseUser 
	 * key loginName
	 * value  "登录名"
	 * 排序 放进去的顺序
	 * 
	 * 4、字典字段集合
	 * 判断是否包含字典字段
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static void write(List dataList, LinkedHashMap<String, String> columns,HttpServletResponse response,String fileName) {
		try {
			if(StringUtils.isBlank(fileName))
			{
				fileName = String.valueOf(System.currentTimeMillis());
			}
			
			Workbook book = new SXSSFWorkbook();
			Sheet sheet = book.createSheet();
			sheet.setDefaultColumnWidth((short)(25));
			
			Row row = sheet.createRow((short) 0);
			
			int cell_index = 0;
			for (String str: columns.keySet()) {
				String name = columns.get(str);
				setCell(row.createCell(cell_index),name);
				cell_index++;
			}
			int row_index = 1;
			for(Object obj:dataList){
				row  =  sheet.createRow(row_index);
				cell_index = 0;
				for(String key:columns.keySet()){
					
					if(null == key || key.trim().equals(""))
					{
						setCell(row.createCell(cell_index),"");
					}else
					{
						Object str=ReflectUtils.getValue(obj,key);
						if(str!=null){
							setCell(row.createCell(cell_index),String.valueOf(str));
						}else{
							setCell(row.createCell(cell_index),"");
						}
					}
					cell_index++;
				}
				row_index++;
			}
		    OutputStream out = response.getOutputStream();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName + ".xlsx");
			book.write(out);
			out.flush();
			out.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void setCell(Cell cell,String name){
		cell.setCellValue(name);
	}


	/**
	 * 获取excel的Workbook
	 * 
	 * @throws IOException
	 */
	public static Workbook getExcelWorkbook(String filePath) throws IOException {
		Workbook book = null;
		File file = null;
		FileInputStream fis = null;

		try {
			file = new File(filePath);
			if(!file.exists()){
				throw new RuntimeException("文件不存在");
			}
			fis = new FileInputStream(file);
			book = WorkbookFactory.create(fis);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {

		}
		return book;
	}

	/**
	 * 根据索引 返回Sheet
	 * 
	 * @param number
	 */
	public static Sheet getSheetByNum(Workbook book, int number) {
		Sheet sheet = null;
		try {
			sheet = book.getSheetAt(number - 1);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return sheet;
	}

}
