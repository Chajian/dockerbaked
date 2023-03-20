package com.ibs.dockerbacked.execption;

import com.ibs.dockerbacked.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 全局处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExecptionHandler {

    @ExceptionHandler(CustomExpection.class)
    public Result<String> customException(CustomExpection customExpection) {

        return Result.error(customExpection.getCode(),customExpection.getMessage());

    }


}
