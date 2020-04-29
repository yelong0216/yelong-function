/**
 * 
 */
package org.yelong.function.library.date;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.yelong.commons.util.Dates;

/**
 * 日期类型推断
 * @author PengFei
 */
public interface DateTypeDeduce {

	/**
	 * 推断日期类型
	 * @param date 日期
	 * @return 日期类型
	 */
	default DateType deduce(final Date date) {
		return deduce(Arrays.asList(date)).get(0);
	}
	
	/**
	 * 推断日期类型
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @see {@link Dates#getDate(int, int, int)}
	 * @return 日期类型
	 */
	default DateType deduce(final int year , int month , int day) {
		return deduce(Dates.getDate(year, month, day));
	}
	
	/**
	 * 批量推断日期类型 并确保与参数的顺序相同
	 * @param dates 日期集合
	 * @return 日期类型集合，且确保了与参数的顺序相同
	 */
	List<DateType> deduce(final List<Date> dates);
	
}
