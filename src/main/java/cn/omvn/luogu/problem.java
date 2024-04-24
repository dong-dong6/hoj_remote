package cn.omvn.luogu;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.omvn.luogu.tool.HtmlContentExtractor;

public class problem {
    private final static String BASE_URL = "https://www.luogu.com.cn/problem/";

    public static void getProblem(String id) {
        // 目标网址
        String url = BASE_URL + id;
        // 创建HTTP请求，并设置请求头
        HttpResponse response = HttpRequest.get(url)
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("accept-language", "zh-CN,zh;q=0.9,en;q=0.8")
                .header("cache-control", "no-cache")
                .header("cookie", "__client_id=26c6c5ffd67768686623e6cc8ca4641d5309a28e; _uid=903642")
                .header("pragma", "no-cache")
                .header("referer", "https://www.luogu.com.cn/problem/list")
                .header("sec-ch-ua", "\"Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Windows\"")
                .header("sec-fetch-dest", "document")
                .header("sec-fetch-mode", "navigate")
                .header("sec-fetch-site", "same-origin")
                .header("sec-fetch-user", "?1")
                .header("upgrade-insecure-requests", "1")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                .execute(); // 发送请求
        // 打印获取到的网页内容
        String html = response.body();
        HtmlContentExtractor.extractContent(html);
    }
}
