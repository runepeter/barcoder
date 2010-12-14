package org.brylex.barcoder.generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

@Component
public class QrBarcodeGenerator extends AbstractBarcodeGenerator
{
    public QrBarcodeGenerator()
    {
        super(BarcodeFormat.QR_CODE, new QRCodeWriter());
    }

}
