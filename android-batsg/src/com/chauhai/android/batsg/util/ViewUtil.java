package com.chauhai.android.batsg.util;

import java.io.IOException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class ViewUtil {

  /**
   * Display an image from the internet to an ImageView.
   * @param imageView
   * @param imageUrl
   * @return the loaded bitmap.
   * @throws IOException
   */
  public static Bitmap showImage(ImageView imageView, String imageUrl) throws IOException {
    URL newurl = new URL(imageUrl);
    Bitmap bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
    imageView.setImageBitmap(bitmap);
    return bitmap;
  }

  /**
   * Display an image from the internet to an ImageView.
   * <p>
   * This method will throw RuntimeException if there is any error.
   * @param imageView
   * @param imageUrl
   * @return the loaded bitmap.
   */
  public static Bitmap showImageNoThrow(ImageView imageView, String imageUrl) {
    try {
      return showImage(imageView, imageUrl);
    } catch (IOException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

}
