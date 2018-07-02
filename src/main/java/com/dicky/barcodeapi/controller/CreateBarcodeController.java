package com.dicky.barcodeapi.controller;


import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@RestController()
@RequestMapping("/api/barcode")
public class CreateBarcodeController {

    @GetMapping("/{value}")
    public ResponseEntity<String> generateBarcode(@PathVariable String value, HttpServletResponse response){
        try {
//            response.setContentType("image/jpg");
//            Code128Bean code128 = new Code128Bean();
//            code128.setHeight(15f);
//            code128.setModuleWidth(0.3);
//            code128.setQuietZone(10);
//            code128.doQuietZone(true);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            BitmapCanvasProvider canvas = new BitmapCanvasProvider(baos, "image/x-png", 300, BufferedImage.TYPE_BYTE_BINARY, false, 0);
//            code128.generateBarcode(canvas, value);
//            canvas.finish();
//            ServletOutputStream responseOutputStream = response.getOutputStream();
//            responseOutputStream.write(baos.toByteArray());
//            responseOutputStream.flush();
//            responseOutputStream.close();

            ByteArrayOutputStream out = QRCode.from(value)
                    .to(ImageType.PNG).stream();
            response.setContentType("image/png");
            response.setContentLength(out.size());
            OutputStream outStream = response.getOutputStream();

            outStream.write(out.toByteArray());

            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<String>(value, HttpStatus.OK);
    }

}
