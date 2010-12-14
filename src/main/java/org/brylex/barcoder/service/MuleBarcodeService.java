package org.brylex.barcoder.service;

import org.mule.api.client.LocalMuleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Collections;

@Service
public class MuleBarcodeService implements BarcodeService
{
    @Autowired
    private LocalMuleClient client;

    public void create(URL url)
    {
        try
        {
            client.dispatch("vm://create", url, Collections.<String, Object>emptyMap());
        } catch (Exception e)
        {
            throw new RuntimeException("Unable to create barcode for URL '" + url + "'.", e);
        }
    }
}
