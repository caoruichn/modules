package com.example.modules.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author CaoRui
 * @date 2019年10月9日
 */
public class CreateImageUtil {

    private static char[]  letterSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    private static char[] numSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    
    /**
     * 获取随机验证码
     * @param codeCount 验证码长度,需要大于0，否则返回null
     * @param isLetter 验证码包含字符，true字母、false数字、null字母和数字
     * @return
     */
    public static String getCaptchaCode(Integer codeCount, Boolean isLetter) {
        if(codeCount == null || codeCount < 1) {
            return null;
        }
        // 验证码类型
        char[] codeSequence;
        if(isLetter == null) {
            codeSequence = ArrayUtils.addAll(letterSequence, numSequence);
        }else if(isLetter) {
            codeSequence = letterSequence;
        }else {
            codeSequence = numSequence;
        }
        Random random = new Random();
        StringBuffer randomCode = new StringBuffer();
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            randomCode.append(strRand);
        }
        return randomCode.toString();
    }
    /**
     * 创建图片并保存为指定outFile
     * @param str 图片文字
     * @param outFile 输出文件
     * @param width 图片宽
     * @param height 图片高
     * @param lineNum 随机线条数
     * @throws IOException 
     */
    public static void createImage(String str, File outFile, Integer width, Integer height, Integer lineNum) throws Exception {
        BufferedImage image = createImage(str, width, height, lineNum);
        ImageIO.write(image, "png", outFile);// 输出png图片
    }
    /**
     * 创建图片写入OutputStream
     * @param str 图片文字
     * @param outFile 输出文件
     * @param width 图片宽
     * @param height 图片高
     * @param lineNum 随机线条数
     * @throws IOException 
     */
    public static void createImage(String str, OutputStream outputStream, Integer width, Integer height, Integer lineNum) throws Exception {
        BufferedImage image = createImage(str, width, height, lineNum);
        ImageIO.write(image, "png", outputStream);// 输出png图片
    }
    
    /**
     * 创建图片
     * @param str 图片文字
     * @param width 图片宽
     * @param height 图片高
     * @param lineNum 随机线条数
     * @throws IOException 
     */
    public static BufferedImage createImage(String str, Integer width, Integer height, Integer lineNum) throws Exception {
        Random random = new Random();
        int x = 0, fontHeight = 30;
        int red = 0, green = 0, blue = 0;
        
        x = width / (str.length() + 2);// 每个字符的宽度(左右各空出一个字符)
        
        // 创建图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        // 设置背景
        g.setColor(Color.WHITE);
        g.fillRect(0,0,width, height);
        // 设置字体
        g.setFont(new Font("微软雅黑", Font.BOLD, fontHeight));
        // 写字(颜色随机)
        char[] strArr = str.toCharArray();
        for(int i = 0; i < strArr.length; i++) {
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(String.valueOf(strArr[i]), (i + 1) * x, (random.nextInt(height-fontHeight))+fontHeight-2);
        }
        // 画干扰线(颜色随机)
        for(int i = 0; i < lineNum; i++) {
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(random.nextInt(width),random.nextInt(height),
                    random.nextInt(width),random.nextInt(height));
        }
        g.dispose();
        return image;
    }
    
    public static void main(String[] args) throws Exception {
        String code = "";
        for(int i=0; i<10; i++) {
            code = getCaptchaCode(6, null);
            createImage(code, new File("C:/Users/CAORUI/Desktop/test/captcha/" + i + code +".png"), 128, 64, 15);
        }
        System.out.println("success");
    }
}
