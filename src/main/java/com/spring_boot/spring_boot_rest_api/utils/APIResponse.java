package com.spring_boot.spring_boot_rest_api.utils;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse<T> {
    private String status;
    private String message;
    private T data;
    private List<ErrorDetails> errors;
}
