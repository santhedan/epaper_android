package com.dandekar.epaper.util;

import android.content.Context;

import com.dandekar.epaper.data.toimodel.Publication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class FileUtils {

    public static void writeStringToFile(Context context, final String fileContents, String fileName) {
        try {
            File file = new File(context.getFilesDir() + ApplicationCache.curSel.getPathToSave(), fileName);
            file.getParentFile().mkdirs();
            FileWriter out = new FileWriter(file);
            out.write(fileContents);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readStringFromFile(Context context, String fileName) {
        try {
            File file = new File(context.getFilesDir() + ApplicationCache.curSel.getPathToSave(), fileName);
            FileReader reader = new FileReader(file);
            StringBuffer stringBuffer = new StringBuffer();
            int numCharsRead;
            char[] charArray = new char[1024];
            while ((numCharsRead = reader.read(charArray)) > 0) {
                stringBuffer.append(charArray, 0, numCharsRead);
            }
            reader.close();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void writeObjectToFile(Context context, final Publication object, String fileName) {
        try {
            File file = new File(context.getFilesDir() + ApplicationCache.curSel.getPathToSave(), fileName);
            file.getParentFile().mkdirs();
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);
            // Write objects to file
            o.writeObject(object);
            // Close the files
            o.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeBytesToFile(Context context, final byte[] bytes, String fileName) {
        try {
            File file = new File(context.getFilesDir() + ApplicationCache.curSel.getPathToSave(), fileName);
            file.getParentFile().mkdirs();
            FileOutputStream f = new FileOutputStream(file);
            f.write(bytes);
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  static byte[] readBytesFromFile(Context context, String fileName) {
        try {
            File file = new File(context.getFilesDir() + ApplicationCache.curSel.getPathToSave(), fileName);
            FileInputStream fi = new FileInputStream(file);
            byte[] fileData = new byte[(int)file.length()];
            fi.read(fileData);
            fi.close();
            return fileData;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Publication readObjectFromFile(Context context, String fileName) {
        try {
            File file = new File(context.getFilesDir() + ApplicationCache.curSel.getPathToSave(), fileName);
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi);
            // Read object
            Publication object = (Publication)oi.readObject();
            oi.close();
            fi.close();
            //
            return object;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean fileExists(Context context, String fileName) {
        File file = new File(context.getFilesDir() + ApplicationCache.curSel.getPathToSave(), fileName);
        return file.exists();
    }
}
