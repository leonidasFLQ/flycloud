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
 * ?????????????????????
 * @author fly
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
	 * ????????????????????????
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
	 * ??????Controller??????????????????
	 * @param e ??????
	 * @return ????????????
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
			// ??????????????????, ????????????????????????????????????????????????, ??????404.???????????????????????????
			code = CommonResponseEnum.SERVER_ERROR.getCode();
			BaseException baseException = new BaseException(CommonResponseEnum.SERVER_ERROR);
			String message = getUnifiedMessage(baseException);
			return R.exception(code, message);
		}
		return R.exception(code, e.getMessage());
	}

	/**
	 * ????????????????????????
	 * @param bindingResult ????????????
	 * @return ????????????
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
	 * ??????????????????
	 * @param e ??????
	 * @return ????????????
	 */
	@ExceptionHandler(value = BindException.class)
	@ResponseBody
	public R<CommonResponseEnum> handleBindException(BindException e) {
		log.error("??????????????????", e);
		return wrapperBindingResult(e.getBindingResult());
	}

	/**
	 * ????????????(Valid)??????????????????????????????????????????????????????????????????
	 * @param e ??????
	 * @return ????????????
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseBody
	public R<CommonResponseEnum> handleValidException(MethodArgumentNotValidException e) {
		log.error("??????????????????", e);
		return wrapperBindingResult(e.getBindingResult());
	}

	/**
	 * ???????????????
	 * @param e ??????
	 * @return ????????????
	 */
	@ExceptionHandler(value = BaseException.class)
	@ResponseBody
	public R<CommonResponseEnum> handleBaseException(BaseException e) {
		log.error(e.getMessage(), e);
		return R.exception(e.getResponseCodeEnum().getCode(), getUnifiedMessage(e));
	}

	/**
	 * ????????????
	 * @param e ??????
	 * @return ????????????
	 */
	@ExceptionHandler(value = BusinessException.class)
	@ResponseBody
	public R<CommonResponseEnum> handleBusinessException(BaseException e) {
		//log.error(e.getMessage(), e);
		return R.exception(e.getResponseCodeEnum().getCode(), getUnifiedMessage(e));
	}

	/**
	 * ???????????????
	 * @param e ??????
	 * @return ????????????
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public R<CommonResponseEnum> handleException(Exception e) {
		 log.error(e.getMessage(), e);

		if (CommonConstants.ENV_PROD.equals(profile)) {
			// ??????????????????, ????????????????????????????????????????????????, ???????????????????????????.???????????????????????????
			int code = CommonResponseEnum.SERVER_ERROR.getCode();
			BaseException baseException = new BaseException(CommonResponseEnum.SERVER_ERROR);
			String message = getUnifiedMessage(baseException);
			return R.exception(code, message);
		}

		return R.exception(CommonResponseEnum.SERVER_ERROR.getCode(), e.getMessage());
	}

}
