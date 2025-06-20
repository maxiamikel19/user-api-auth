package com.maxiamikel.userAuthApi.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StandardError {
    private Integer status;
    private String message;
}
