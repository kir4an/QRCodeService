package qrcodeapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


public class TypeException extends RuntimeException{
    public TypeException(){
        super();
    }
}
