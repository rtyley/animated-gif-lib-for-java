package com.madgag.gif.fmsware;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import static com.google.common.io.ByteStreams.toByteArray;
import static org.fest.assertions.api.Assertions.assertThat;

public class TestGifDecoder {

    @Test
    public void testDecodingGifWithDeferredClearCodesInLZWCompression() throws Exception {
        GifDecoder decoder = new GifDecoder();
        decoder.read(getClass().getResourceAsStream("/brucelee.gif"));
        BufferedImage image = decoder.getImage();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "gif", outputStream);
        byte[] actualBytes = outputStream.toByteArray();
        byte[] expectedBytes = toByteArray(getClass().getResourceAsStream("/brucelee-frame.gif"));
        assertThat(actualBytes).isEqualTo(expectedBytes);
    }
}
