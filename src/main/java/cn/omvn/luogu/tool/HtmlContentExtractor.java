package cn.omvn.luogu.tool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 2024/4/21
 * 栋dong
 */
public class HtmlContentExtractor {

    public static void extractContent(String html) {
        Document doc = Jsoup.parse(html);

        // 提取标题
        String title = doc.title();
        System.out.println("标题: " + title);

        // 提取题目描述
        Element description = doc.select("h2:contains(题目描述) + div").first();
        System.out.println("题目描述: " + (description != null ? description.text() : "未找到"));

        // 提取输入格式和输出格式
        // 查找“输入格式”后的第一个 div，跳过 <br/>
        Element inputFormat = doc.select("h3:contains(输入格式) + br + div").first();
        System.out.println("输入格式: " + (inputFormat != null ? inputFormat.text() : "未找到"));


        // 查找“输出格式”后的第一个 div，跳过 <br/>
        Element outputFormat = doc.select("h3:contains(输出格式) + br + div").first();
        System.out.println("输出格式: " + (outputFormat != null ? outputFormat.text() : "未找到"));

        // 提取输入输出示例
        // 直接查找示例代码块中的文本
        Element inputExample = doc.select("h3:contains(输入样例) + pre code").first();
        System.out.println("输入示例: " + (inputExample != null ? inputExample.text() : "未找到"));

        Element outputExample = doc.select("h3:contains(输出样例) + pre code").first();
        System.out.println("输出示例: " + (outputExample != null ? outputExample.text() : "未找到"));
    }

}