package com.madgag.gif.fmsware;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class TestAnimatedGifEncoder {

  private ByteArrayOutputStream outputStream;
  private AnimatedGifEncoder encoder;
  private BufferedImage sonic1;
  private BufferedImage sonic2;

  @Before
  public void setUp() throws IOException {
    sonic1 = getImage("/sonic1.png");
    sonic2 = getImage("/sonic2.png");

    outputStream = new ByteArrayOutputStream();
    encoder = new AnimatedGifEncoder();
  }

  @Test
  public void testBasicOutput() throws Exception {
    encoder.start(outputStream);
    encoder.setRepeat(0);
    encoder.setDelay(400);
    encoder.addFrame(sonic1);
    encoder.addFrame(sonic2);
    encoder.finish();

    byte[] expectedBytes = getExpectedBytes("/sonic-normal.gif");
    byte[] actualBytes = outputStream.toByteArray();

    assertThat(actualBytes.length).isEqualTo(expectedBytes.length);
    assertThat(actualBytes).isEqualTo(expectedBytes);
  }

  @Test
  public void testNullBackgroundWorks() throws Exception {
    encoder.start(outputStream);
    encoder.setRepeat(0);
    encoder.setDelay(400);
    encoder.setTransparent(null);
    encoder.addFrame(sonic1);
    encoder.addFrame(sonic2);
    encoder.finish();

    byte[] expectedBytes = getExpectedBytes("/sonic-normal.gif");
    byte[] actualBytes = outputStream.toByteArray();

    assertThat(actualBytes.length).isEqualTo(expectedBytes.length);
    assertThat(actualBytes).isEqualTo(expectedBytes);
  }

  @Test
  public void testBackgroundColorWorksOnOversizeImage() throws Exception {
    encoder.start(outputStream);
    encoder.setRepeat(0);
    encoder.setDelay(400);
    encoder.setSize(600, 600);
    encoder.setBackground(Color.RED);
    encoder.addFrame(sonic1);
    encoder.addFrame(sonic2);
    encoder.finish();

    byte[] expectedBytes = getExpectedBytes("/sonic-big-and-red.gif");
    byte[] actualBytes = outputStream.toByteArray();

    assertThat(actualBytes.length).isEqualTo(expectedBytes.length);
    assertThat(actualBytes).isEqualTo(expectedBytes);
  }

  @Test
  public void testTransparentColor() throws Exception {
    encoder.start(outputStream);
    encoder.setRepeat(0);
    encoder.setDelay(400);
    encoder.setTransparent(Color.BLUE);
    encoder.addFrame(sonic1);
    encoder.addFrame(sonic2);
    encoder.finish();

    byte[] expectedBytes = getExpectedBytes("/sonic-blue-transparent.gif");
    byte[] actualBytes = outputStream.toByteArray();

    assertThat(actualBytes.length).isEqualTo(expectedBytes.length);
    assertThat(actualBytes).isEqualTo(expectedBytes);
  }

  @Test
  public void testBackgroundAndTransparent() throws Exception {
    encoder.start(outputStream);
    encoder.setRepeat(0);
    encoder.setDelay(400);
    encoder.setSize(600, 600);
    encoder.setBackground(Color.GREEN);
    encoder.setTransparent(Color.BLUE);
    encoder.addFrame(sonic1);
    encoder.addFrame(sonic2);
    encoder.finish();

    byte[] expectedBytes = getExpectedBytes("/sonic-green-bg-blue-transparent.gif");
    byte[] actualBytes = outputStream.toByteArray();

    assertThat(actualBytes.length).isEqualTo(expectedBytes.length);
    assertThat(actualBytes).isEqualTo(expectedBytes);
  }

  private byte[] getExpectedBytes(String name) throws IOException {
    File expectedFile = new File(getClass().getResource(name).getFile());
    byte[] expectedBytes = new byte[(int) expectedFile.length()];
    FileInputStream inputStream = new FileInputStream(expectedFile);
    int readBytes = inputStream.read(expectedBytes);

    assertThat(readBytes).isGreaterThan(0);
    assertThat(readBytes).isEqualTo(expectedBytes.length);

    return expectedBytes;
  }

  private BufferedImage getImage(String name) throws IOException {
    File file = new File(getClass().getResource(name).getFile());
    return ImageIO.read(file);
  }
}
