package ru.testtask.beans;

import ru.testtask.Interface.Reader;
import ru.testtask.Interface.Writer;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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


    public Handler() {   }

    public File getFile() {
        return file;
    }

    public void setFile(String file, String extension) {
        this.file = new File(file);
        setFilePath(file);
        setFileName(getFilePath());
        setFileExtension(extension);
    }

    public boolean isFileEnd() {
        return isFileEnd;
    }

    public void setFileEnd(boolean fileEnd) {
        isFileEnd = fileEnd;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(String file) {
        filePath = Paths.get(file);

    }

    public String getFileName() {
        return filename;
    }

    public void setFileName(Path filePath) {
        filename = filePath.getFileName().toString();
    }

    public void getHandle(LinkedList list) throws InterruptedException {

        writer.Printer(list);

    }

    public LinkedList setHandle(String string, int line) throws Exception {

        reader.objectReader(string, getFileName(), line);
        return reader.getList();

    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }




}

