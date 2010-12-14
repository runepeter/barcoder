package org.brylex.barcoder.generator;

import java.io.OutputStream;
import java.net.URL;

public interface BarcodeGenerator
{
    byte [] generate(URL url, int size);
}
