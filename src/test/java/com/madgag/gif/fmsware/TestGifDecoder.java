package com.madgag.gif.fmsware;

import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;
import sun.misc.IOUtils;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class TestGifDecoder {

    @Test
    public void testDecodingGifWithDeferredClearCodesInLZWCompression() throws Exception {
        GifDecoder decoder = new GifDecoder();
        decoder.read(getClass().getResourceAsStream("/brucelee.gif"));
        BufferedImage image = decoder.getImage();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "gif", outputStream);
        byte[] actualBytes = outputStream.toByteArray();
        byte[] expectedBytes = IOUtils.readFully(getClass().getResourceAsStream("/brucelee-frame.gif"), -1, true);
        assertThat(actualBytes).isEqualTo(expectedBytes);
    }
}
