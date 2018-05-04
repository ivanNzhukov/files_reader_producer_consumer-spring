package ru.testtask.beans;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

@Component
@Scope("prototype")
public class Producer implements Runnable {


    private int i = 1;
    private Handler handler;
    private BlockingQueue<LinkedList> queue;
    private Stream stream;
    private Iterator<String> iterator;
    private String[] charsets = {"UTF-8","windows-1251"};

    Producer() {
    }

    public Producer(Handler handler, BlockingQueue queue) {
        this.queue = queue;
        this.handler = handler;

    }

    @Override
    public void run() {

        try {
            if (getFileExtension().equals("xlsx")) {
                xlsxRead(getFile());

            } else {

                for (int j = 0; j < charsets.length; j++) {
                    try {
                        stream = Files.lines(getFile().toPath(), Charset.forName(charsets[j]));
                        iterator = stream.iterator();

                        while (!isEnd()) {
                            String s = iterator.next();

                            if (s.isEmpty()) throw new NoSuchElementException();
                            process(s);
                            setFileEnd(!iterator.hasNext());
                        }
                        stream.close();
                        break;
                    } catch (UncheckedIOException uioe) {
                        System.out.println("Кодировка отлична от " +charsets[j]);
                        System.out.println();
                    }
                }

            }
        } catch (NoSuchElementException e) {
            System.err.println("Файл пуст " + e);
            stream.close();
        } catch (MalformedInputException mie) {
            System.out.println("Error: " + mie);
        } catch (IOException e) {
            System.err.println("Error 1: " + e);
        } catch (Exception e) {
            System.err.println("Ошибка 2:" + e + " Пожалуйста, обратись к разработчику");
        } finally {
            setFileEnd(true);
        }
    }

    private File getFile() {
        return handler.getFile();
    }

    private boolean isEnd() {

        return handler.isFileEnd();

    }

    private String getFileExtension() {
        return handler.getFileExtension();
    }

    private void setFileEnd(Boolean b) {
        handler.setFileEnd(b);
    }

    private void process(String list) throws Exception {

        queue.put(handler.setQueue(list, i));

        Thread.sleep(100);
        i++;
    }

    private void xlsxRead(File file) {

        try {

            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet.getPhysicalNumberOfRows() == 0) throw new Error();
            for (Row row : sheet) {

                for (Cell cell : row) {
                    process(cell.getStringCellValue());
                }
            }
            workbook.close();
        } catch (Error e) {
            System.out.println("Файл " + file.getName() + " пуст!");
        } catch (Exception e) {
            System.err.println("Ошибка " + e + " при чтение " + file);
        }
        handler.setFileEnd(true);
    }
}


