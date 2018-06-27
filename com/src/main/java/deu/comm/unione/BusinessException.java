package deu.comm.unione;

import java.util.Locale;

import org.springframework.context.MessageSource;

import egovframework.rte.fdl.cmmn.exception.BaseException;


public class BusinessException extends BaseException {
    private static final long serialVersionUID = 1L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Exception cause) {
        super(message, cause);
    }

    public BusinessException(MessageSource messageSource, String messageKey) {
        this(messageSource, messageKey, null, null);
    }

    public BusinessException(MessageSource messageSource, String messageKey, Object[] messageParameters) {
        this(messageSource, messageKey, messageParameters, null);
    }

    public BusinessException(MessageSource messageSource, String messageKey, Exception cause) {
        this(messageSource, messageKey, null, cause);
    }

    public BusinessException(MessageSource messageSource, String messageKey,
            Object[] messageParameters, Exception cause) {
        this(messageSource, messageKey, messageParameters, Locale.getDefault(), cause);
    }

    public BusinessException(MessageSource messageSource, String messageKey,
            Object[] messageParameters, Locale locale, Exception cause) {
        super(messageSource, messageKey, messageParameters, locale, cause);
    }
}
