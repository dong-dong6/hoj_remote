package cn.omvn.luogu;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;

import java.net.HttpCookie;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static Map<String, String> headers = MapUtil
            .builder(new HashMap<String, String>())
            .put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36")
            .map();
    public static List<HttpCookie> cookies = new ArrayList<>();

    public static void login() {
        HttpResponse execute = HttpRequest.get(HOST).execute();
        cookies.add(execute.getCookie("__client_id"));
        cookies.add(execute.getCookie("_uid"));
        cookies.add(execute.getCookie("C3VK"));
        // 获取当前的时间戳
        Instant instant = Instant.now();
        // 获取毫秒级时间戳
        long milliseconds = instant.toEpochMilli();
        // 获取纳秒部分
        long nanos = instant.getNano();
        // 将毫秒级时间戳和纳秒部分组合成字符串表示的时间戳
        String timestamp = milliseconds + "." + String.format("%04d", Math.round((double) nanos / 1_000_000));
        String body = HttpRequest.get(HOST + CAPTCHA_URL).cookie(execute.getCookie("__client_id")).cookie(execute.getCookie("_uid")).execute().body();
        System.out.println(body);
        JSONObject payload = new JSONObject();
        payload.set("username", username);
        payload.set("password", password);
        String captcha = new String();
        payload.set("captcha", captcha);
        HttpRequest.post(HOST + LOGIN_URL)
                .body(payload.toString())
                .cookie().execute();
    }
    //   public static
}
