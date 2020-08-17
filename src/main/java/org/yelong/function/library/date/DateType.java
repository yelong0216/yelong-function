/**
 * 
 */
package org.yelong.function.library.date;

import java.io.Serializable;
import java.util.Date;

/**
 * 日期类型。分为：工作日、休息日、节假日。不是这几个类型的日期均为未知类型的日期
 * 
 * @since 1.0.0
 */
public class DateType implements Serializable {

	private static final long serialVersionUID = -8302164151297843441L;

	/**
	 * 工作日
	 */
	public static final int WORKDAY = 0;

	/**
	 * 休息日
	 */
	public static final int RESTDAY = 1;

	/**
	 * 节假日
	 */
	public static final int HOLIDAY = 2;

	private final Date date;

	private final int type;

	/**
	 * @param date 日期
	 * @param type 类型。限定但不限于的值： {@link WORKDAY} 工作日 {@link RESTDAY} 休息日
	 *             {@link HOLIDAY} 节假日 不是上述值时均为未知的类型
	 */
	public DateType(final Date date, final int type) {
		this.date = date;
		this.type = type;
	}

	/**
	 * 是否是工作日
	 * 
	 * @return <tt>true</tt> 是工作日
	 */
	public boolean isWorkday() {
		return type == WORKDAY;
	}

	/**
	 * 是否是休息日
	 * 
	 * @return <tt>true</tt> 是休息日
	 */
	public boolean isRestday() {
		return type == RESTDAY;
	}

	/**
	 * 是否是节假日
	 * 
	 * @return <tt>true</tt> 是节假日
	 */
	public boolean isHoliday() {
		return type == HOLIDAY;
	}

	/**
	 * 是否是未知的日期类型
	 * 
	 * @return <tt>true</tt> 未知的日期类型
	 */
	public boolean isUnknown() {
		switch (type) {
		case WORKDAY:
		case RESTDAY:
		case HOLIDAY:
			return false;
		default:
			return true;
		}
	}

	/**
	 * @return date
	 */
	public Date date() {
		return date;
	}

	/**
	 * @return type 日期的类型
	 * @see #WORKDAY
	 * @see #RESTDAY
	 * @see #HOLIDAY
	 */
	public int type() {
		return type;
	}

}
