package cn.omvn.util;

import cn.hutool.http.HttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageToBase64Converter {

    public static String convertImageToBase64(String imageUrl) throws IOException {
        // 发起 HTTP 请求获取图片数据
        byte[] imageData = HttpRequest.get(imageUrl).execute().bodyBytes();
        
        // 将图片数据转换为 Base64 格式字符串
        return Base64.getEncoder().encodeToString(imageData);
    }

    public static void main(String[] args) {
        String imageUrl = "https://example.com/image.jpg"; // 替换为实际图片的 URL
        try {
            String base64String = convertImageToBase64(imageUrl);
            System.out.println(base64String);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}