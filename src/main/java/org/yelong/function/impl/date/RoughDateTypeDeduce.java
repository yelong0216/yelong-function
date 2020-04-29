/**
 * 
 */
package org.yelong.function.impl.date;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.yelong.commons.util.Dates;
import org.yelong.function.library.date.DateType;
import org.yelong.function.library.date.DateTypeDeduce;

/**
 * 粗略的推算日期类型
 * 
 * 节假日：年假、五一假、国庆假。
 * 休息日：周六、周末
 * 工作日：非节假日、工作日
 * @author PengFei
 * @since 1.0.0
 */
public class RoughDateTypeDeduce implements DateTypeDeduce{

	@Override
	public List<DateType> deduce(List<Date> dates) {
		if(CollectionUtils.isEmpty(dates)) {
			return Collections.emptyList();
		}
		List<DateType> dateTypes = new ArrayList<>(dates.size());
		for (Date date : dates) {
			int dateType = DateType.WORKDAY;
			int dayOfWeek = Dates.getDayOfWeek(date);
			//先判断是否是年假
			if(isNewYear(date) || isMayDay(date) || isNationalDay(date)) {
				dateType = DateType.HOLIDAY;
			} else if( dayOfWeek == 1 || dayOfWeek == 7) {//是否是周末
				dateType = DateType.RESTDAY;
			} else {
				dateType = DateType.WORKDAY;
			}
			dateTypes.add(new DateType(date,dateType));
		}
		return dateTypes;
	}
	
	/**
	 * 是否是新年
	 * @return <tt>true</tt>是新年
	 */
	protected boolean isNewYear(Date date) {
		int month = Dates.getMonth(date);
		int dayOfMonth = Dates.getDayOfMonth(date);
		return month == 0 && dayOfMonth == 1;
	}
	
	/**
	 * 是否是国庆节
	 * @return <tt>true</tt>是国庆节
	 */
	protected boolean isNationalDay(Date date) {
		int month = Dates.getMonth(date);
		int dayOfMonth = Dates.getDayOfMonth(date);
		return month == 9 && dayOfMonth == 1;
	}
	
	/**
	 * 是否是五一劳动节
	 * @return <tt>true</tt>是五一劳动节
	 */
	protected boolean isMayDay(Date date) {
		int month = Dates.getMonth(date);
		int dayOfMonth = Dates.getDayOfMonth(date);
		return month == 4 && dayOfMonth == 1;
	}
	
}
