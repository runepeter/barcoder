package org.brylex.barcoder.test.functional;

import org.brylex.barcoder.repository.BarcodeRepository;
import org.brylex.barcoder.service.BarcodeService;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.api.MuleContext;
import org.mule.api.client.LocalMuleClient;
import org.mule.api.registry.MuleRegistry;
import org.mule.management.stats.AllStatistics;
import org.mule.management.stats.ServiceStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(locations = "classpath:spring-context.xml")
public class BarcoderFunctionalTest extends AbstractJUnit4SpringContextTests
{
    @Autowired
    private BarcodeService service;

    @Autowired
    private BarcodeRepository repository;

    @Autowired
    private MuleContext muleContext;

    @Ignore
    @Test
    public void createBarcode() throws Exception
    {
        URL url = new URL("http://www.mulesource.org");

        service.create(url);

        Thread.sleep(3000); // wait for messages to process.

        assertTrue(muleContext.isStarted());
        assertNotNull(repository.getByUrl(url));
    }

}
