package qrcodeapi.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;
import qrcodeapi.exception.ImageException;
import qrcodeapi.exception.IncorrectValueException;
import qrcodeapi.exception.TypeException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class ImageService {
    private final int width = 250;
    private final int height = 250;
    private BufferedImage image;
    public BufferedImage generateImage(int size,String type){
        BufferedImage image;
        if (type.equalsIgnoreCase("PNG")) {
            image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        }else if (type.equalsIgnoreCase("JPEG")) {
            image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        } else {
            image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        }
        Graphics2D g = image.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, size, size);
        return image;
    }
    public void validType(String type){
        if(!(type.equals("png") || type.equals("jpeg") || type.equals("gif"))){
            throw new TypeException();
        }
    }

    public void validSize(int size){
        if (size < 150 || size > 350) {
            throw new ImageException();
        }
    }
    public Map<EncodeHintType, ?> generateHintsMap(String correction){
        if(correction.equalsIgnoreCase("H")){
            Map<EncodeHintType, ?> hints = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            return hints;
        }else if(correction.equalsIgnoreCase("L")){
            Map<EncodeHintType, ?> hints = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            return hints;
        }else if(correction.equalsIgnoreCase("M")) {
            Map<EncodeHintType, ?> hints = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            return hints;
        }else if(correction.equalsIgnoreCase("Q")) {
            Map<EncodeHintType, ?> hints = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
            return hints;
        }else{
            throw new IncorrectValueException();
        }
    }

    public BufferedImage generateQRCode(String contents, int size,Map hints) throws WriterException {
        //Map<EncodeHintType, ?> hints = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, size, size,hints);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return bufferedImage;
    }
    public byte[] convertImageToBytes(BufferedImage bufferedImage, String type) throws IOException {
        try (var baos = new ByteArrayOutputStream()) {
            type = type.toLowerCase();
            ImageIO.write(bufferedImage, type, baos);
            return baos.toByteArray();
        }
    }
}
