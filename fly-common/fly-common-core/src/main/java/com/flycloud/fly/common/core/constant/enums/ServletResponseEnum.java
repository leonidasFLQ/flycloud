package com.flycloud.fly.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

/**
 * 异常类与http status对照映射关系
 * @author fly
 * @date 2022/2/25
 **/

@Getter
@AllArgsConstructor
public enum ServletResponseEnum implements IResponseCodeEnum{

	MethodArgumentNotValidException(-400, "", HttpServletResponse.SC_BAD_REQUEST),
	MethodArgumentTypeMismatchException(-400, "", HttpServletResponse.SC_BAD_REQUEST),
	MissingServletRequestPartException(-400, "", HttpServletResponse.SC_BAD_REQUEST),
	MissingPathVariableException(-400, "", HttpServletResponse.SC_BAD_REQUEST),
	BindException(-400, "", HttpServletResponse.SC_BAD_REQUEST),
	MissingServletRequestParameterException(-400, "", HttpServletResponse.SC_BAD_REQUEST),
	TypeMismatchException(-400, "", HttpServletResponse.SC_BAD_REQUEST),
	ServletRequestBindingException(-400, "", HttpServletResponse.SC_BAD_REQUEST),
	HttpMessageNotReadableException(-400, "", HttpServletResponse.SC_BAD_REQUEST),
	NoHandlerFoundException(-404, "", HttpServletResponse.SC_NOT_FOUND),
	NoSuchRequestHandlingMethodException(-404, "", HttpServletResponse.SC_NOT_FOUND),
	HttpRequestMethodNotSupportedException(-405, "", HttpServletResponse.SC_METHOD_NOT_ALLOWED),
	HttpMediaTypeNotAcceptableException(-406, "", HttpServletResponse.SC_NOT_ACCEPTABLE),
	HttpMediaTypeNotSupportedException(-415, "", HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE),
	ConversionNotSupportedException(-500, "", HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
	HttpMessageNotWritableException(-500, "", HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
	AsyncRequestTimeoutException(-503, "", HttpServletResponse.SC_SERVICE_UNAVAILABLE)

	;

	/**
	 * 返回码，目前与{@link #statusCode}相同
	 */
	private int code;
	/**
	 * 返回信息，直接读取异常的message
	 */
	private String msg;
	/**
	 * HTTP状态码
	 */
	private int statusCode;
}
