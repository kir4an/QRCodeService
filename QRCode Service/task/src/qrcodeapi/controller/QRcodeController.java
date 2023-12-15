package qrcodeapi.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qrcodeapi.Validation.NotEmpty;
import qrcodeapi.exception.ImageException;
import qrcodeapi.exception.NoContentsException;
import qrcodeapi.exception.TypeException;
import qrcodeapi.service.ImageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@Validated
public class QRcodeController {
    private final ImageService imageService;

    public QRcodeController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/api/health")
    public ResponseEntity<?> getInformation() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/api/qrcode")
    public ResponseEntity<byte[]> getImage(@NotBlank @RequestParam String contents,
                                            @RequestParam(defaultValue = "png") String type,
                                            @RequestParam(defaultValue = "L") String correction,
                                            @RequestParam(defaultValue = "250")int size) throws IOException, WriterException {

        imageService.validSize(size);
        Map<EncodeHintType, ?> hints = imageService.generateHintsMap(correction);
        imageService.validType(type);

        BufferedImage qrCodeImage = imageService.generateQRCode(contents, size,hints);
        byte[] bytes = imageService.convertImageToBytes(qrCodeImage, type);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("image/" + type))
                .body(bytes);
    }

}

