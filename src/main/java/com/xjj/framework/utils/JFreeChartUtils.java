package com.xjj.framework.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.Rotation;

public class JFreeChartUtils {


	
	/**
	 * step2:创建PieChart图表
	 * 
	 * @param dataset
	 * @return
	 */
	private static JFreeChart createPieChart(DefaultPieDataset dataset,
			String title, boolean is3D) {
		JFreeChart chart = null;
		if (is3D) {
			chart = ChartFactory.createPieChart3D(title, // 图表标题
					dataset, // 数据集
					true, // 是否显示图例
					true, // 是否显示工具提示
					true // 是否生成URL
					);
		} else {
			chart = ChartFactory.createPieChart(title, // 图表标题
					dataset, // 数据集
					true, // 是否显示图例
					true, // 是否显示工具提示
					true // 是否生成URL
					);
		}
		// 设置标题字体==为了防止中文乱码：必须设置字体
		chart.setTitle(new TextTitle(title, new Font("黑体", Font.ITALIC, 22)));
		// 设置图例的字体==为了防止中文乱码：必须设置字体
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 12));
		// 获取饼图的Plot对象(实际图表)
		PiePlot plot = (PiePlot) chart.getPlot();
		// 图形边框颜色
		plot.setBaseSectionOutlinePaint(Color.GRAY);
		// 图形边框粗细
		plot.setBaseSectionOutlineStroke(new BasicStroke(0.0f));
		// 设置饼状图的绘制方向，可以按顺时针方向绘制，也可以按逆时针方向绘制
		plot.setDirection(Rotation.ANTICLOCKWISE);
		// 设置绘制角度(图形旋转角度)
		plot.setStartAngle(70);
		// 设置突出显示的数据块
		// plot.setExplodePercent("One", 0.1D);
		// 设置背景色透明度
		plot.setBackgroundAlpha(0.7F);
		// 设置前景色透明度
		plot.setForegroundAlpha(0.65F);
		// 设置区块标签的字体==为了防止中文乱码：必须设置字体
		plot.setLabelFont(new Font("宋体", Font.PLAIN, 12));
		// 扇区分离显示,对3D图不起效
		if (is3D)
			plot.setExplodePercent(dataset.getKey(3), 0.1D);
		// 图例显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				//"{0}:{1}\r\n({2})", NumberFormat.getNumberInstance(),
				"({2})", NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));
		// 图例显示百分比
		// plot.setLegendLabelGenerator(new
		// StandardPieSectionLabelGenerator("{0}={1}({2})"));
		// 指定显示的饼图为：圆形(true) 还是椭圆形(false)
		plot.setCircular(true);
		// 没有数据的时候显示的内容
		plot.setNoDataMessage("找不到可用数据...");

		// 设置鼠标悬停提示
		plot.setToolTipGenerator(new StandardPieToolTipGenerator());
		// 设置热点链接
		// plot.setURLGenerator(new StandardPieURLGenerator("detail.jsp"));

		return chart;
	}

	private static JFreeChart createBarChart(CategoryDataset dataset,
			String title, String x, String y, boolean is3D) {
		JFreeChart chart = null;
		if (is3D) {
			chart = ChartFactory.createBarChart3D( // 3D柱状图
					// JFreeChart chart = ChartFactory.createLineChart3D(
					// //3D折线图
					title, // 图表的标题
					x, // 目录轴的显示标签
					y, // 数值轴的显示标签
					dataset, // 数据集
					PlotOrientation.VERTICAL, // 图表方式：V垂直;H水平
					true, // 是否显示图例
					false, // 是否显示工具提示
					false // 是否生成URL
					);
		} else {
			chart = ChartFactory.createBarChart( // 柱状图
					// JFreeChart chart = ChartFactory.createLineChart3D(
					// //3D折线图
					title, // 图表的标题
					x, // 目录轴的显示标签
					y, // 数值轴的显示标签
					dataset, // 数据集
					PlotOrientation.VERTICAL, // 图表方式：V垂直;H水平
					true, // 是否显示图例
					false, // 是否显示工具提示
					false // 是否生成URL
					);
		}

		// ===============为了防止中文乱码：必须设置字体
		chart.setTitle(new TextTitle(title, new Font("黑体", Font.ITALIC, 22)));

		LegendTitle legend = chart.getLegend(); // 获取图例
		legend.setItemFont(new Font("宋体", Font.BOLD, 12)); // 设置图例的字体，防止中文乱码

		CategoryPlot plot = (CategoryPlot) chart.getPlot(); // 获取柱图的Plot对象(实际图表)
		// 设置柱图背景色（注意，系统取色的时候要使用16位的模式来查看颜色编码，这样比较准确）
		plot.setBackgroundPaint(new Color(255, 255, 204));
		plot.setForegroundAlpha(0.65F); // 设置前景色透明度

		// 设置横虚线可见
		plot.setRangeGridlinesVisible(true);
		// 虚线色彩
		plot.setRangeGridlinePaint(Color.gray);

		CategoryAxis h = plot.getDomainAxis(); // 获取x轴
		h.setMaximumCategoryLabelWidthRatio(1.0f);// 横轴上的 Lable 是否完整显示
		h.setLabelFont(new Font("宋体", Font.BOLD, 12));// 设置字体，防止中文乱码
		h.setTickLabelFont(new Font("宋体", Font.BOLD, 12));// 轴数值
		// h.setCategoryLabelPositions(CategoryLabelPositions.UP_45);//45度倾斜

		plot.getRangeAxis().setLabelFont(new Font("宋体", Font.BOLD, 12)); // Y轴设置字体，防止中文乱码

		// 柱图的呈现器
		BarRenderer renderer = new BarRenderer();
		// 设置柱子宽度
		// renderer.setMaximumBarWidth(0.05);
		// 设置柱子高度
		// renderer.setMinimumBarLength(0.2);
		// 设置柱子边框颜色
		renderer.setBaseOutlinePaint(Color.BLACK);
		// 设置柱子边框可见
		renderer.setDrawBarOutline(true);
		// 设置每个柱的颜色
		renderer.setSeriesPaint(0, Color.BLUE);
		renderer.setSeriesPaint(1, Color.GREEN);
		renderer.setSeriesPaint(2, Color.RED);
		// 设置每个地区所包含的平行柱的之间距离
		renderer.setItemMargin(0.05);
		// 显示每个柱的数值，并修改该数值的字体属性
		renderer.setIncludeBaseInRange(true);
		renderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		// 设置柱的透明度
		plot.setForegroundAlpha(1.0f);
		// 给柱图添加呈现器
		plot.setRenderer(renderer);

		// 没有数据的时候显示的内容
		plot.setNoDataMessage("找不到可用数据...");

		return chart;
	}

	private static JFreeChart createSpiderChart(CategoryDataset categorydataset, String title) {
		ScaleSpiderWebPlot spiderwebplot = new ScaleSpiderWebPlot(categorydataset);
		spiderwebplot.setStartAngle(54D);
		spiderwebplot.setInteriorGap(0.40000000000000002D);
		spiderwebplot.setToolTipGenerator(new StandardCategoryToolTipGenerator());
		spiderwebplot.setMaxValue(10);
		spiderwebplot.setTicks(5);
		JFreeChart jfreechart = new JFreeChart(title, TextTitle.DEFAULT_FONT,
				spiderwebplot, false);
		LegendTitle legendtitle = new LegendTitle(spiderwebplot);
		legendtitle.setPosition(RectangleEdge.BOTTOM);
		jfreechart.addSubtitle(legendtitle);
		return jfreechart;
	}

	public static String generateSpiderChart(Map<String,Integer> data,String title,int width, int height, HttpSession session){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
		for(String key : data.keySet()){
			dataset.addValue(data.get(key),title,key); 
		}
		JFreeChart chart = createSpiderChart(dataset,title);
		String filename = "";
		try {
			filename = ServletUtilities.saveChartAsJPEG(chart,width, height, null, session);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return filename;
	}
	public static String generateSpiderChart(Map<String,Double> datas[],String title,int width, int height, HttpSession session){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
		for(Map<String,Double> data : datas){
			for(String key : data.keySet()){
				dataset.addValue(data.get(key),title,key); 
			}
		}
		JFreeChart chart = createSpiderChart(dataset,title);
		String filename = "";
		try {
			filename = ServletUtilities.saveChartAsJPEG(chart,width, height, null, session);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return filename;
	}
	
	/**
	 * 
	 * @param data
	 * @param title
	 * @param x
	 * @param y
	 * @param width
	 * @param heigth
	 * @param request
	 * @return
	 */
	public static String generateBarChart(List<Object[]> data, String title,
			String x, String y, int width, int heigth, boolean is3D, HttpSession session) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for (Iterator<Object[]> iterator = data.iterator(); iterator.hasNext();) {
			Object[] o = (Object[]) iterator.next();
			dataset.setValue(new Integer(o[1].toString()), o[0].toString(), o[2]
					.toString());
		}
		
//		dataset.addValue(200, "计划", "清华大学");   
//		dataset.addValue(400, "实报", "清华大学");   
//		dataset.addValue(360, "计划", "天津大学");   
//		dataset.addValue(520, "实报", "天津大学");   
//		dataset.addValue(100, "计划", "北京大学");   
//		dataset.addValue(150, "实报", "北京大学");   
//		dataset.addValue(280, "计划", "复旦大学");   
//		dataset.addValue(300, "实报", "复旦大学");   

		JFreeChart chart = createBarChart(dataset, title, x, y, is3D);
		
		
		String filename = "";
		try {
			filename = ServletUtilities.saveChartAsJPEG(chart,width, heigth, null, session);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		/*SimpleDateFormat df = new SimpleDateFormat("/yyyy/MM/dd/");
		String subPath = df.format(new Timestamp(System.currentTimeMillis())) + System.currentTimeMillis()+ ".jpg";
		String returnPath = "/images/barchart" + subPath;
		System.out.println("-----------------"+returnPath);
		File f = new File(filePath + returnPath);
		f.getParentFile().mkdirs();
		
		drawToOutputStream(f.getAbsolutePath(), chart, width, heigth);*/
		
		return filename;
	}

	/**
	 * 生成饼图，返回文件所在的路径
	 * 
	 * @param dataMap key为项目名称， value为项目值
	 * @param title //饼图名称
	 * @param width //饼图宽
	 * @param heigth //饼图高
	 * @param is3D //是否是3D显示
	 * @param filePath //文件的所在目录路径
	 * @return
	 */
	public static String generatePieChart(Map<String, Double> dataMap, String title,
			int width, int heigth, boolean is3D, String filePath) {
		//设定根目录
		if(!filePath.endsWith("/")){filePath = filePath + "/";}
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		Set<String> keySet = dataMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			dataset.setValue(key, dataMap.get(key));
		}
		
		JFreeChart chart = createPieChart(dataset, title, is3D);
		
		SimpleDateFormat df = new SimpleDateFormat("/yyyy/MM/dd/");
		String subPath = df.format(new Timestamp(System.currentTimeMillis())) + System.currentTimeMillis()+ ".jpg";
		String returnPath = "/images/piechart/" + subPath;

		File f = new File(filePath + returnPath);
		f.getParentFile().mkdirs();
		
		drawToOutputStream(f.getAbsolutePath(), chart, width,heigth);
		
		return filePath + returnPath;
	}
	
	

	/**
	 * step3: 输出图表到指定的磁盘
	 * 
	 * @param destPath
	 * @param chart
	 */
	private static boolean drawToOutputStream(String destPath,
			JFreeChart chart, int width, int heigth) {
		FileOutputStream fos = null;
		boolean returnValue = true;
		try {
			fos = new FileOutputStream(destPath);
			ChartUtilities.writeChartAsPNG(fos, // 指定目标输出流
					chart, // 图表对象
					width, // 宽
					heigth, // 高
					null); // ChartRenderingInfo信息
		} catch (IOException e) {
			e.printStackTrace();
			returnValue = false;
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return returnValue;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		CategoryDataset dataset = createBarDataSet1();
		JFreeChart barChart = createBarChart(dataset, "111111111111111",
				"2222222", "2222222", true);
		drawToOutputStream("D:\\bar.png", barChart, 320, 240);
		
		DefaultPieDataset pieDataSet = createPieDataSet2();
		JFreeChart pieChart = createPieChart(pieDataSet, "Pie Test", false);
		drawToOutputStream("D:\\pie.png", pieChart, 320, 240);
	}
//	/**
//	 * step1:创建 简单数据集对象
//	 * 
//	 * @return
//	 */
//	private static DefaultPieDataset createPieDataSet1() {
//		DefaultPieDataset dataset = new DefaultPieDataset();
//		dataset.setValue("java程序设计语言", 10000);
//		dataset.setValue("JSP基础与案例开发详解", 20000);
//		dataset.setValue("struts基础与案例开发详解", 30000);
//		dataset.setValue("精通JSF", 40000);
//
//		return dataset;
//	}

	private static DefaultPieDataset createPieDataSet2() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue(" 市场前期", new Double(10));
		dataset.setValue(" 立项", new Double(15));
		dataset.setValue(" 计划", new Double(10));
		dataset.setValue(" 需求与设计", new Double(10));
		dataset.setValue(" 执行控制", new Double(35));
		dataset.setValue(" 收尾", new Double(10));
		dataset.setValue(" 运维", new Double(10));
		return dataset;
	}

	/**
	 * @return
	 */
	private static CategoryDataset createBarDataSet1() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(10000, "", "Corejava");
		dataset.setValue(20000, "", "JavaWeb");
		dataset.setValue(30000, "", "易用struts");
		dataset.setValue(40000, "", "精通JSF");

		return dataset;
	}

//	/**
//	 * 组合数据集对象
//	 * 
//	 * @return
//	 */
//	private static CategoryDataset createBarDataSet2() {
//		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//		dataset.setValue(5000, "北京", "Corejava");
//		dataset.setValue(3000, "上海", "Corejava");
//		dataset.setValue(2000, "广州", "Corejava");
//
//		dataset.setValue(10000, "北京", "JavaWeb");
//		dataset.setValue(6000, "上海", "JavaWeb");
//		dataset.setValue(4000, "广州", "JavaWeb");
//
//		dataset.setValue(15000, "北京", "易用struts");
//		dataset.setValue(5000, "上海", "易用struts");
//		dataset.setValue(10000, "广州", "易用struts");
//
//		dataset.setValue(20000, "北京", "精通JSF");
//		dataset.setValue(10000, "上海", "精通JSF");
//		dataset.setValue(10000, "广州", "精通JSF");
//
//		return dataset;
//	}
}
