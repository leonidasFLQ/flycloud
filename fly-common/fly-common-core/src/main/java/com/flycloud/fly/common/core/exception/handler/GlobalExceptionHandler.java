package com.flycloud.fly.common.core.exception.handler;

import com.flycloud.fly.common.core.constant.CommonConstants;
import com.flycloud.fly.common.core.constant.enums.ArgumentResponseEnum;
import com.flycloud.fly.common.core.constant.enums.CommonResponseEnum;
import com.flycloud.fly.common.core.constant.enums.ServletResponseEnum;
import com.flycloud.fly.common.core.exception.BaseException;
import com.flycloud.fly.common.core.exception.BusinessException;
import com.flycloud.fly.common.core.exception.il8n.UnifiedMessageSource;
import com.flycloud.fly.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理器
 * @author fy
 * @date 2022/2/25
 **/

@Component
@ControllerAdvice
@ConditionalOnWebApplication
@ConditionalOnMissingBean({GlobalExceptionHandler.class})
@Slf4j
public class GlobalExceptionHandler {

	@Autowired
	private UnifiedMessageSource unifiedMessageSource;

	/**
	 * 获取当前系统环境
	 */
	@Value("${spring.profiles.active}")
	private String profile;


	public String getUnifiedMessage(BaseException e)
	{
		String code = "response." + e.getResponseCodeEnum().toString();
		String message = this.unifiedMessageSource.getMessage(code, e.getMsgArgs());
		if ((message == null) || (message.isEmpty())) {
			return e.getMessage();
		}
		return message;
	}


	/**
	 * 进入Controller前的相关异常
	 * @param e 异常
	 * @return 异常结果
	 */
	@ExceptionHandler({
			NoHandlerFoundException.class,
			HttpRequestMethodNotSupportedException.class,
			HttpMediaTypeNotSupportedException.class,
			HttpMediaTypeNotAcceptableException.class,
			MissingPathVariableException.class,
			MissingServletRequestParameterException.class,
			TypeMismatchException.class,
			HttpMessageNotReadableException.class,
			HttpMessageNotWritableException.class,
			BindException.class,
			MethodArgumentNotValidException.class,
			ServletRequestBindingException.class,
			ConversionNotSupportedException.class,
			MissingServletRequestPartException.class,
			AsyncRequestTimeoutException.class
	})
	@ResponseBody
	public R<CommonResponseEnum> handleServletException(Exception e) {
		log.error(e.getMessage(), e);
		int code = CommonResponseEnum.SERVER_ERROR.getCode();
		try {
			ServletResponseEnum servletExceptionEnum = ServletResponseEnum.valueOf(e.getClass().getSimpleName());
			code = servletExceptionEnum.getCode();
		} catch (IllegalArgumentException e1) {
			log.error("class [{}] not defined in enum {}", e.getClass().getName(), ServletResponseEnum.class.getName());
		}

		if (CommonConstants.ENV_PROD.equals(profile)) {
			// 当为生产环境, 不适合把具体的异常信息展示给用户, 比如404.在这里做国际化处理
			code = CommonResponseEnum.SERVER_ERROR.getCode();
			BaseException baseException = new BaseException(CommonResponseEnum.SERVER_ERROR);
			String message = getUnifiedMessage(baseException);
			return R.exception(code, message);
		}
		return R.exception(code, e.getMessage());
	}

	/**
	 * 包装绑定异常结果
	 * @param bindingResult 绑定结果
	 * @return 异常结果
	 */
	private R<CommonResponseEnum> wrapperBindingResult(BindingResult bindingResult) {
		StringBuilder msg = new StringBuilder();

		for (ObjectError error : bindingResult.getAllErrors()) {
			msg.append(", ");
			if (error instanceof FieldError) {
				msg.append(((FieldError) error).getField()).append(": ");
			}
			msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());

		}
		return R.exception(ArgumentResponseEnum.VALID_ERROR.getCode(), msg.substring(2));
	}

	/**
	 * 参数绑定异常
	 * @param e 异常
	 * @return 异常结果
	 */
	@ExceptionHandler(value = BindException.class)
	@ResponseBody
	public R<CommonResponseEnum> handleBindException(BindException e) {
		log.error("参数绑定异常", e);
		return wrapperBindingResult(e.getBindingResult());
	}

	/**
	 * 参数校验(Valid)异常，将校验失败的所有异常组合成一条错误信息
	 * @param e 异常
	 * @return 异常结果
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseBody
	public R<CommonResponseEnum> handleValidException(MethodArgumentNotValidException e) {
		log.error("参数校验异常", e);
		return wrapperBindingResult(e.getBindingResult());
	}

	/**
	 * 自定义异常
	 * @param e 异常
	 * @return 异常结果
	 */
	@ExceptionHandler(value = BaseException.class)
	@ResponseBody
	public R<CommonResponseEnum> handleBaseException(BaseException e) {
		log.error(e.getMessage(), e);
		return R.exception(e.getResponseCodeEnum().getCode(), getUnifiedMessage(e));
	}

	/**
	 * 业务异常
	 * @param e 异常
	 * @return 异常结果
	 */
	@ExceptionHandler(value = BusinessException.class)
	@ResponseBody
	public R<CommonResponseEnum> handleBusinessException(BaseException e) {
		//log.error(e.getMessage(), e);
		return R.exception(e.getResponseCodeEnum().getCode(), getUnifiedMessage(e));
	}

	/**
	 * 未定义异常
	 * @param e 异常
	 * @return 异常结果
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public R<CommonResponseEnum> handleException(Exception e) {
		 log.error(e.getMessage(), e);

		if (CommonConstants.ENV_PROD.equals(profile)) {
			// 当为生产环境, 不适合把具体的异常信息展示给用户, 比如数据库异常信息.在这里做国际化处理
			int code = CommonResponseEnum.SERVER_ERROR.getCode();
			BaseException baseException = new BaseException(CommonResponseEnum.SERVER_ERROR);
			String message = getUnifiedMessage(baseException);
			return R.exception(code, message);
		}

		return R.exception(CommonResponseEnum.SERVER_ERROR.getCode(), e.getMessage());
	}

}
