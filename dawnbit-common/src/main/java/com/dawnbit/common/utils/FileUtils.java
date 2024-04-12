package com.dawnbit.common.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

public class FileUtils {

    /**
     *
     */
    private static final String CLASSPATH_FILE_PREFIX = "classpath:";

    /**
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getFileAsString(String filePath) throws IOException {
        InputStream inputStream = null;
        try {
            if (filePath.startsWith(CLASSPATH_FILE_PREFIX)) {
                inputStream = Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream(filePath.substring(CLASSPATH_FILE_PREFIX.length()));
            } else {
                inputStream = new FileInputStream(filePath);
            }
            return getFileAsString(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public static File downloadFile(String sourcePath, String destinationPath) {
        OutputStream outStream = null;
        InputStream inStream = null;
        try {
            URL Url = new URL(sourcePath);
            byte[] buf;
            outStream = new BufferedOutputStream(new FileOutputStream(destinationPath));
            URLConnection uCon = Url.openConnection();
            inStream = uCon.getInputStream();
            int bytesRead = 0;
            buf = new byte[1000];
            while ((bytesRead = inStream.read(buf)) != -1) {
                outStream.write(buf, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                inStream.close();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new File(destinationPath);
    }

    public static String getFileAsString(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static void writeToFile(String path, byte[] data, boolean overwrite) throws IOException {
        File file = new File(path);
        if (!file.exists() || overwrite) {
            org.apache.commons.io.FileUtils.writeByteArrayToFile(file, data);
        }
    }

    public static void writeToFile(String path, byte[] data) throws IOException {
        writeToFile(path, data, false);
    }

    public static byte[] toByteArray(InputStream stream) throws IOException {
        return IOUtils.toByteArray(stream);
    }

    public static void write(byte[] data, OutputStream output) throws IOException {
        IOUtils.write(data, output);
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        ReadableByteChannel inputChannel = Channels.newChannel(inputStream);
        WritableByteChannel outputChannel = Channels.newChannel(outputStream);
        copyChannel(inputChannel, outputChannel);
    }

    public static void copyChannel(final ReadableByteChannel src, final WritableByteChannel dest) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            buffer.flip();
            dest.write(buffer);
            buffer.compact();
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }

    public static void zip(String fileOrDirectory, String outputfile) throws IOException {
        File destinationFile = new File(outputfile);
        File sourceFile = new File(fileOrDirectory);
        if (sourceFile.isDirectory()) {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(destinationFile));
            addDirectoryToZipStream(sourceFile, zipOutputStream, "");
            zipOutputStream.close();
        } else if (sourceFile.isFile()) {
            zipFile(sourceFile, destinationFile);
        } else {
            throw new IllegalArgumentException("Invalid file path");
        }
    }

    /**
     * @param sourceFile
     * @param zipOutputStream
     * @param string
     * @throws IOException
     */
    private static void addDirectoryToZipStream(File sourceFile, ZipOutputStream zipOutputStream, String parentFolderPath)
            throws IOException {
        zipOutputStream.putNextEntry(new ZipEntry(parentFolderPath + "/" + sourceFile.getName() + "/"));
        for (File file : sourceFile.listFiles()) {
            if (file.isDirectory()) {
                addDirectoryToZipStream(file, zipOutputStream, parentFolderPath + "/" + sourceFile.getName());
            } else {
                addFileToZipStream(file, zipOutputStream, parentFolderPath + "/" + sourceFile.getName());
            }
        }
        zipOutputStream.closeEntry();
    }

    /**
     * @param sourceFile
     * @param destinationFile
     * @throws IOException
     */
    private static void zipFile(File sourceFile, File destinationFile) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(destinationFile));
        addFileToZipStream(sourceFile, zipOutputStream, ".");
        zipOutputStream.close();

    }

    /**
     * @param sourceFile
     * @param zipOutputStream
     * @param string
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void addFileToZipStream(File sourceFile, ZipOutputStream zipOutputStream, String parentFolderPath)
            throws FileNotFoundException, IOException {
        FileInputStream sourceStream = null;
        try {
            zipOutputStream.putNextEntry(new ZipEntry(parentFolderPath + "/" + sourceFile.getName()));
            sourceStream = new FileInputStream(sourceFile);
            FileUtils.copyStream(sourceStream, zipOutputStream);
            zipOutputStream.closeEntry();
        } finally {
            if (sourceStream != null) {
                sourceStream.close();
            }
        }
    }

    public static void deleteFileOrDirectory(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File file : fileOrDirectory.listFiles()) {
                FileUtils.deleteFileOrDirectory(file);
            }
            if (fileOrDirectory.list().length == 0) {
                fileOrDirectory.delete();
            }
        } else {
            fileOrDirectory.delete();
        }
    }

    public static String normalizeFilePath(String directoryPath, String fileName) {
        return (directoryPath.endsWith("/") ? directoryPath : directoryPath + "/") + fileName;
    }

}
