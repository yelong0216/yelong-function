/**
 * 
 */
package org.yelong.function.impl.date;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yelong.commons.util.Dates;
import org.yelong.function.library.date.DateType;
import org.yelong.function.library.date.DateTypeDeduce;

/**
 * 自定义规则的日期类型推断 可以自定一添加 某年某月某日是工作日、休息日还是节假日。
 * 
 * 未定义的日期均为未知日期类型
 * 
 * @since 1.0.0
 */
public class CustomDateTypeDeduce implements DateTypeDeduce {

	private final Map<String, Integer> customDateTypes = new HashMap<>();

	@Override
	public List<DateType> deduce(List<Date> dates) {
		List<DateType> dateTypes = new ArrayList<DateType>(dates.size());
		for (Date date : dates) {
			int dateType = getDateType(Dates.getYear(date), Dates.getMonth(date), Dates.getDayOfMonth(date));
			dateTypes.add(new DateType(date, dateType));
		}
		return dateTypes;
	}

	/**
	 * 添加工作日
	 * 
	 * @param year  年
	 * @param month 月
	 * @param day   日
	 */
	public void addWorkday(int year, int month, int day) {
		add(year, month, day, DateType.WORKDAY);
	}

	/**
	 * 添加休息日
	 * 
	 * @param year  年
	 * @param month 月
	 * @param day   日
	 */
	public void addRestday(int year, int month, int day) {
		add(year, month, day, DateType.RESTDAY);
	}

	/**
	 * 添加节假日
	 * 
	 * @param year  年
	 * @param month 月
	 * @param day   日
	 */
	public void addHoliday(int year, int month, int day) {
		add(year, month, day, DateType.HOLIDAY);
	}

	/**
	 * 添加
	 * 
	 * @param year     年
	 * @param month    月
	 * @param day      日
	 * @param dateType 日期类型
	 */
	public void add(int year, int month, int day, int dateType) {
		customDateTypes.put(getKey(year, month, day), dateType);
	}

	/**
	 * 添加
	 * 
	 * @param date     日期
	 * @param dateType 日期类型
	 */
	public void add(Date date, int dateType) {
		add(Dates.getYear(date), Dates.getMonth(date), Dates.getDayOfMonth(date), dateType);
	}

	/**
	 * 移除
	 * 
	 * @param year  年
	 * @param month 月
	 * @param day   日
	 */
	public void remove(int year, int month, int day) {
		this.customDateTypes.remove(getKey(year, month, day));
	}

	/**
	 * 清空
	 */
	public void clear() {
		this.customDateTypes.clear();
	}

	/**
	 * @param year  年
	 * @param month 月
	 * @param day   日
	 * @return 定义的key
	 */
	private String getKey(int year, int month, int day) {
		return "" + year + month + day;
	}

	/**
	 * 获取日期的类型
	 * 
	 * @param year  年
	 * @param month 月
	 * @param day   日
	 * @return 日期的类型。如果类型不存在则返回{@link Integer#MAX_VALUE}
	 */
	public int getDateType(int year, int month, int day) {
		Integer dateType = customDateTypes.get(getKey(year, month, day));
		if (null == dateType) {
			return Integer.MAX_VALUE;
		}
		return dateType;
	}

}
