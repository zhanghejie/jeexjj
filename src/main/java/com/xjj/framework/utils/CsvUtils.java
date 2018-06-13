package com.xjj.framework.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV问卷的读取工具类
 * @author xjj
 *
 */
public class CsvUtils {
	private List<String> rowList = new ArrayList<String>();
	private List<List<String>> colList = new ArrayList<List<String>>();
	private String encode = "ISO8859-1";
	
	/**
	 * 构造函数私有
	 * @param filePath 文件名
	 * @param encode 源编码，默认ANSI
	 * @throws Exception
	 */
	private CsvUtils(String filePath, String encode) throws Exception {
		
		if(StringUtils.isBlank(encode)){
			this.encode = encode;
		}
		
		BufferedReader bufferedreader = null;
		try {
			bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)),this.encode) );
			String stemp;
			int pre_count = -1;
			int cur_count = 0;

			while ((stemp = bufferedreader.readLine()) != null) {
				rowList.add(stemp);
				List<String> cols = new ArrayList<String>();
				cur_count = 0;
				for (String col : stemp.split(",")) {
					cur_count++;
					cols.add(col);
				}
				colList.add(cols);
				if (pre_count > 0 && pre_count != cur_count) {
					throw new Exception("csv文件格式错误，列数不一致！");
				}
				pre_count = cur_count;
			}
		} catch (FileNotFoundException e1) {
			throw new Exception("没有找到文件！");
		} catch (IOException e) {
			throw new Exception("文件打开错误！");
		} finally {
			try {
				bufferedreader.close();
			} catch (Exception e) {}
		}
	}

	/**
	 * 打开CSV文件
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static CsvUtils openCsvFile(String fileName) throws Exception {
		return openCsvFile(fileName,null);
	}
	
	/**
	 * 打开CSV文件
	 * @param fileName
	 * @param encode
	 * @return
	 * @throws Exception
	 */
	public static CsvUtils openCsvFile(String fileName, String encode) throws Exception {
		if (fileName == null) {
			throw new IOException("no file name input! ");
		}
		return new CsvUtils(fileName,encode);
	}

	/**
	 * 返回所有的文本内容
	 * @return
	 */
	public List<String> getList() {
		return rowList;
	}

	/**
	 * 得到csv文件的行数
	 * @return
	 */
	public int getRowNum() {
		return rowList.size();
	}

	/**
	 *  得到csv文件的列数
	 * @return
	 */
	public int getColNum() {
		if (colList.size() > 0) {
			return colList.get(0).size();
		} else {
			return 0;
		}
	}

	/**
	 *  取得指定行的值
	 * @param index
	 * @return
	 */
	public String getRow(int index) {
		if (rowList.size() > index) {
			return rowList.get(index);
		} else {
			return null;
		}
	}

	/**
	 *  取得指定行的值
	 */
	public List<String> getRowList(int index) {
		if (colList.size() > index) {
			return colList.get(index);
		} else {
			return new ArrayList<String>();
		}
	}

	/**
	 *  取得指定列的值
	 * @param index
	 * @return
	 */
	public String getCol(int index) {
		if (getColNum() == 0 || this.getColNum() <= index) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for (List<String> cols : colList) {
			buffer.append(cols.get(index)).append(",");
		}
		return buffer.substring(0, buffer.length() - 1);
	}

	/**
	 * 取得指定列的值
	 * @param index
	 * @return
	 */
	public List<String> getColList(int index) {
		if (getColNum() == 0 || this.getColNum() <= index) {
			return new ArrayList<String>();
		}
		List<String> col_list = new ArrayList<String>();
		for (List<String> cols : colList) {
			col_list.add(cols.get(index));
		}
		return col_list;
	}

	/**
	 *  取得指定行，指定列的值
	 * @param row
	 * @param col
	 * @return
	 */
	public String getString(int row, int col) {
		if (this.getRowNum() <= row || this.getColNum() <= col) {
			return null;
		}
		return colList.get(row).get(col);
	}

	public static void main(String[] args) throws Exception {
		CsvUtils cu = CsvUtils.openCsvFile("d:/test.csv");
		for (int i = 0; i < cu.getRowNum(); i++) {
			for (int j = 0; j < cu.getColNum(); j++) {
				String title = cu.getString(i, j);// 得到第i行.第一列的数据.
				System.out.println("===title:" + title);
			}
			System.out.println(" ");
		}
	}

}
