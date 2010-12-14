package org.brylex.barcoder.repository;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.brylex.barcoder.Barcode;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

@Repository
public class BarcodeRepository
{
    private final File repositoryDir = new File("target/barcodes");

    public BarcodeRepository()
    {
        if (!repositoryDir.exists() && !repositoryDir.mkdirs())
        {
            throw new IllegalStateException("Unable to create required repository root directory '" + repositoryDir + "'.");
        }
    }

    public Barcode getByUrl(final URL url)
    {
        File file = new File(repositoryDir, url.toExternalForm() + ".png");
        if (file.exists())
        {
            FileReader reader = null;
            try
            {
                reader = new FileReader(file);
                return new Barcode(url, IOUtils.toByteArray(reader));

            } catch (IOException e)
            {
                throw new RuntimeException("Unable to read barcode from file '" + file + "'.", e);
            } finally
            {
                IOUtils.closeQuietly(reader);
            }
        }

        return null;
    }

    public void save(final Barcode barcode)
    {
        File file = new File(repositoryDir, barcode.getUrl().toExternalForm() + ".png");

        FileWriter writer = null;
        try
        {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            writer = new FileWriter(file);
            IOUtils.write(barcode.getBytes(), writer);

        } catch (IOException e)
        {
            throw new RuntimeException("Unable to write barcode to file '" + file + "'.", e);
        } finally
        {
            IOUtils.closeQuietly(writer);
        }

    }
}
