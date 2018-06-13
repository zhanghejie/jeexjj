package com.xjj.framework.utils;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("rawtypes")
public class ExcelUtils {
	protected final Log log = LogFactory.getLog(getClass());
	
  /**
   * @param args
   */
  public static void main(String[] args) {
  	
  }

  /**
   * 每个list写一个sheet
   * 
   * 
   * @author amy
   * @param os
   * @param list
   * @return
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public static OutputStream writeExcels(OutputStream os, List<Map> list) throws Exception {
    WritableWorkbook book = Workbook.createWorkbook(os);
    WritableSheet ws = null;
    Map map = null;
    for (int i = 0; i < list.size(); i++) {
      map = list.get(i);
      String key = map.keySet().toArray()[0].toString();
      List<String[]> valueList = (List<String[]>) map.get(key);
      ws = book.createSheet(key, i);
      Iterator<String[]> cell = valueList.iterator();
      int row = 0;
      while (cell.hasNext()) {
        String[] data = cell.next();
        addRow(ws, data, row);
        row++;
      }
    }
    book.write();
    book.close();
    return os;
  }

  /**
   * 把list内容写到一个sheet并写入输出流中
   * 
   * 
   * @author amy
   * @param os
   * @param list
   * @return
   * @throws Exception
   */
  public static OutputStream writeExcel(OutputStream os, List<String[]> list) throws Exception {
    WritableWorkbook book = Workbook.createWorkbook(os);
    WritableSheet ws = book.createSheet("第一页", 0);
    Iterator<String[]> i = list.iterator();
    int row = 0;
    while (i.hasNext()) {
      String[] data = i.next();
      addRow(ws, data, row);
      row++;
    }
    book.write();
    book.close();
    return os;
  }

  /**
   * 合并单元格
   * 
   * @param ws
   * @param value
   * @param rowNum
   * @return
   * @throws Exception
   */
  public static WritableSheet mergeCells(WritableSheet ws, int col1, int row1, int col2, int row2)
      throws Exception {
    ws.mergeCells(col1, row1, col2, row2);
    return ws;
  }

  /**
   * 添加一条记录
   * 
   * @param ws
   * @param value
   * @param rowNum
   * @return
   * @throws Exception
   */
  public static WritableSheet addRow(WritableSheet ws, String[] value, int rowNum) throws Exception {
    int length = value.length;
    for (int i = 0; i < length; i++) {
      ws.addCell(mode(i, rowNum, value[i]));
    }
    return ws;
  }

  /**
   * 添加一条记录
   * 
   * @param ws
   * @param value
   * @param rowNum
   * @return
   * @throws Exception
   */
  public static WritableSheet addRow(WritableSheet ws, Object[] value, int rowNum) throws Exception {
    int length = value.length;
    for (int i = 0; i < length; i++) {
      if (value[i] == null) {
        ws.addCell(mode(i, rowNum, ""));
      } else {
        ws.addCell(mode(i, rowNum, value[i].toString()));
      }
    }
    return ws;
  }

  /**
   * 添加一条记录
   * 
   * @param ws
   * @param value
   * @param rowNum
   * @return
   * @throws Exception
   */
  public static WritableSheet addRowNoColor(WritableSheet ws, Object[] value, int rowNum)
      throws Exception {
    int length = value.length;
    for (int i = 0; i < length; i++) {
      ws.addCell(modeNoColor(i, rowNum, value[i].toString()));
    }
    return ws;
  }

  /**
   * 添加一条记录,可以设置指定列的宽度
   * 
   * @param ws
   * @param value
   * @param rowNum
   * @param exColumn
   * @return
   * @throws Exception
   */
  public static WritableSheet addRow(WritableSheet ws, String[] value, int rowNum, int[][] exColumn)
      throws Exception {
    if (exColumn == null || exColumn.length == 0 || value == null || value.length == 0) {
      throw new NullPointerException("设置宽度的列不能为空");
    }
    int length = value.length;
    for (int i = 0; i < length; i++) {
      ws.addCell(mode(i, rowNum, value[i]));
      for (int j = 0; j < exColumn.length; j++) {
        // 需要设置宽度的列
        if (i == exColumn[j][0]) {
          ws.setColumnView(i, exColumn[j][1]);
          continue;
        }
      }
    }
    return ws;
  }
  /**
   * 添加一条记录,可以设置指定列的宽度
   * 
   * @param ws
   * @param value
   * @param rowNum
   * @param exColumn
   * @return
   * @throws Exception
   */
  public static WritableSheet addRow(WritableSheet ws, String[] value, Integer rowNum, Integer[] exColumn)
      throws Exception {
    if (value == null || value.length == 0) {
      throw new NullPointerException("数据结果为空");
    }
    int length = value.length;
    for (int i = 0; i < length; i++) {
      ws.addCell(mode(i, rowNum, value[i]));
      if(exColumn == null || exColumn.length == 0){
      	continue;
      }
      // 需要设置宽度的列
      ws.setColumnView(i, exColumn[i]);
    }
    return ws;
  }

  /**
   * 添加一条记录,元素放到指定位置
   * 
   * @param ws
   * @param value
   * @param rowNum
   * @return
   * @throws Exception
   */
  public static WritableSheet addRow(WritableSheet ws, String[] value, int[] x, int y)
      throws Exception {
    int length = value.length;
    for (int i = 0; i < length; i++) {
      ws.addCell(mode(x[i], y, value[i]));
    }
    return ws;
  }

  /**
   * 添加一条记录,元素放到指定位置
   * 
   * @param ws
   * @param value
   * @param rowNum
   * @return
   * @throws Exception
   */
  public static WritableSheet addRow(WritableSheet ws, Object[] value, int[] x, int y)
      throws Exception {
    int length = value.length;
    for (int i = 0; i < length; i++) {
      ws.addCell(mode(x[i], y, value[i].toString()));
    }
    return ws;
  }

  /**
   * 添加一条记录,元素放到指定位置,并且设置exColumn列的宽度
   * 
   * @param ws
   * @param value
   * @param x
   * @param y
   * @param exColumn
   * @return
   * @throws Exception
   */
  public static WritableSheet addRow(WritableSheet ws, String[] value, int[] x, int y,
      int[][] exColumn) throws Exception {
    if (exColumn == null || exColumn.length == 0) {
      throw new NullPointerException("设置宽度的列不能为空");
    }
    int length = value.length;
    for (int i = 0; i < length; i++) {
      ws.addCell(mode(x[i], y, value[i]));
      for (int j = 0; j < exColumn.length; j++) {
        // 需要设置宽度的列
        if (i == exColumn[j][0]) {
          ws.setColumnView(i, exColumn[j][1]);
          continue;
        }
      }
    }
    return ws;
  }

  /**
   * cell的格式
   * 
   * @param x
   * @param y
   * @param str
   * @return
   * @throws Exception
   */
  public static Label mode(int x, int y, String str) throws Exception {
    WritableFont arial10ptBold;
    if (y == 0) {
      arial10ptBold = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD);
    } else {
      arial10ptBold = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD);
    }
    WritableCellFormat arial10BoldFormat = new WritableCellFormat(arial10ptBold, NumberFormats.TEXT);// 设置文本格式
    arial10BoldFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
    arial10BoldFormat.setWrap(true);
    if (y % 2 == 1) {// 奇数行蓝色
      arial10BoldFormat.setBackground(Colour.ICE_BLUE);
    }
    Label L = new Label(x, y, str, arial10BoldFormat);
    return L;
  }

  /**
   * cell的格式
   * 
   * @param x
   * @param y
   * @param str
   * @return
   * @throws Exception
   */
  public static Label modeNoColor(int x, int y, String str) throws Exception {
    WritableFont arial10ptBold;
    if (y == 0) {
      arial10ptBold = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD);
    } else {
      arial10ptBold = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD);
    }
    WritableCellFormat arial10BoldFormat = new WritableCellFormat(arial10ptBold, NumberFormats.TEXT);// 设置文本格式
    arial10BoldFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
    Label L = new Label(x, y, str, arial10BoldFormat);
    return L;
  }

  /**
   * 添加一个单元格,元素放到指定位置
   * 
   * @param ws
   * @param value
   * @param rowNum
   * @return
   * @throws Exception
   */
  public static WritableSheet addCell(WritableSheet ws, String value, int x, int y)
      throws Exception {
    ws.addCell(modeNoColor(x, y, value));
    return ws;
  }
  /**
	 * 返回到客户端
	 * @param request
	 * @param response
	 * @param bytes
	 * @param fileName
	 * author liyin 
	 * create time 4:22:49 PM
	 */
	public static void exportToResponseForXls(HttpServletRequest request, HttpServletResponse response,byte[] bytes, String fileName) {
		OutputStream ouputStream = null;
		try {
			if (bytes != null && bytes.length > 0) {
				response.reset();
				if (StringUtils.isBlank(fileName)) {
					fileName = "导出excel文件";
				}
				// 解决linux+ie下乱码问题
				String userAgent = request.getHeader("User-Agent");
				boolean isIE = false;
				if (userAgent.indexOf("MSIE") > 0) isIE = true;
				if (isIE) {
					fileName = new String(fileName.getBytes("gb2312"), "iso-8859-1");
				} else {
					fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
				}
				
				fileName = fileName + ".xls";
				
				response.setContentType("application/vnd.ms-excel");
				response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
				response.addHeader("Content-Length", "" + bytes.length);
				
				ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();
			}else{
				response.getWriter().write("no data for export..");
			}
		} catch (Exception e) {
			// log.error(e.getMessage());
		} finally {
			if (ouputStream != null) {
				try {
					ouputStream.close();
				} catch (Exception err) {
					ouputStream = null;
				}
			}
		}
	}
	
		 /**
	     * 文件下载
	     * @return
	     */
	    public static void download(String filePath,HttpServletResponse response) {
			//生成doc
			try {
				// 下载本地文件
		        String fileName = FileUtils.getFileName(filePath);
		        // 读到流中
		        InputStream inStream = new FileInputStream(filePath);
		        // 设置输出的格式
		        response.reset();
		        response.setContentType("bin");
		        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		        // 循环取出流中的数据
		        byte[] b = new byte[100];
		        int len;
		       
		            while ((len = inStream.read(b)) > 0)
		            {
		                response.getOutputStream().write(b, 0, len);
		            }
		            inStream.close();
				
				
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
	    }
}