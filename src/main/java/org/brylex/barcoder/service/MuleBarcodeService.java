package org.brylex.barcoder.service;

import org.mule.api.MuleException;
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
            client.dispatch("vm://create", "JALLA, BALLA", Collections.<String, Object>emptyMap());
        } catch (MuleException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
