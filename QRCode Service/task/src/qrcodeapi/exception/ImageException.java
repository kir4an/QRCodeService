package qrcodeapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ImageException extends RuntimeException {
    public ImageException() {
        super();
    }
}