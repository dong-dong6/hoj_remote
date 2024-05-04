package cn.omvn.luogu;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.omvn.util.ReUtil;

import java.net.HttpCookie;
import java.time.Instant;
import java.util.*;

/**
 * 2024/4/21
 * 栋dong
 */
public class Login {
    private static final String username = "dongdong741236@outlook.com";
    private static final String password = "qweASDzxc123!@#";
    public static final String HOST = "https://www.luogu.com.cn/";
    public static final String LOGIN_URL = "do-auth/password";
    public static final String CAPTCHA_URL = "lg4/captcha";
    public static final String SUBMIT_URL = "fe/api/problem/submit/";
    public static Map<String, String> headers = MapUtil
            .builder(new HashMap<String, String>())
            .put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36")
            .map();
    public static List<HttpCookie> cookies = new ArrayList<>();
    public static String imagePath = "/path/to/your/server/images";

    public static void login() {
        HttpResponse execute = HttpRequest.get("https://www.luogu.com.cn/auth/login").execute();
//        cookies.add(execute.getCookie("__client_id"));
        // 获取当前的时间戳
        Instant instant = Instant.now();
        // 获取毫秒级时间戳
        long milliseconds = instant.toEpochMilli();
        // 获取纳秒部分
        long nanos = instant.getNano();
        // 将毫秒级时间戳和纳秒部分组合成字符串表示的时间戳
        String timestamp = milliseconds + "." + String.format("%04d", Math.round((double) nanos / 1_000_000));
        HttpResponse captchaResponse = HttpRequest.get(HOST + CAPTCHA_URL)
                .cookie(execute.getCookie("__client_id"))
                .execute();
        String img = Base64.getEncoder().encodeToString(captchaResponse.bodyBytes());
        System.out.println("data:image/png;base64," + img);
        JSONObject payload = new JSONObject();
        payload.set("username", username);
        payload.set("password", password);
        Scanner scanner = new Scanner(System.in);
        String captcha = scanner.next();
        payload.set("captcha", captcha);
        HttpResponse loginExecute = HttpRequest.post(HOST + LOGIN_URL)
                .body(payload.toString())
                .cookie(execute.getCookie("__client_id"))
                .execute();
        System.out.println(loginExecute.body());
        String uid = ReUtil.get("\"syncToken\":\"[^+\"]+\\+(\\d+)", loginExecute.body(), 1);
//        cookies.add(new HttpCookie("_uid",uid));
        cookies = loginExecute.getCookies();
        System.out.println(cookies);

    }

    public static void submit(String id) {
        login();
        JSONObject load = new JSONObject();
        load.put("code","#include <stdio.h>\n" +
                        "\n" +
                        "int main()\n" +
                        "{\n" +
                        "    int a,b;\n" +
                        "    scanf(\"%d%d\",&a,&b);\n" +
                        "    printf(\"%d\\n\", a+b);\n" +
                        "    return 0;\n" +
                        "}");
        load.put("enableO2",1);
        load.put("lang",28);
        System.out.println(HOST + SUBMIT_URL + id);
        HttpResponse execute = HttpRequest.post(HOST + SUBMIT_URL + id)
                .cookie(cookies)
                .body(load.toString())
                .execute();
        System.out.println(execute.body());
    }
}
