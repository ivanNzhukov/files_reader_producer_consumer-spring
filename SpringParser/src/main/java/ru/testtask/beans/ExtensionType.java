package ru.testtask.beans;


import java.util.ArrayList;
import java.util.List;

public enum ExtensionType {
    json, csv, xlsx;



    public static List<String> checkFileExtension(String fileName) {
        ArrayList<String> extension = new ArrayList<String>();


        int i = fileName.lastIndexOf('.');
        if (i >= 0) {

            extension.add(fileName.substring(i + 1));
            try {
                switch (ExtensionType.valueOf(extension.get(0))) {
                    case json:

                        extension.add("jsonReader");
                        return extension;
                    case csv:

                        extension.add("csvReader");
                        return extension;
                    case xlsx:

                        extension.add("xlsxReader");
                        return extension;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Не корректное расширение");
                 extension.set(0,"none");
                return extension;
            }
        }
        extension.set(0,"none");
        return extension;
    }
}
