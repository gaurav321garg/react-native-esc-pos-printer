package com.escposprinter;

import com.facebook.react.bridge.ReadableMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.content.Context;
import android.net.Uri;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageManager {
  public static Bitmap getBitmapFromSource(ReadableMap source, Context mContext) throws Exception {
    String uriString = source.getString("uri");

    if (uriString == null || uriString.isEmpty()) {
      throw new IllegalArgumentException("Image uri is null or empty");
    }

    Uri uri = Uri.parse(uriString);
    String scheme = uri.getScheme();

    // Base64 data URI: data:image/png;base64,...
    if ("data".equals(scheme)) {
      String pureBase64Encoded = uriString.substring(uriString.indexOf(",") + 1);
      byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
      Bitmap image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
      if (image == null) throw new Exception("Failed to decode base64 image");
      return image;
    }

    // Remote URL
    if ("http".equals(scheme) || "https".equals(scheme)) {
      HttpURLConnection connection = (HttpURLConnection) new URL(uriString).openConnection();
      connection.setConnectTimeout(10000);
      connection.setReadTimeout(10000);
      try {
        InputStream stream = connection.getInputStream();
        Bitmap image = BitmapFactory.decodeStream(stream);
        if (image == null) throw new Exception("Failed to decode remote image: " + uriString);
        return image;
      } finally {
        connection.disconnect();
      }
    }

    // Content URI (MediaStore, camera, document picker)
    if ("content".equals(scheme)) {
      InputStream stream = mContext.getContentResolver().openInputStream(uri);
      if (stream == null) throw new Exception("Failed to open content URI: " + uriString);
      try {
        Bitmap image = BitmapFactory.decodeStream(stream);
        if (image == null) throw new Exception("Failed to decode content URI image: " + uriString);
        return image;
      } finally {
        stream.close();
      }
    }

    // File URI: file:///storage/...
    if ("file".equals(scheme)) {
      String path = uri.getPath();
      if (path == null) throw new Exception("Invalid file URI: " + uriString);
      Bitmap image = BitmapFactory.decodeFile(path);
      if (image == null) throw new Exception("Failed to decode file: " + path);
      return image;
    }

    // Drawable resource name fallback
    int resourceId = mContext.getResources().getIdentifier(uriString, "drawable", mContext.getPackageName());
    if (resourceId == 0) throw new Exception("Could not resolve image source: " + uriString);
    Bitmap image = BitmapFactory.decodeResource(mContext.getResources(), resourceId);
    if (image == null) throw new Exception("Failed to decode drawable: " + uriString);
    return image;
  }
}
