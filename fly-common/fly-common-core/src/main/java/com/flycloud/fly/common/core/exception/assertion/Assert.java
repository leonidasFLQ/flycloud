package com.flycloud.fly.common.core.exception.assertion;

import com.flycloud.fly.common.core.exception.BaseException;
import com.flycloud.fly.common.core.exception.WrapMessageException;
import cn.hutool.core.util.ArrayUtil;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 异常断言接口
 * 提供简便的方式判断条件，并在条件满足时抛出异常，错误码和错误信息定义在枚举类中，在本断言方法中，传递错误信息需要的参数
 * @author fy
 * @date 2022/2/25
 **/

public abstract interface Assert {

	/**
	 * 创建BaseException基类异常
	 * @param args
	 * @return
	 */
	BaseException newException(Object... args);

	/**
	 * 创建BaseException基类异常
	 * @param t
	 * @param args
	 * @return
	 */
	BaseException newException(Throwable t, Object... args);

	/**
	 * 创建BaseException基类异常
	 * @param errMsg  自定义的错误信息
	 * @param args
	 * @return
	 */
	default BaseException newExceptionWithMsg(String errMsg, Object... args) {
		if (args != null && args.length > 0) {
			errMsg = MessageFormat.format(errMsg, args);
		}

		WrapMessageException e = new WrapMessageException(errMsg);
		throw newException(e, args);
	}

	/**
	 * 创建BaseException基类异常
	 * @param errMsg  自定义的错误信息
	 * @param t
	 * @param args
	 * @return
	 */
	default BaseException newExceptionWithMsg(String errMsg, Throwable t, Object... args) {
		if (ArrayUtil.isNotEmpty(args)) {
			errMsg = MessageFormat.format(errMsg, args);
		}

		WrapMessageException e = new WrapMessageException(errMsg, t);
		throw newException(e, args);
	}

	/**
	 * 断言对象非空，若为空，则抛出异常
	 * @param obj 待判断对象
	 */
	default void objAssertNotNull(Object obj) {
		if (obj == null) {
			throw newException();
		}
	}

	/**
	 * 断言对象非空，若为空，则抛出异常
	 * 异常信息msg支持传递参数方式，避免在判断之前进行字符串拼接操作
	 * @param obj 待判断对象
	 * @param args  message占位符对应的参数列表
	 */
	default void objAssertNotNull(Object obj, Object... args) {
		if (obj == null) {
			throw newException(args);
		}
	}

	/**
	 * 断言对象obj非空。如果对象obj为空，则抛出异常
	 * @param obj   待判断对象
	 * @param errMsg  自定义的错误信息
	 */
	default void objAssertNotNullWithMsg(Object obj, String errMsg) {
		if (obj == null) {
			throw newExceptionWithMsg(errMsg);
		}
	}

	/**
	 * 断言对象obj非空。如果对象obj为空，则抛出异常
	 * 异常信息msg支持传递参数方式，避免在判断之前进行字符串拼接操作
	 * @param obj 待判断对象
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void objAssertNotNullWithMsg(Object obj, String errMsg, Object... args) {
		if (obj == null) {
			throw newExceptionWithMsg(errMsg, args);
		}
	}

	/**
	 * 断言对象obj非空。如果对象obj为空，则抛出异常
	 * @param obj 待判断对象
	 * @param errMsg 自定义的错误信息
	 */
	default void objAssertNotNullWithMsg(Object obj, Supplier<String> errMsg) {
		if (obj == null) {
			throw newExceptionWithMsg(errMsg.get());
		}
	}

	/**
	 * 断言对象obj非空。如果对象obj为空，则抛出异常
	 * 异常信息msg支持传递参数方式，避免在判断之前进行字符串拼接操作
	 * @param obj 待判断对象
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void objAssertNotNullWithMsg(Object obj, Supplier<String> errMsg, Object... args) {
		if (obj == null) {
			throw newExceptionWithMsg(errMsg.get(), args);
		}
	}

	/**
	 * 断言字符串str不为空串（长度为0）。如果字符串str为空串，则抛出异常
	 * @param str 待判断字符串
	 */
	default void strAssertNotEmpty(String str) {
		if (null == str || "".equals(str.trim())) {
			throw newException();
		}
	}

	/**
	 * 断言字符串str不为空串（长度为0）。如果字符串str为空串，则抛出异常
	 * 异常信息msg支持传递参数方式，避免在判断之前进行字符串拼接操作
	 * @param str 待判断字符串
	 * @param args message占位符对应的参数列表
	 */
	default void strAssertNotEmpty(String str, Object... args) {
		if (str == null || "".equals(str.trim())) {
			throw newException(args);
		}
	}

	/**
	 * 断言字符串str不为空串（长度为0）。如果字符串str为空串，则抛出异常
	 * @param str 待判断字符串
	 * @param errMsg 自定义的错误信息
	 */
	default void strAssertNotEmptyWithMsg(String str, String errMsg) {
		if (null == str || "".equals(str.trim())) {
			throw newExceptionWithMsg(errMsg);
		}
	}

	/**
	 * 断言字符串str不为空串（长度为0）。如果字符串str为空串，则抛出异常
	 * 异常信息msg支持传递参数方式，避免在判断之前进行字符串拼接操作
	 * @param str 待判断字符串
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void strAssertNotEmptyWithMsg(String str, String errMsg, Object... args) {
		if (str == null || "".equals(str.trim())) {
			throw newExceptionWithMsg(errMsg, args);
		}
	}

	/**
	 * <断言数组arrays大小不为0。如果数组arrays大小为0，则抛出异常
	 * @param arrays 待判断数组
	 */
	default void arraysAssertNotEmpty(Object[] arrays) {
		if (arrays == null || arrays.length == 0) {
			throw newException();
		}
	}

	/**
	 * 断言数组arrays大小不为0。如果数组arrays大小为0，则抛出异常
	 * 异常信息msg支持传递参数方式，避免在判断之前进行字符串拼接操作
	 * @param arrays 待判断数组
	 * @param args message占位符对应的参数列表
	 */
	default void arraysAssertNotEmpty(Object[] arrays, Object... args) {
		if (arrays == null || arrays.length == 0) {
			throw newException(args);
		}
	}

	/**
	 * 断言数组arrays大小不为0。如果数组arrays大小为0，则抛出异常
	 * @param arrays 待判断数组
	 * @param errMsg 自定义的错误信息
	 */
	default void arraysAssertNotEmptyWithMsg(Object[] arrays, String errMsg) {
		if (arrays == null || arrays.length == 0) {
			throw newExceptionWithMsg(errMsg);
		}
	}

	/**
	 * 断言数组arrays大小不为0。如果数组arrays大小为0，则抛出异常
	 * 异常信息msg支持传递参数方式，避免在判断之前进行字符串拼接操作
	 * @param arrays 待判断数组
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void arraysAssertNotEmptyWithMsg(Object[] arrays, String errMsg, Object... args) {
		if (arrays == null || arrays.length == 0) {
			throw newExceptionWithMsg(errMsg, args);
		}
	}

	/**
	 * 断言数组arrays大小不为0。如果数组arrays大小为0，则抛出异常
	 * @param arrays 待判断数组
	 * @param errMsg 自定义的错误信息
	 */
	default void arraysAssertNotEmptyWithMsg(Object[] arrays, Supplier<String> errMsg) {
		if (arrays == null || arrays.length == 0) {
			throw newExceptionWithMsg(errMsg.get());
		}
	}

	/**
	 * 断言数组arrays大小不为0。如果数组arrays大小为0，则抛出异常
	 * 异常信息msg支持传递参数方式，避免在判断之前进行字符串拼接操作
	 * @param arrays 待判断数组
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void arraysAssertNotEmptyWithMsg(Object[] arrays, Supplier<String> errMsg, Object... args) {
		if (arrays == null || arrays.length == 0) {
			throw newExceptionWithMsg(errMsg.get(), args);
		}
	}

	/**
	 * 断言集合collection大小不为0。如果集合collection大小为0，则抛出异常
	 * @param c 待判断集合
	 */
	default void collectionAssertNotEmpty(Collection<?> c) {
		if (c ==  null || c.isEmpty()) {
			throw newException();
		}
	}

	/**
	 * 断言集合collection大小不为0。如果集合collection大小为0，则抛出异常
	 * @param c 待判断集合
	 * @param args message占位符对应的参数列表
	 */
	default void collectionAssertNotEmpty(Collection<?> c, Object... args) {
		if (c ==  null || c.isEmpty()) {
			throw newException(args);
		}
	}

	/**
	 * 断言集合collection大小不为0。如果集合collection大小为0，则抛出异常
	 * @param c 待判断集合
	 * @param errMsg 自定义的错误信息
	 */
	default void collectionAssertNotEmptyWithMsg(Collection<?> c, String errMsg) {
		if (c ==  null || c.isEmpty()) {
			throw newExceptionWithMsg(errMsg);
		}
	}

	/**
	 * 断言集合collection大小不为0。如果集合collection大小为0，则抛出异常
	 * @param c 待判断集合
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void collectionAssertNotEmptyWithMsg(Collection<?> c, String errMsg, Object... args) {
		if (c ==  null || c.isEmpty()) {
			throw newExceptionWithMsg(errMsg, args);
		}
	}

	/**
	 * 断言集合collection大小不为0。如果集合collection大小为0，则抛出异常
	 * @param c 待判断集合
	 * @param errMsg 自定义的错误信息
	 */
	default void collectionAssertNotEmptyWithMsg(Collection<?> c, Supplier<String> errMsg) {
		if (c ==  null || c.isEmpty()) {
			throw newExceptionWithMsg(errMsg.get());
		}
	}

	/**
	 * 断言集合collection大小不为0。如果集合collection大小为0，则抛出异常
	 * @param c 待判断集合
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void collectionAssertNotEmptyWithMsg(Collection<?> c, Supplier<String> errMsg, Object... args) {
		if (c ==  null || c.isEmpty()) {
			throw newExceptionWithMsg(errMsg.get(), args);
		}
	}

	/**
	 * 断言Map大小不为0。如果Map大小为0，则抛出异常
	 * @param map 待判断Map
	 */
	default void mapAssertNotEmpty(Map<?, ?> map) {
		if (map ==  null || map.isEmpty()) {
			throw newException();
		}
	}

	/**
	 * 断言Map大小不为0。如果Map大小为0，则抛出异常
	 * @param map 待判断Map
	 * @param args message占位符对应的参数列表
	 */
	default void mapAssertNotEmpty(Map<?, ?> map, Object... args) {
		if (map ==  null || map.isEmpty()) {
			throw newException(args);
		}
	}

	/**
	 * 断言Map大小不为0。如果Map大小为0，则抛出异常
	 * @param map 待判断Map
	 * @param errMsg 自定义的错误信息
	 */
	default void mapAssertNotEmptyWithMsg(Map<?, ?> map, String errMsg) {
		if (map ==  null || map.isEmpty()) {
			throw newExceptionWithMsg(errMsg);
		}
	}

	/**
	 * 断言Map大小不为0。如果Map大小为0，则抛出异常
	 * @param map 待判断Map
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void mapAssertNotEmptyWithMsg(Map<?, ?> map, String errMsg, Object... args) {
		if (map ==  null || map.isEmpty()) {
			throw newExceptionWithMsg(errMsg, args);
		}
	}

	/**
	 * 断言Map大小不为0。如果Map大小为0，则抛出异常
	 * @param map 待判断Map
	 * @param errMsg 自定义的错误信息
	 */
	default void mapAssertNotEmptyWithMsg(Map<?, ?> map, Supplier<String> errMsg) {
		if (map ==  null || map.isEmpty()) {
			throw newExceptionWithMsg(errMsg.get());
		}
	}

	/**
	 * 断言Map大小不为0。如果Map大小为0，则抛出异常
	 * @param map 待判断Map
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void mapAssertNotEmptyWithMsg(Map<?, ?> map, Supplier<String> errMsg, Object... args) {
		if (map ==  null || map.isEmpty()) {
			throw newExceptionWithMsg(errMsg.get(), args);
		}
	}

	/**
	 * 断言布尔值expression为false。如果布尔值expression为true，则抛出异常
	 * @param expression 待判断布尔变量
	 */
	default void booleanAssertIsFalse(boolean expression) {
		if (expression) {
			throw newException();
		}
	}

	/**
	 * 断言布尔值expression为false。如果布尔值expression为true，则抛出异常
	 * @param expression 待判断布尔变量
	 * @param args message占位符对应的参数列表
	 */
	default void booleanAssertIsFalse(boolean expression, Object... args) {
		if (expression) {
			throw newException(args);
		}
	}

	/**
	 *断言布尔值expression为false。如果布尔值expression为true，则抛出异常
	 * @param expression 待判断布尔变量
	 * @param errMsg 自定义的错误信息
	 */
	default void booleanAssertIsFalseWithMsg(boolean expression, String errMsg) {
		if (expression) {
			throw newExceptionWithMsg(errMsg);
		}
	}

	/**
	 * 断言布尔值expression为false。如果布尔值expression为true，则抛出异常
	 * @param expression 待判断布尔变量
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void booleanAssertIsFalseWithMsg(boolean expression, String errMsg, Object... args) {
		if (expression) {
			throw newExceptionWithMsg(errMsg, args);
		}
	}

	/**
	 * 断言布尔值expression为false。如果布尔值expression为true，则抛出异常
	 * @param expression 待判断布尔变量
	 * @param errMsg 自定义的错误信息
	 */
	default void booleanAssertIsFalseWithMsg(boolean expression, Supplier<String> errMsg) {
		if (expression) {
			throw newExceptionWithMsg(errMsg.get());
		}
	}

	/**
	 * 断言布尔值expression为false。如果布尔值expression为true，则抛出异常
	 * @param expression 待判断布尔变量
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void booleanAssertIsFalseWithMsg(boolean expression, Supplier<String> errMsg, Object... args) {
		if (expression) {
			throw newExceptionWithMsg(errMsg.get(), args);
		}
	}

	/**
	 * 断言布尔值expression为true。如果布尔值expression为false，则抛出异常
	 * @param expression 待判断布尔变量
	 */
	default void booleanAssertIsTrue(boolean expression) {
		if (!expression) {
			throw newException();
		}
	}

	/**
	 * 断言布尔值expression为true。如果布尔值expression为false，则抛出异常
	 * @param expression 待判断布尔变量
	 * @param args message占位符对应的参数列表
	 */
	default void booleanAssertIsTrue(boolean expression, Object... args) {
		if (!expression) {
			throw newException(args);
		}
	}

	/**
	 * 断言布尔值expression为true。如果布尔值expression为false，则抛出异常
	 * @param expression 待判断布尔变量
	 * @param errMsg 自定义的错误信息
	 */
	default void booleanAssertIsTrueWithMsg(boolean expression, String errMsg) {
		if (!expression) {
			throw newExceptionWithMsg(errMsg);
		}
	}

	/**
	 * 断言布尔值expression为true。如果布尔值expression为false，则抛出异常
	 * @param expression 待判断布尔变量
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void booleanAssertIsTrueWithMsg(boolean expression, String errMsg, Object... args) {
		if (!expression) {
			throw newExceptionWithMsg(errMsg, args);
		}
	}

	/**
	 * 断言布尔值expression为true。如果布尔值expression为false，则抛出异常
	 * @param expression 待判断布尔变量
	 * @param errMsg 自定义的错误信息
	 */
	default void booleanAssertIsTrueWithMsg(boolean expression, Supplier<String> errMsg) {
		if (!expression) {
			throw newExceptionWithMsg(errMsg.get());
		}
	}

	/**
	 * 断言布尔值expression为true。如果布尔值expression为false，则抛出异常
	 * @param expression 待判断布尔变量
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void booleanAssertIsTrueWithMsg(boolean expression, Supplier<String> errMsg, Object... args) {
		if (!expression) {
			throw newExceptionWithMsg(errMsg.get(), args);
		}
	}

	/**
	 * 断言对象obj为null。如果对象obj不为null，则抛出异常
	 * @param obj 待判断对象
	 */
	default void objAssertIsNull(Object obj) {
		if (obj != null) {
			throw newException();
		}
	}

	/**
	 * 断言对象obj为null。如果对象obj不为null，则抛出异常
	 * @param obj 待判断对象
	 * @param args message占位符对应的参数列表
	 */
	default void objAssertIsNull(Object obj, Object... args) {
		if (obj != null) {
			throw newException(args);
		}
	}

	/**
	 * 断言对象obj为null。如果对象obj不为null，则抛出异常
	 * @param obj 待判断对象
	 * @param errMsg 自定义的错误信息
	 */
	default void objAssertIsNullWithMsg(Object obj, String errMsg) {
		if (obj != null) {
			throw newExceptionWithMsg(errMsg);
		}
	}

	/**
	 * 断言对象obj为null。如果对象obj不为null，则抛出异常
	 * @param obj 待判断对象
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void objAssertIsNullWithMsg(Object obj, String errMsg, Object... args) {
		if (obj != null) {
			throw newExceptionWithMsg(errMsg, args);
		}
	}

	/**
	 * 断言对象obj为null。如果对象obj不为null，则抛出异常
	 * @param obj 待判断对象
	 * @param errMsg 自定义的错误信息
	 */
	default void objAssertIsNullWithMsg(Object obj, Supplier<String> errMsg) {
		if (obj != null) {
			throw newExceptionWithMsg(errMsg.get());
		}
	}

	/**
	 * 断言对象obj为null。如果对象obj不为null，则抛出异常
	 * @param obj 待判断对象
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void objAssertIsNullWithMsg(Object obj, Supplier<String> errMsg, Object... args) {
		if (obj != null) {
			throw newExceptionWithMsg(errMsg.get(), args);
		}
	}

	/**
	 * 断言对象o1与对象o2相等，此处的相等指（o1.equals(o2)为true,如果两对象不相等，则抛出异常
	 * @param o1 待判断对象，若o1为null，也当作不相等处理
	 * @param o2  待判断对象
	 */
	default void assertEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return;
		}
		if (o1 == null || !o1.equals(o2)) {
			throw newException();
		}
	}

	/**
	 * 断言对象o1与对象o2相等，此处的相等指（o1.equals(o2)为true,如果两对象不相等，则抛出异常
	 * @param o1 待判断对象，若o1为null，也当作不相等处理
	 * @param o2  待判断对象
	 * @param args message占位符对应的参数列表
	 */
	default void assertEquals(Object o1, Object o2, Object... args) {
		if (o1 == o2) {
			return;
		}
		if (o1 == null || !o1.equals(o2)) {
			throw newException(args);
		}
	}

	/**
	 断言对象o1与对象o2相等，此处的相等指（o1.equals(o2)为true,如果两对象不相等，则抛出异常
	 * @param o1 待判断对象，若o1为null，也当作不相等处理
	 * @param o2  待判断对象
	 * @param errMsg 自定义的错误信息
	 */
	default void assertEqualsWithMsg(Object o1, Object o2, String errMsg) {
		if (o1 == o2) {
			return;
		}
		if (o1 == null || !o1.equals(o2)) {
			throw newExceptionWithMsg(errMsg);
		}
	}

	/**
	 * 断言对象o1与对象o2相等，此处的相等指（o1.equals(o2)为true,如果两对象不相等，则抛出异常
	 * @param o1 待判断对象，若o1为null，也当作不相等处理
	 * @param o2  待判断对象
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void assertEqualsWithMsg(Object o1, Object o2, String errMsg, Object... args) {
		if (o1 == o2) {
			return;
		}
		if (o1 == null || !o1.equals(o2)) {
			throw newExceptionWithMsg(errMsg, args);
		}
	}

	/**
	 * 断言对象o1与对象o2相等，此处的相等指（o1.equals(o2)为true,如果两对象不相等，则抛出异常
	 * @param o1 待判断对象，若o1为null，也当作不相等处理
	 * @param o2  待判断对象
	 * @param errMsg 自定义的错误信息
	 */
	default void assertEqualsWithMsg(Object o1, Object o2, Supplier<String> errMsg) {
		if (o1 == o2) {
			return;
		}
		if (o1 == null || !o1.equals(o2)) {
			throw newExceptionWithMsg(errMsg.get());
		}
	}

	/**
	 * 断言对象o1与对象o2相等，此处的相等指（o1.equals(o2)为true,如果两对象不相等，则抛出异常
	 * @param o1 待判断对象，若o1为null，也当作不相等处理
	 * @param o2  待判断对象
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void assertEqualsWithMsg(Object o1, Object o2, Supplier<String> errMsg, Object... args) {
		if (o1 == o2) {
			return;
		}
		if (o1 == null || !o1.equals(o2)) {
			throw newExceptionWithMsg(errMsg.get(), args);
		}
	}

	/**
	 * 直接抛出异常
	 */
	default void assertFail() {
		throw newException();
	}

	/**
	 * 直接抛出异常
	 * @param args message占位符对应的参数列表
	 */
	default void assertFail(Object... args) {
		throw newException(args);
	}

	/**
	 * 直接抛出异常，并包含原异常信息
	 * 当捕获非运行时异常（非继承{@link RuntimeException}）时，并该异常进行业务描述时，
	 * 必须传递原始异常，作为新异常的cause
	 * @param t 原始异常
	 */
	default void assertFail(Throwable t) {
		throw newException(t);
	}

	/**
	 * 直接抛出异常，并包含原异常信息
	 * 当捕获非运行时异常（非继承{@link RuntimeException}）时，并该异常进行业务描述时，
	 * 必须传递原始异常，作为新异常的cause
	 *
	 * @param t 原始异常
	 * @param args message占位符对应的参数列表
	 */
	default void assertFail(Throwable t, Object... args) {
		throw newException(t, args);
	}

	/**
	 * 直接抛出异常
	 * @param errMsg 自定义的错误信息
	 */
	default void assertFailWithMsg(String errMsg) {
		throw newExceptionWithMsg(errMsg);
	}

	/**
	 * 直接抛出异常
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void assertFailWithMsg(String errMsg, Object... args) {
		throw newExceptionWithMsg(errMsg, args);
	}

	/**
	 * 直接抛出异常，并包含原异常信息
	 * 当捕获非运行时异常（非继承{@link RuntimeException}）时，并该异常进行业务描述时，
	 * 必须传递原始异常，作为新异常的cause
	 * @param errMsg 自定义的错误信息
	 * @param t 原始异常
	 */
	default void assertFailWithMsg(String errMsg, Throwable t) {
		throw newExceptionWithMsg(errMsg, t);
	}

	/**
	 * 直接抛出异常，并包含原异常信息
	 * 当捕获非运行时异常（非继承{@link RuntimeException}）时，并该异常进行业务描述时，
	 * 必须传递原始异常，作为新异常的cause
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param t 原始异常
	 * @param args message占位符对应的参数列表
	 */
	default void assertFailWithMsg(String errMsg, Throwable t, Object... args) {
		throw newExceptionWithMsg(errMsg, t, args);
	}

	/**
	 * 直接抛出异常
	 * @param errMsg 自定义的错误信息
	 */
	default void assertFailWithMsg(Supplier<String> errMsg) {
		throw newExceptionWithMsg(errMsg.get());
	}

	/**
	 * 直接抛出异常
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param args message占位符对应的参数列表
	 */
	default void assertFailWithMsg(Supplier<String> errMsg, Object... args) {
		throw newExceptionWithMsg(errMsg.get(), args);
	}

	/**
	 * 直接抛出异常，并包含原异常信息
	 * 当捕获非运行时异常（非继承{@link RuntimeException}）时，并该异常进行业务描述时，
	 * 必须传递原始异常，作为新异常的cause
	 * @param errMsg 自定义的错误信息
	 * @param t 原始异常
	 */
	default void assertFailWithMsg(Supplier<String> errMsg, Throwable t) {
		throw newExceptionWithMsg(errMsg.get(), t);
	}

	/**
	 * 直接抛出异常，并包含原异常信息
	 * 当捕获非运行时异常（非继承{@link RuntimeException}）时，并该异常进行业务描述时，
	 * 必须传递原始异常，作为新异常的cause
	 * @param errMsg 自定义的错误信息. 支持 {index} 形式的占位符, 比如: errMsg-用户[{0}]不存在, args-1001, 最后打印-用户[1001]不存在
	 * @param t 原始异常
	 * @param args message占位符对应的参数列表
	 */
	default void assertFailWithMsg(Supplier<String> errMsg, Throwable t, Object... args) {
		throw newExceptionWithMsg(errMsg.get(), t, args);
	}

}
