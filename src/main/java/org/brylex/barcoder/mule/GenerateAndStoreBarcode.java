package org.brylex.barcoder.mule;

import org.brylex.barcoder.Barcode;
import org.brylex.barcoder.generator.BarcodeGenerator;
import org.brylex.barcoder.repository.BarcodeRepository;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class GenerateAndStoreBarcode implements Callable
{
    private final BarcodeGenerator generator;
    private final BarcodeRepository repository;

    @Autowired
    public GenerateAndStoreBarcode(BarcodeGenerator generator, BarcodeRepository repository)
    {
        this.generator = generator;
        this.repository = repository;
    }

    public Object onCall(MuleEventContext eventContext) throws Exception
    {
        MuleMessage message = eventContext.getMessage();
        URL url = (URL) message.getPayload();

        byte[] bytes = generator.generate(url, 647);
        Barcode barcode = new Barcode(url, bytes);

        repository.save(barcode);

        return null;
    }

}
