package com.qflbai.lib.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * @author WenXian Bai
 * @Date: 2018/7/5.
 * @Description:
 */
public class DimenTool {
    public static void baseDemen() {
        //以此文件夹下的dimens.xml文件内容为初始值参照

        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<resources>");

        sb.append("\n");
        sb.append("\n");
        for (double i = -100; i < 600; i += 0.1) {
            if (i < 0) {
                sb.append("<dimen name=\"dp_m_" + Math.abs(i) + "\">" + i + "dp</dimen>\n");
            } else {
                sb.append("<dimen name=\"dp_" + i + "\">" + i + "dp</dimen>\n");
            }
        }
        sb.append("\n");
        sb.append("\n");
        for (int i = 6; i < 50; i ++) {
            sb.append("<dimen name=\"sp_" + i + "\">" + i + "sp</dimen>\n");
        }

        sb.append("\n");
        sb.append("\n");
        sb.append("</resources>");

        StringBuffer sb2 = new StringBuffer();
        sb2.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb2.append("<resources>");

        File file = new File("./baseLib/src/main/res/values/dimens.xml");
        if (file.exists()) {
            return;
        }
        createFile(file, sb, sb2);
    }

    public static void swDemen(int swdp) {
        //以此文件夹下的dimens.xml文件内容为初始值参照

        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<resources>");

        sb.append("\n");
        sb.append("\n");
        double multiple = swdp / 375.0000;
        for (double i = -100; i < 600; i = +0.1) {
            double outValue = i * multiple;
            if (i < 0) {
                sb.append("<dimen name=\"dp_m_" + Math.abs(i) + "\">" + outValue + "dp</dimen>\n");
            } else {
                sb.append("<dimen name=\"dp_" + i + "\">" + outValue + "dp</dimen>\n");
            }
        }
        sb.append("\n");
        sb.append("\n");
        for (int i = 6; i < 60; i += 1) {
            double outValue = i * multiple;
            sb.append("<dimen name=\"sp_" + i + "\">" + outValue + "sp</dimen>\n");
        }

        sb.append("\n");
        sb.append("\n");
        sb.append("</resources>");

        StringBuffer sb2 = new StringBuffer();
        sb2.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb2.append("<resources>");

        File rootFile = new File("./app/src/main/res/values-sw" + swdp + "dp");
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        String fileName = "dimens.xml";
        File file = new File(rootFile, fileName);
        if (file.exists()) {
            return;
        }
        createFile(file, sb, sb2);
    }

    private static void createFile(File file, StringBuffer sb, StringBuffer sb2) {

        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(file));
            pw.print(sb.toString());
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        baseDemen();
       // DimenTool.swDemen(375);
    }
}
