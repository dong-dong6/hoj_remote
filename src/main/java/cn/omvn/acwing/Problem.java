package cn.omvn.acwing;

import cn.hutool.http.HttpRequest;
import cn.omvn.acwing.tool.ProblemContentExtractor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 2024/4/13
 * 栋dong
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Problem {
    private final static String BASE_URL = "https://www.acwing.com/problem/content/";
    String title;
    String content;
    String InputFormat;
    String OutputFormat;
    String Explain;

    public Problem getProblem(int id) {
        String url = BASE_URL + id + "/";
        String body = HttpRequest.get(url)
                .header("Referer", "https://www.acwing.com/problem/")
                .header("Host", "www.acwing.com").header("Accept", "*/*")
                .header("connection", "keep-alive")
                .cookie(cookie.getCsrftoken()).execute().body();
        //System.out.println(body);
        Problem problem = ProblemContentExtractor.extractContent(body);
        return problem;
    }
}
