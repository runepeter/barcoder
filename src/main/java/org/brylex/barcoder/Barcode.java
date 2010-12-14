package org.brylex.barcoder;

import java.net.URL;

public class Barcode
{
    private final URL url;
    private final byte [] bytes;

    public Barcode(final URL url, final byte[] bytes)
    {
        this.bytes = bytes;
        this.url = url;
    }

    public URL getUrl()
    {
        return url;
    }

    public byte[] getBytes()
    {
        return bytes;
    }
}
