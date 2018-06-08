package com.softtek.lai.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Java utils 实现的Zip工具
 *
 * @author jerry.guan
 */
public class ZipUtils {

    /**
     * 压缩目录
     * @param srcFile 要压缩的源文件
     * @param zipFile 压缩过后的输出文件
     * @throws FileNotFoundException
     */
    public static void zipFolder(File srcFile, File zipFile) throws FileNotFoundException {

        // 创建Zip包
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFile));
        // 压缩
        try {
            zipFiles(srcFile, outZip);
            outZip.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 完成,关闭
            try {
                outZip.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 递归遍历文件目录并 压缩
     * @param file 原目录文件
     * @param zipOut 压缩输出文件
     * @throws IOException
     */
    private static void zipFiles(File file, ZipOutputStream zipOut) throws IOException {
        if (zipOut == null) {
            return;
        }
        // 判断是不是文件
        if (file.isFile()) {
            ZipEntry zipEntry = new ZipEntry(file.getName());
            FileInputStream inputStream = new FileInputStream(file);
            zipOut.putNextEntry(zipEntry);
            int len;
            byte[] buffer = new byte[100000];
            while ((len = inputStream.read(buffer)) != -1) {
                zipOut.write(buffer, 0, len);
            }
            inputStream.close();
            zipOut.closeEntry();

        } else {
            // 文件夹的方式,获取文件夹下的子文件
            String[] fileList = file.list();
            // 如果没有子文件, 则添加进去即可
            if (fileList.length <= 0) {
                ZipEntry zipEntry = new ZipEntry(file.getName() + File.separator);
                zipOut.putNextEntry(zipEntry);
                zipOut.closeEntry();
            }
            // 如果有子文件, 遍历子文件
            for (String name:fileList) {
                zipFiles(new File(file,name), zipOut);
            }
        }

    }
}
