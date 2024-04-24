package cn.omvn.acwing;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.omvn.acwing.tool.CsrfTool;
import lombok.Data;

import java.net.HttpCookie;

/**
 * 2024/4/13
 * 栋dong
 */
@Data
public class cookie {
    private static final String BASE_URL = "https://www.acwing.com/";
    public static HttpCookie csrftoken;
    public static HttpCookie sessionid;
    public static HttpCookie getCsrftoken() {
        if (csrftoken == null) {
            setCookie();
            return csrftoken;
        } else {
            return csrftoken;
        }
    }
    public static HttpCookie getSessionid() {
        if (sessionid == null) {
            setCookie();
            return sessionid;
        } else {
            return sessionid;
        }
    }

    public static void setCookie() {
        HttpResponse execute = HttpRequest.get(BASE_URL).execute();
        String body = execute.body();
        //System.out.println(CsrfTool.extractCsrfToken(body));
        //System.out.println(ReUtil.get("name=\"csrfmiddlewaretoken\" value=\"([\\s\\S]*?)\"", body, 1));
        csrftoken = execute.getCookie("csrftoken");
        String postData = "csrfmiddlewaretoken=" + CsrfTool.extractCsrfToken(body) +
                "&username=DongDong741236%40outlook.com" +
                "&password=J%40Geh76GGVHnEEc" +
                "&remember_me=on";
        String loginUrl = "https://www.acwing.com/user/account/signin/";
        HttpResponse loginExecute = HttpRequest.post(loginUrl)
                .header(Header.REFERER, BASE_URL)
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
                .header(Header.COOKIE, csrftoken.toString())
                .header("X-CSRFToken", csrftoken.getValue())
                .header("X-Requested-With", "XMLHttpRequest")
                .body(postData)
                .execute();
        sessionid = loginExecute.getCookie("sessionid");
        System.out.println(loginExecute.body());
    }
}
