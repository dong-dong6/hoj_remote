package cn.omvn.acwing;

import cn.omvn.util.ReUtil;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WebSocketWithCookies extends WebSocketClient {

    private Map<String, String> cookies;
    public static String flag;
    public String code = "{\"activity\":\"problem_submit_code\",\"problem_id\":1,\"code\":\"#include <iostream>\\n#include <cstring>\\n#include <algorithm>\\n\\nusing namespace std;\\n\\nint main()\\n{\\n    int a;\\n    int b;\\n    cin>>a>>b;\\n    cout<<a+b;\\n}\",\"language\":\"C++\",\"mode\":\"normal\",\"problem_activity_id\":0,\"record\":\"[]\",\"program_time\":0}";
    public WebSocketWithCookies(URI serverUri, Map<String, String> cookies) {
        super(serverUri);
        this.cookies = cookies;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Opened connection");
        send("{\n" +
                "  \"activity\": \"heartbeat\"\n" +
                "}");

    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received: " + message);
        String s = ReUtil.get("status\":\\s*\"(\\w+)", message, 1);
        System.out.println("当前判题状态" + s);

        flag = s;

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void connect() {
        if (cookies != null && !cookies.isEmpty()) {
            String cookieHeader = cookies.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .reduce((c1, c2) -> c1 + "; " + c2)
                    .orElse("");
            addHeader("Cookie", cookieHeader);
            addHeader("Origin", "https://www.acwing.com");
        }

        super.connect();
    }

    public void judge(String json,WebSocketWithCookies client) {
        client.connect();

    }

    public static void main(String[] args) {
        HttpCookie csrftoken = cookie.getCsrftoken();
        HttpCookie sessionid = cookie.getSessionid();

        Map<String, String> cookieMap = new HashMap<>();
        cookieMap.put("csrftoken", csrftoken.getValue());
        cookieMap.put("sessionid", sessionid.getValue());
        System.out.println(csrftoken.toString());
        System.out.println(sessionid.toString());

        WebSocketWithCookies client = new WebSocketWithCookies(URI.create("wss://www.acwing.com/wss/socket/"), cookieMap);
        client.judge(client.code,client);
        System.out.println(flag);
    }
}