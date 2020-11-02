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
        for (int i = -100; i < 0; i ++){
            sb.append("<dimen name=\"dp_m_" + Math.abs(i) + "\">" + i + "dp</dimen>\n");
        }

        for (float i = 0; i < 600; i += 0.5) {
            if (i < 0) {
                sb.append("<dimen name=\"dp_m_" + Math.abs(i) + "\">" + i + "dp</dimen>\n");
            } else {

              //  sb.append("<dimen name=\"dp_" + i + "\">" + i + "dp</dimen>\n");


                String ii =  String.valueOf(i);
                if (ii.contains(".5")) {
                    String[] split = ii.split("\\.");
                    String s = split[0];
                    String s1 = split[1];

                    //sb.append("<dimen name=\"dp_" + s + "_" + s1 + "\">" + i + "dp</dimen>\n");
                    sb.append("<dimen name=\"dp_" + ii + "\">" + i + "dp</dimen>\n");
                } else {
                    int a = (int) i;
                    sb.append("<dimen name=\"dp_" + a + "\">" + a + "dp</dimen>\n");
                }

            }
        }
        sb.append("\n");
        sb.append("\n");
        for (int i = 6; i < 50; i++) {
            sb.append("<dimen name=\"sp_" + i + "\">" + i + "sp</dimen>\n");
        }

        sb.append("\n");
        sb.append("\n");
        sb.append("</resources>");

        StringBuffer sb2 = new StringBuffer();
        sb2.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb2.append("<resources>");

        File file = new File("./baseLib/src/main/res/values/dimenss.xml");
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
        for (int i = -100; i < 500; i += 0.5) {
            double outValue = i * multiple;
            if (i < 0) {
                sb.append("<dimen name=\"dp_m_" + Math.abs(i) + "\">" + outValue + "dp</dimen>\n");
            } else {
                String ii = "" + i;
                if (ii.contains(".")) {
                    String[] split = ii.split(".");
                    String s = split[0];
                    String s1 = split[1];

                    sb.append("<dimen name=\"dp_" + s + "_" + s1 + "\">" + outValue + "dp</dimen>\n");
                } else {
                    sb.append("<dimen name=\"dp_" + i + "\">" + outValue + "dp</dimen>\n");
                }

            }
        }
        sb.append("\n");
        sb.append("\n");
        for (int i = 6; i < 50; i += 1) {
            double outValue = i * multiple;
            sb.append("<dimen name=\"sp_" + i + "\">" + outValue + "sp</dimen>\n");
        }

        sb.append("\n");
        sb.append("\n");
        sb.append("</resources>");

        StringBuffer sb2 = new StringBuffer();
        sb2.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb2.append("<resources>");

        //File rootFile = new File("./baseLib/src/main/res/values/dimens.xml");
        File rootFile = new File("./baseLib/src/main/res/values-sw" + swdp + "dp");
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

   /* public static void main(String[] args) {
        //  baseDemen();
      *//*  for (int i = 320; i <= 560; i += 5)
            DimenTool.swDemen(i);*//*

      //  DimenTool.swDemen(375);

       // DimenTool.baseDemen();
        String fileName = "dimens.xml";
    }*/

    public static void main(String args[]) {
        baseDemen();
    }
}
