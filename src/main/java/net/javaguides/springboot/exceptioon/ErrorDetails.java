package net.javaguides.springboot.exceptioon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private String timeStamp;
    private String message;
    private String path;
    private String errorCode;
}
