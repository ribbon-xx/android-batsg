package com.chauhai.android.batsg.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.util.Log;

/**
 * Utility functions on file.
 *
 * @author umbalaconmeogia
 *
 */
public class FileUtil {

  private static final String TAG = "FileUtil";

	/**
	 * Get all sub directories and files in specified directory, sort by name.
	 * @param dirPath
	 * @param fileFilter If NULL, then sub files are not get.
	 * @param fileComparator Used to sort the file. May be null.
	 * @return If the directory does not exist, return null.
	 *         Else return ArrayList of file.
	 */
	public static ArrayList<File> listFiles(String dirPath,
			FileFilter fileFilter,
			Comparator<File> fileComparator) {

		// Current directory.
		File dir = new File(dirPath);
		// Return null if it is not a directory.
		if (!dir.exists() || !dir.isDirectory()) {
			return null;
		}

		List<File> files;
		// Get files
		Log.d(TAG, "listFiles(" + dirPath +")" + fileFilter);
		files = Arrays.asList(dir.listFiles(fileFilter));
		if (fileComparator != null) {
			Collections.sort(files, fileComparator);
		}
		// Merge.
		return new ArrayList<File>(files);
	}

	/**
	 * Return the file extension (without dot).
	 * @param file
	 * @return
	 */
	public static String fileExtension(File file) {
		String fileExt = null;
		String fileName = file.getName();
		int dotPosition = fileName.lastIndexOf(".");
		if (dotPosition > 0) {
			fileExt = fileName.substring(dotPosition + 1, fileName.length());
		}
		return fileExt;
	}

	/**
	 * Get the file name without extension.
	 * @param file
	 * @return
	 */
	public static String fileNameWithoutExtension(File file) {
		String fileName = file.getName();
		String bareName = fileName;
		int dotPosition = fileName.lastIndexOf(".");
		if (dotPosition > 0) {
			bareName = fileName.substring(0, dotPosition);
		}
		return bareName;
	}

	/**
	 * Save a file form InputStream to a file in the Internal Storage.
	 * @param context
	 * @param in
	 * @param internalStorageFilePath
	 * @throws IOException
	 */
  public static void save(Context context, InputStream in, String internalStorageFilePath) throws IOException {
    String fileName = context.getFilesDir() + "/" + internalStorageFilePath;
    save(in, fileName);
  }

  /**
   * Save a file from InputStream.
   * @param in
   * @param fileName
   * @throws IOException
   */
  public static void save(InputStream in, String fileName) throws IOException {
    FileOutputStream out = new FileOutputStream(fileName);
    byte[] buffer = new byte[4096];
    int len = 0;
    while ((len = in.read(buffer)) > 0 ) {
     out.write(buffer,0, len);
    }
    out.close();
  }

  /**
   * Get content of a file.
   * @param filePath
   * @return
   * @throws IOException
   */
	public static String getContents(String filePath) throws IOException {
	  return getContents(new FileReader(filePath));
	}

	/**
	 * Get content of a file in internal storage.
	 * @param context
	 * @param internalStorageFileName
	 * @return
	 * @throws IOException
	 */
	public static String getContents(Context context,
	    String internalStorageFileName) throws IOException {
	  return getContents(new InputStreamReader(context.openFileInput(internalStorageFileName)));
	}

	/**
	 * Get content from a reader.
	 * This will close reader.
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static String getContents(Reader reader) throws IOException {
    StringBuilder text = new StringBuilder();
    BufferedReader br = new BufferedReader(reader);
    String line;
    while ((line = br.readLine()) != null) {
      text.append(line);
      text.append("\n");
    }
    reader.close();
    return text.toString();
	}

	/**
   * Get content from a reader.
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String getContents(InputStream in) throws IOException {
	  return getContents(new InputStreamReader(in));
	}

	/**
	 * Write a string to file.
	 * @param filePath
	 * @param content
	 * @throws IOException
	 */
	public static void filePutContents(String filePath, String content) throws IOException {
		FileWriter fileWriter = new FileWriter(filePath);
		fileWriter.write(content);
		fileWriter.flush();
		fileWriter.close();
	}

	public static void delete(String filePath) {
	  File file = new File(filePath);
	  file.delete();
	}
}