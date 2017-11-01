/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testPackage;

/**
 *
 * @author 赵学鹏
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Tools {

    static String SqlmapDir;
    static String DB;

    /**
     * @param args
     * @throws IOException
     */


    public static int delBat(File dir) {
        File[] files = dir.listFiles();
        int count = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                delBat(files[i]);
            } else {
                String strFileName = files[i].getAbsolutePath().toLowerCase();
                if (strFileName.endsWith(".bat")) {
                    System.out.println("正在删除：" + strFileName);
                    files[i].delete();
                    count++;
                }
            }
        }
        return count;
    }
//删除结果文件夹

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
//判断是否已存在旧的bat

    public static boolean isExistBat(File dir) {
        File[] f = dir.listFiles();
        boolean flag = false;
        for (int i = 0; i < f.length; i++) {
            if (f[i].isDirectory()) {
                isExistBat(f[i]);
            } else {
                if (f[i].getName().endsWith(".bat")) {
                    //System.out.println(f[i].getName()+"此文件为bat文件");
                    flag = true;
                }
            }
        }
        return flag;
    }
//判断是否有文件

    public static boolean isExistFile(String dir, String s) {
        File file = new File(dir + "\\" + s);
        //System.out.println(file.toString());
        return (file.exists());
    }
//暂时不用这个方法，无法遍历到子文件夹，使用getFiles()

    public static ArrayList<String> getFileList(File file) {
        ArrayList<String> result = new ArrayList<String>();
        if (!file.isDirectory()) {
            System.out.println(file.getAbsolutePath());
            result.add(file.getAbsolutePath());
        } else {
            // 内部匿名类，用来过滤文件类型  
            File[] directoryList = file.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    if (file.isFile() && file.getName().indexOf("txt") > -1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            for (int i = 0; i < directoryList.length; i++) {
                result.add(directoryList[i].getAbsolutePath());
            }
        }
        return result;
    }
//获取目录下所有的txt文件，包括子目录中

    public static ArrayList<String> getFiles(File fileDir, String fileType) {
        ArrayList<String> lfile = new ArrayList<String>();
        File[] fs = fileDir.listFiles();
        for (File f : fs) {
            if (f.isFile()) {
                if (fileType
                        .equals(f.getName().substring(
                                f.getName().lastIndexOf(".") + 1,
                                f.getName().length()))) {
                    lfile.add(f.getAbsolutePath());
                }
            } else {
                ArrayList<String> ftemps = getFiles(f, fileType);
                lfile.addAll(ftemps);
            }
        }
        return lfile;
    }

//    static void readXML() {
//        String rootPath = System.getProperty("user.dir").replace("\\", "/");
//        // 可以使用绝对路径
//        //System.out.println(rootPath);
//        SAXReader reader = new SAXReader();
//        File f = new File(rootPath + "\\config.xml");
//        try {
//
//            // 读取XML文件 
//            Document doc = reader.read(f);
//
//            Element root = doc.getRootElement();
//
//            //System.out.println(root.getName()); 
//            List<Element> param = root.elements();
//
//            for (Element element : param) {
//                if (element.getName().equals("SqlmapDir")) {
//                    SqlmapDir = element.getText();
//                } else if (element.getName().equals("DB")) {
//                    DB = element.getText();
//                }
//
//            }
//
//        } catch (DocumentException e) {
//
//            e.printStackTrace();
//
//        }
//    }

    public void killProcess() {
        Runtime rt = Runtime.getRuntime();
        Process p = null;
        try {
            rt.exec("cmd.exe /C start wmic process where name='cmd.exe' call terminate");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
