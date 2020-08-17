package org.yelong.function.library.date;

import java.util.Date;

import org.yelong.commons.util.Dates;

/**
 * 中国日期工具类
 * 
 * @since 1.0.0
 */
public class DateCNUtils {

	// 不允许实例化
	private DateCNUtils() {
	}

	/**
	 * 格式化星期
	 * 
	 * @param date 日期
	 * @return 格式化后的星期
	 */
	public static String formatDayOfWeek(Date date) {
		return formatDayOfWeek(Dates.getDayOfWeek(date));
	}

	/**
	 * 格式化星期
	 * 
	 * @param dayOfWeek {@link Dates#getDayOfWeek(Date)}
	 * @return 格式化后的星期
	 */
	public static String formatDayOfWeek(int dayOfWeek) {
		String value = "";
		switch (dayOfWeek) {
		case 1:
			value = "星期天";
			break;
		case 2:
			value = "星期一";
			break;
		case 3:
			value = "星期二";
			break;
		case 4:
			value = "星期三";
			break;
		case 5:
			value = "星期四";
			break;
		case 6:
			value = "星期五";
			break;
		case 7:
			value = "星期六";
			break;
		default:
			throw new IllegalArgumentException("无效的星期：" + dayOfWeek);
		}
		return value;
	}

}