package org.brylex.barcoder.test.functional;

import org.brylex.barcoder.repository.BarcodeRepository;
import org.brylex.barcoder.service.BarcodeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.net.URL;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(locations = "classpath:spring-context.xml")
public class BarcoderFunctionalTest extends AbstractJUnit4SpringContextTests
{
    @Autowired
    private BarcodeService service;

    @Autowired
    private BarcodeRepository repository;

    @Test
    public void createBarcode() throws Exception
    {
        URL url = new URL("http://www.github.org/runepeter");

        service.create(url);

        Thread.sleep(3000); // wait for messages to process.

        assertNotNull(repository.getByUrl(url));
    }

}
