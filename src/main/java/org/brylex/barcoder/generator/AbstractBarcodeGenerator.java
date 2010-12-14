package org.brylex.barcoder.generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.io.IOUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;

abstract class AbstractBarcodeGenerator implements BarcodeGenerator
{
    private static final String MIME_TYPE = "image/x-png";

    private final Writer writer;

    private final BarcodeFormat format;

    public AbstractBarcodeGenerator(final BarcodeFormat format, final Writer writer)
    {
        this.format = format;
        this.writer = writer;
    }

    public byte[] generate(URL url, int size)
    {
        BufferedImage image = createBufferedImage(url, size);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageOutputStream os = null;
        ImageWriter writer = getImageWriter();
        try
        {
            os = ImageIO.createImageOutputStream(baos);
            writer.setOutput(os);
            writer.write(new IIOImage(image, null, null));
            writer.dispose();

        } catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("Unable to write image to ByteArrayOutputStream.", e);
        } finally
        {
            closeQuietly(os);
            IOUtils.closeQuietly(baos);
        }

        return baos.toByteArray();
    }

    private void closeQuietly(ImageOutputStream os)
    {
        try
        {
            os.close();
        } catch (IOException e)
        {
            // ignored
        }
    }

    private BufferedImage createBufferedImage(URL url, int size)
    {
        BitMatrix matrix = createBitMatrix(url, size);

        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, size, size);

        graphics.setColor(Color.BLACK);
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (matrix.get(i, j))
                {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        return image;
    }

    private ImageWriter getImageWriter()
    {
        Iterator<ImageWriter> iterator = ImageIO.getImageWritersByMIMEType(MIME_TYPE);
        if (!iterator.hasNext())
        {
            throw new IllegalStateException("Didn't find any ImageWriter for mime-type '" + MIME_TYPE + "'.");
        }

        return iterator.next();
    }

    private BitMatrix createBitMatrix(URL url, int size)
    {
        try
        {
            return writer.encode(url.toString(), format, size, size, createHints());
        } catch (WriterException e)
        {
            throw new RuntimeException("Unable to create bit matrix for URL '" + url + "'.", e);
        }
    }

    private Hashtable<EncodeHintType, ErrorCorrectionLevel> createHints()
    {
        Hashtable<EncodeHintType, ErrorCorrectionLevel> map = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        return map;
    }

}
