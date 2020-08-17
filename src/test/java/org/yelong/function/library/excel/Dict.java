/**
 * 
 */
package org.yelong.function.library.excel;

import java.util.HashMap;

import org.yelong.commons.util.map.MapWrapper;

/**
 * @author PengFei
 */
public class Dict extends MapWrapper<String, Object> {

	private final String dictType;

	public Dict(final String dictType) {
		super(new HashMap<>());
		this.dictType = dictType;
	}

	/**
	 * 添加一个字典
	 * 
	 * @param dictValue 字典值
	 * @param dictText  字典显示文本
	 */
	public void add(String dictValue, Object dictText) {
		this.put(dictValue, dictText);
	}

	public String getDictType() {
		return dictType;
	}

	public String getDictText(String dictValue) {
		return (String) get(dictValue);
	}

	public String getDictText(String dictValue, String defaultText) {
		return (String) getOrDefault(dictValue, defaultText);
	}

}
