package cn.omvn.acwing.tool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 2024/4/13
 * 栋dong
 */
public class CsrfTool {
    public static String extractCsrfToken(String html) {
        Document doc = Jsoup.parse(html);
        Element csrfInput = doc.select("input[name=csrfmiddlewaretoken]").first();
        if (csrfInput != null) {
            //System.out.println(csrfInput.attr("value"));
            return csrfInput.attr("value");
        } else {
            return null; // 如果未找到 CSRF 令牌字段，返回 null 或者你认为合适的默认值
        }
    }
}
