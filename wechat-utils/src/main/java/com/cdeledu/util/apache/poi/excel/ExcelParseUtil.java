package com.cdeledu.util.apache.poi.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: 用开源组件POI3.9 动态解析EXCEL文档
 *       <ul>
 *       <li>Java读取excel文件的顺序是</li>
 *       <li>Excel文件->工作表->行->单元格 对应到POI中为:workbook->sheet->row->cell</li>
 *       <li>● sheet:以0开始,以workbook.getNumberOfSheets()-1结束</li>
 *       <li>● row:以0开始(getFirstRowNum),以getLastRowNum结束</li>
 *       <li>● cell:以0开始(getFirstCellNum),以getLastCellNum结束</li>
 *       <li>●</li>
 *       <li></li>
 *       </ul>
 * @创建者: 皇族灬战狼
 * @联系方式: duleilewuhen@sina.com
 * @创建时间: 2018年9月10日 下午9:30:30
 * @版本: V0.1
 * @since: JDK 1.7
 */
public final class ExcelParseUtil {
	/** ----------------------------------------------------- Fields start */
	private static Logger logger = Logger.getLogger(ExcelImportUtil.class);
	/* 默认的日期格式 */
	private static SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** ----------------------------------------------------- Fields end */
	/**
	 * @方法描述 : 获取单元格的数据
	 *       <ul>
	 *       <li>获取key的值,针对不同类型获取不同的值</li>
	 *       </ul>
	 * @param cell
	 * @return
	 */
	private static String getCellFormatValue(Cell cell) {
		String cellValue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BLANK: // 空，不知道何时算空
				cellValue = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN: // 布尔类型
				cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
				break;
			case Cell.CELL_TYPE_FORMULA:// 表达式类型
				switch (cell.getCachedFormulaResultType()) {
				case Cell.CELL_TYPE_NUMERIC: // 数字&日期
					if (DateUtil.isCellDateFormatted(cell)) {
						cellValue = Format.format(cell.getDateCellValue());
					} else {
						cellValue = String.valueOf(cell.getNumericCellValue());
					}
					break;
				case Cell.CELL_TYPE_STRING: // 字符串
					cellValue = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BOOLEAN: // Boolean
					cellValue = String.valueOf(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_BLANK: // 空值
					break;
				case Cell.CELL_TYPE_ERROR: // 故障
					break;
				default:
					cellValue = cell.getCellFormula();
					break;
				}
				break;
			case Cell.CELL_TYPE_NUMERIC:// 数字&日期类型
				// 判断当前的cell是否为Date
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = Format.format(cell.getDateCellValue());
				} else { // 如果是纯数字
					// 取得当前Cell的数值,默认返回时Integer类型
					// new DecimalFormat("0").format(cell.getNumericCellValue())
					Integer num = new Integer((int) cell.getNumericCellValue());
					cellValue = String.valueOf(num);
				}
				break;
			case Cell.CELL_TYPE_STRING: // 字符串类型
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_ERROR: // 故障
				break;
			default:
				break;
			}
		}

		return cellValue.trim();
	}

	/**
	 * @方法描述 : 解析execl文档内容
	 * @param workBook
	 *            文档对象
	 * @param clazz
	 *            工作表 sheet
	 * @return
	 * @throws Exception
	 */
	private static <T> Collection<T> getContent(Workbook workBook, Class<T> clazz)
			throws Exception {
		Collection<T> result = new ArrayList<T>();

		Field[] fields = clazz.getDeclaredFields();

		// 获取第一页
		Sheet sheet = workBook.getSheetAt(0);
		// 获取行数
		int rowMem = sheet.getLastRowNum();

		if (rowMem < 1) {
			return new ArrayList<T>();
		}

		/**
		 * 循环工作表 Sheet 并获取工作表信息
		 */
		// 获取Excel的sheet数量
		Integer sheetNum = workBook.getNumberOfSheets();

		for (int numSheet = 0; numSheet < sheetNum; numSheet++) {
			sheet = workBook.getSheetAt(numSheet);// 取得相应工作表

			if (sheet == null) {
				continue;
			}

			/**
			 * <ul>
			 * <li>循环行Row,获取 行信息</li>
			 * <li>一般情况下,正文内容应该从第二行开始,第一行为表头的标题</li>
			 * </ul>
			 */

			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}
				T instance = (T) clazz.newInstance();

				// 循环列Cell,获取单元格信息
				for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
					Cell cell = row.getCell(cellNum);
					if (cell == null) {
						continue;
					}

					Field field = fields[cellNum];
					String cellValue = getCellFormatValue(cell);
					field.setAccessible(true);
					Class<?> type = field.getType();
					if (type == String.class) {
						if (StringUtils.isNotBlank(cellValue)) {
							field.set(instance, cellValue);
						}
					} else if (type == double.class || type == Double.class) {
						if (StringUtils.isNotBlank(cellValue)) {
							field.set(instance, Double.parseDouble(cellValue));
						}
					} else if (type == int.class || type == Integer.class) {
						if (StringUtils.isNotBlank(cellValue)) {
							field.set(instance, Integer.parseInt(cellValue));
						}
					} else if (type == float.class || type == Float.class) {
						if (StringUtils.isNotBlank(cellValue)) {
							field.set(instance, Float.parseFloat(cellValue));
						}
					} else if (type == long.class || type == Long.class) {
						if (StringUtils.isNotBlank(cellValue)) {
							field.set(instance, Long.parseLong(cellValue));
						}
					} else if (type == Date.class) {
						if (StringUtils.isNotBlank(cellValue)) {
							field.set(instance, Format.parse(cellValue));
						}
					} else {
						field.set(instance, null);
					}
				}
				result.add(instance);
			}
		}
		return result;
	}

	/**
	 * @方法描述 : 解析Excel文档文件
	 * @param excelPath
	 *            excel文件路径
	 * @param clazz
	 *            解析Class实体类
	 * @return
	 */
	public static <T> Collection<T> excelParse(String excelPath, Class<T> clazz) {
		Collection<T> result = new ArrayList<T>();
		InputStream input = null;
		try {
			// 加载文档
			input = new FileInputStream(new File(excelPath));
			Workbook workBook = WorkbookFactory.create(input);
			result = getContent(workBook, clazz);
		} catch (Exception ex) {
			logger.equals(ex);
		} finally {
			IOUtils.closeQuietly(input);
		}
		return result;
	}
}
