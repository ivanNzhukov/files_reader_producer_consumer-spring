package ru.testtask.beans;

import ru.testtask.Interface.Reader;
import ru.testtask.Interface.Writer;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Handler {

    private Reader reader;
    private Writer writer;
    private boolean isFileEnd = false;
    private String filename = "";
    private Path filePath;
    private String fileExtension = "none";
    private File file;
    private BlockingQueue<LinkedList> queue;

    public Handler(Writer writer, Reader reader, int count) {

        this.reader = reader;
        this.writer = writer;
        queue = new LinkedBlockingQueue(count);

    }


    public Handler() {
    }

    File getFile() {
        return file;
    }

    public void setFile(File file, String extension) {
        this.file = file;
        setFilePath(file);
        setFileName(file);
        setFileExtension(extension);
    }

    boolean isFileEnd() {
        return isFileEnd;
    }

    void setFileEnd(boolean fileEnd) {
        isFileEnd = fileEnd;
    }

    private void setFilePath(File file) {
        filePath = file.toPath();
    }

    private String getFileName() {
        return filename;
    }

    private void setFileName(File file) {

        filename = file.getName();
    }

    void getQueue(LinkedList list) {

        writer.Printer(list);

    }

    LinkedList setQueue(String string, int line) {

        reader.objectReader(string, getFileName(), line);
        return reader.getList();

    }

    String getFileExtension() {
        return fileExtension;
    }

    private void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }


}

