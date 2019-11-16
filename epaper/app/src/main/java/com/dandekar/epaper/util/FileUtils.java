package com.dandekar.epaper.util;

import android.content.Context;

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
            FileWriter out = new FileWriter(new File(context.getFilesDir(), fileName));
            out.write(fileContents);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readStringFromFile(Context context, String fileName) {
        try {
            FileReader reader = new FileReader(new File(context.getFilesDir(), fileName));
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

    public static void writeObjectToFile(Context context, final Object object, String fileName) {
        try {
            FileOutputStream f = new FileOutputStream(new File(context.getFilesDir(), fileName));
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
            FileOutputStream f = new FileOutputStream(new File(context.getFilesDir(), fileName));
            f.write(bytes);
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readObjectFromFile(Context context, String fileName) {
        try {
            FileInputStream fi = new FileInputStream(new File(context.getFilesDir(), fileName));
            ObjectInputStream oi = new ObjectInputStream(fi);
            // Read object
            Object object = oi.readObject();
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
}
