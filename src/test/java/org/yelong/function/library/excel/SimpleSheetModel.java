/**
 * 
 */
package org.yelong.function.library.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author PengFei
 *
 */
public class SimpleSheetModel<M> {

	private String sheetName;

	private final Class<M> modelClass;

	private final List<M> models;

	private final Map<String, Dict> dicts = new HashMap<>();

	/**
	 * 字段与title
	 */
	private final Map<String, String> fieldMappingTitleTexts = new HashMap<>();

	/**
	 * 时间的格式化
	 */
	private final Map<String, String> dateFormat = new HashMap<>();

	/**
	 * 需要生成的字段
	 */
	private final List<String> operationFieldNames = new ArrayList<>();

	public SimpleSheetModel(final Class<M> modelClass, List<M> models) {
		this.modelClass = modelClass;
		this.models = models;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<M> getModels() {
		return models;
	}

	public Map<String, Dict> getDicts() {
		return dicts;
	}

	public Map<String, String> getFieldMappingTitleTexts() {
		return fieldMappingTitleTexts;
	}

	public String getFieldMappingTitleText(String fieldName) {
		return fieldMappingTitleTexts.get(fieldName);
	}

	public Map<String, String> getDateFormat() {
		return dateFormat;
	}

	/**
	 * 添加时间字段的格式化类型。这只有在该字段为 java.util.Date类型时生效
	 * 
	 * @param fieldName 字段名称
	 * @param format    格式化的类型
	 */
	public void addDateFormat(String fieldName, String format) {
		this.dateFormat.put(fieldName, format);
	}

	/**
	 * 添加字段对应的title
	 * 
	 * @param fieldName 字段名称
	 * @param text      文本
	 */
	public void addFieldMappingTitleText(String fieldName, String text) {
		this.fieldMappingTitleTexts.put(fieldName, text);
	}

	/**
	 * 获取需要操作的字段
	 * 
	 * @return
	 */
	public List<String> getOperationFieldNames() {
		return operationFieldNames;
	}

	/**
	 * 添加一个操作的字段
	 * 
	 * @param fieldName 字段名称
	 */
	public void addOperationField(String... fieldNames) {
		if (null != fieldNames) {
			for (String fieldName : fieldNames) {
				operationFieldNames.add(fieldName);
			}
		}
	}

	/**
	 * 添加一个字典
	 * 
	 * @param dictType  字典类型
	 * @param dictValue 字典值
	 * @param dictText  字典文本
	 */
	public synchronized Dict addDict(String dictType, String dictValue, String dictText) {
		Dict dict = dicts.get(dictType);
		if (null == dict) {
			dict = new Dict(dictType);
			dicts.put(dictType, dict);
		}
		dict.add(dictValue, dictText);
		return dict;
	}

	/**
	 * 获取字典
	 * 
	 * @param dictType
	 * @return
	 */
	public Dict getDict(String dictType) {
		return dicts.get(dictType);
	}

	public boolean existDict(String dictType) {
		return getDict(dictType) != null;
	}

	public String getDictText(String dictType, String dictValue) {
		Dict dict = getDict(dictType);
		if (null == dict) {
			return "";
		}
		return dict.getDictText(dictValue, "");
	}

	/**
	 * 添加时间字段的格式化类型。这只有在该字段为 java.util.Date类型时生效
	 * 
	 * @param fieldName 字段名称
	 * @param format    格式化的类型
	 */
	public String getDateFormat(String fieldName) {
		return this.dateFormat.get(fieldName);
	}

	public boolean existDateFormat(String fieldName) {
		return StringUtils.isNotBlank(getDateFormat(fieldName));
	}

	public Class<M> getModelClass() {
		return modelClass;
	}

}
