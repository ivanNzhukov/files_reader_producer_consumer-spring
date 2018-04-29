package ru.testtask.beans;

public class CheckExtension {

    private String fileName;
    private String ext;

    public CheckExtension() {
    }

    public CheckExtension(String fileName) {
        this.fileName = fileName;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String checkFileExtension(String fileName) {


        int i = fileName.lastIndexOf('.');
        if (i >= 0) {

            ext = fileName.substring(i + 1);
            try {
                switch (ExtensionType.valueOf(ext)) {
                    case json:
                        ext = fileName.substring(i + 1);
                        return "jsonReader";
                    case csv:
                        ext = fileName.substring(i + 1);
                        return "csvReader";
                    case xlsx:
                        ext = fileName.substring(i + 1);
                        return "xlsxReader";
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Не корректное расширение");
                return ext;
            }
        }
        return ext;
    }
}
