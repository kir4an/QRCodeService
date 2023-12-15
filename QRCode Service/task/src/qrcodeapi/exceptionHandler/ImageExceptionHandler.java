package qrcodeapi.exceptionHandler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import qrcodeapi.exception.ImageException;
import qrcodeapi.exception.IncorrectValueException;
import qrcodeapi.exception.NoContentsException;
import qrcodeapi.exception.TypeException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ImageExceptionHandler {
    @ExceptionHandler(ImageException.class)
    public ResponseEntity<Map<String, String>> handleImageException(ImageException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Image size must be between 150 and 350 pixels");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(TypeException.class)
    public ResponseEntity<Map<String, String>> handleImageException(TypeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Only png, jpeg and gif image types are supported");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleImageException(ConstraintViolationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Contents cannot be null or blank");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(IncorrectValueException.class)
    public ResponseEntity<Map<String, String>> handleImageException(IncorrectValueException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Permitted error correction levels are L, M, Q, H");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}