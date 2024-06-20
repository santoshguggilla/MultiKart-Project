package com.ecom.QRCodeGenareter;

import java.io.ByteArrayOutputStream;

//Java code to generate QR code

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class QRCode {
	@SuppressWarnings("deprecation")
	public static byte[] createQR(String data, int height, int width)
			throws WriterException, IOException {

		BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes()),
				BarcodeFormat.QR_CODE, 200,200);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(matrix, "JPEG", byteArrayOutputStream);

		return byteArrayOutputStream.toByteArray();
	}

}
