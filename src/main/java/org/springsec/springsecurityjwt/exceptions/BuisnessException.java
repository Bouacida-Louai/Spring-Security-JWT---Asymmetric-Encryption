package org.springsec.springsecurityjwt.exceptions;

import ch.qos.logback.core.spi.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class BuisnessException  extends RuntimeException{

    private final ErrorCode errorCode;
    private final Object[] args;

    public BuisnessException(final ErrorCode errorCode ,final Object... args){
        super(getFormatterMessage(errorCode,args));
        this.errorCode=errorCode;
        this.args=args;
    }

    private static String getFormatterMessage(ErrorCode errorCode, Object[] args) {
        if (args == null || args.length == 0) {
            return String.format(errorCode.getDefaultMessaege(),args);
        }
        return errorCode.getDefaultMessaege();
    }
}
