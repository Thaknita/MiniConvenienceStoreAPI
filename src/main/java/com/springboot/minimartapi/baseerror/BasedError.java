package com.springboot.minimartapi.baseerror;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasedError<T> {
    private String message;
    private LocalDateTime timestamp;
    private Integer code;
    private  T error;
}
