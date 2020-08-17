/**
 * 
 */
package org.yelong.function.impl.excel;

import java.awt.FontMetrics;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.yelong.commons.beans.BeanUtilsE;
import org.yelong.commons.io.FileUtilsE;
import org.yelong.function.library.excel.GenerateException;
import org.yelong.function.library.excel.ModelSheetGenerator;
import org.yelong.function.library.excel.SimpleSheetModel;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @author PengFei
 *
 */
public class JXLModelSheetGenerator implements ModelSheetGenerator {

	@SuppressWarnings("deprecation")
	@Override
	public <M> File generate(SimpleSheetModel<M> sheetModel, String filePath) throws GenerateException {
		try {
			String sheetName = sheetModel.getSheetName();
			WritableWorkbook workbook = builder(filePath);
			// 创建新的一页
			WritableSheet sheet = workbook.createSheet(sheetName, 1);
			List<Field> fields = FieldUtils.getAllFieldsList(sheetModel.getModelClass());
			List<String> operationFieldNames = sheetModel.getOperationFieldNames();
			if (!operationFieldNames.isEmpty()) {
				fields = fields.stream().filter(x -> operationFieldNames.contains(x.getName()))
						.collect(Collectors.toList());
			}
			// 第一行
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				String columnName = StringUtils.isNotEmpty(sheetModel.getFieldMappingTitleText(field.getName()))
						? sheetModel.getFieldMappingTitleText(field.getName())
						: field.getName();
				// 创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
				// 列的宽度与excel的宽度大约比例为 x/7+1
				int columnH = getStringWidth(columnName) / 6 + 6;
				WritableFont titleWf = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD, false);
				WritableCellFormat wcf = new WritableCellFormat(titleWf);
				wcf.setAlignment(Alignment.CENTRE); // 设置对齐方式
				wcf.setVerticalAlignment(VerticalAlignment.CENTRE); // 设置为垂直居中
				wcf.setBorder(Border.ALL, BorderLineStyle.THIN); // 添加边框
				Label column = new Label(i, 0, columnName, wcf);
				sheet.addCell(column);
				sheet.setRowView(i, 400);
				sheet.setColumnView(i, columnH);
			}
			List<M> models = sheetModel.getModels();
			// model数据 i是行
			for (int i = 0; i < models.size(); i++) {
				M model = models.get(i);
				int row = i + 1;
				// j 是列
				for (int j = 0; j < fields.size(); j++) {
					Field field = fields.get(j);
					String fieldName = field.getName();
					Object value = BeanUtilsE.getProperty(model, fieldName);
					if (null == value) {
						value = "";
					} else {
						if (sheetModel.existDict(fieldName)) {
							value = sheetModel.getDictText(fieldName, (String) value);
						} else if (value instanceof Date) {
							if (sheetModel.existDateFormat(fieldName)) {
								value = DateFormatUtils.format((Date) value, sheetModel.getDateFormat(fieldName));
							}
						}
					}
					Label column = toLabel(value, j, row);
					sheet.addCell(column);
					int columnH = getStringWidth(column.getString()) / 6 + 6;
					// 如果内容长度大于原单元格长度则替换
					CellView columnView = sheet.getColumnView(j);
					if (columnView.getDimension() < columnH) {
						sheet.setColumnView(j, columnH);
					}
					sheet.setRowView(i, 400);
				}
			}
			workbook.write();
			workbook.close();
			return FileUtilsE.getFile(filePath);
		} catch (Exception e) {
			throw new GenerateException(e);
		}
	}

	private Label toLabel(Object value, int column, int row) throws WriteException {
		if (null == value) {
			value = "--";
		} else if (value instanceof Character && StringUtils.isEmpty(value.toString())) {
			value = "--";
		}
		String str = value.toString();
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 12);
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setAlignment(Alignment.LEFT); // 设置对齐方式
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE); // 设置为垂直居中
		// wcf.setBorder(Border.ALL, BorderLineStyle.THICK); // 添加边框
		wcf.setBorder(Border.ALL, jxl.format.BorderLineStyle.THIN); // 添加边框
		return new Label(column, row, str, wcf);
	}

	private static int getStringWidth(String str) {
		JLabel label = new JLabel(str);
		FontMetrics metrics = label.getFontMetrics((label.getFont()));
		// int textH = metrics.getHeight(); //字符串的高, 只和字体有关
		int textW = metrics.stringWidth(label.getText()); // 字符串的宽
		return textW;
	}

	/**
	 * 构建excel 如果文件不存在则创建。
	 * 
	 * @date 2019年11月14日下午1:11:22
	 * @version 1.2
	 * @param fileAbsolutePath excel文件
	 * @return
	 * @throws IOException
	 * @throws BiffException
	 */
	public static WritableWorkbook builder(String fileAbsolutePath) throws IOException, BiffException {
		File file = FileUtilsE.createNewFileOverride(fileAbsolutePath);
		return Workbook.createWorkbook(file);
	}

}
