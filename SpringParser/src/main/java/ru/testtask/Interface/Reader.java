package ru.testtask.Interface;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface Reader {
    void objectReader(String string, String filename, int lineNumber);

    LinkedList getList();

}
