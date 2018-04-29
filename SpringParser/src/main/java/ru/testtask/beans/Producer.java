package ru.testtask.beans;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

@Component
@Scope("prototype")
public class Producer implements Runnable {


    int i = 0;
    Handler handler;
    private BlockingQueue<LinkedList> queue;


    Producer() {
    }

    public Producer(Handler handler, BlockingQueue queue) {
        this.queue = queue;
        this.handler = handler;

    }


    @Override
    public void run() {

        try {
            if (handler.getFileExtension().equals("xlsx")) {
                xlsxCheck(handler.getFile());

            } else {
                Stream stream = Files.lines(getFilePath());
                Iterator iterator = stream.iterator();
                while (true) {


                    i++;

                    if (handler.isFileEnd()) {
                        setFileEnd(true);
                        System.out.println("produce stop");
                        Thread.sleep(100);
                        break;
                    }
                    Process(iterator.next().toString(), i);


                }
            }
            System.out.println("Чтение файла завершено");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            stopConsumeProcess();
            System.out.println("Закрываем Producer 1");

        }
        System.out.println("Закрываем Producer 2");
    }

    private void stopConsumeProcess() {

        handler.setFileEnd(true);

        Thread.currentThread().interrupt();
    }

    String getFileExtension() {
        return handler.getFileExtension();
    }

    String getFileName() {
        return handler.getFileName();
    }

    void setFileEnd(Boolean b) {
        handler.setFileEnd(b);
    }

    Path getFilePath() {
        return handler.getFilePath();
    }

    private void Process(String list, int line) throws Exception {
  //      LinkedList linkedList = new LinkedList();
        System.out.println("Положили строчку в очередь " + handler.setHandle(list, line) + "    ---i---   " + i);
     //   linkedList.addAll();
  //      System.out.println(linkedList);
        queue.put(handler.setHandle(list, line));
        System.out.println("Producer   Queue Capacity is " + queue.remainingCapacity());
        System.out.println("Это поток----------------------" + Thread.currentThread().getName());

        Thread.sleep(100);
    }

    private void xlsxCheck(File file) {

        try {

            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                i++;
                for (Cell cell : row) {
                    Process(cell.getStringCellValue(), i);

                }

            }

            workbook.close();
        } catch (Exception e) {
            System.err.println("Ошибка в чтение xlsx");
        }
        handler.setFileEnd(true);
        Thread.currentThread().interrupt();
    }

}


