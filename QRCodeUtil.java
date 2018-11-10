import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QRCodeUtil {
	private static Logger logger= LoggerFactory.getLogger(QRCodeUtil.class);

	public static BufferedImage encodeBarCode(String contents, int width, int height) {
		int codeWidth = 3 + // start guard
				(7 * 6) + // left bars
				5 + // middle guard
				(7 * 6) + // right bars
				3; // end guard
		codeWidth = Math.max(codeWidth, width);
        BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.EAN_13, codeWidth, height, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

    public static void encodeBarCodeToFile(String contents, int width, int height,String imgPath) throws IOException {
        BufferedImage image = encodeBarCode(contents,width,height);
        ImageIO.write(image,"png",new File(imgPath));
    }

    public static String decodeBarCode(BufferedImage barCode) {
        Result result = null;
        try {
            if (barCode == null) {
                logger.error("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(barCode);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            result = new MultiFormatReader().decode(bitmap, null);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	public static String decodeBarCodeFromFile(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				logger.error("the decode image may be not exit.");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			result = new MultiFormatReader().decode(bitmap, null);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage encodeQRCode(String contents, int width, int height) {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// 指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "GBK");
        BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.QR_CODE, width, height, hints);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

    public static void encodeQRCodeToFile(String contents, int width, int height,String imgPath) throws IOException {
        BufferedImage image = encodeQRCode(contents,width,height);
        ImageIO.write(image,"png",new File(imgPath));
    }

    public static String decodeQRCode(BufferedImage qrCode) {
        Result result = null;
        try {
            if (qrCode == null) {
                logger.error("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(qrCode);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
            hints.put(DecodeHintType.CHARACTER_SET, "GBK");
            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	public static String decodeQRCodeFromFile(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				logger.error("the decode image may be not exit.");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
			hints.put(DecodeHintType.CHARACTER_SET, "GBK");
			result = new MultiFormatReader().decode(bitmap, hints);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}