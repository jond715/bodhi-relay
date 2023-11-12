package com.leafbodhi.nostr.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Jond
 */
@Slf4j
public class HttpUtil {
    public static String get(String url) throws Exception {
        // 创建URL对象
        URL obj = new URL(url);
        // 打开HTTP连接
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        // 设置请求头部参数
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Content-Type","application/json");
        con.setRequestProperty("apikey","zycOxJpiCyEcbN4RJIsobs7qxbDZ2430");
        // 获取响应码
        int responseCode = con.getResponseCode();
        // 打印输出信息
        log.info("Sending 'GET' request to URL : " + url);
        log.info("Response Code : " + responseCode);
        // 创建一个输入流缓冲区来读取响应信息
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        // 将响应信息读入字符串缓冲区
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        // 关闭输入流
        in.close();
        // 返回响应信息
        return response.toString();
    }
    public static String getForLNbits(String url, String apiKey, String body) throws Exception {
        // 创建URL对象
        URL obj = new URL(url);
        // 打开HTTP连接
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        // 设置请求头部参数
        con.setRequestProperty("Content-Type","application/json");
        con.setRequestProperty("X-Api-Key",apiKey);

        if(StringUtils.hasLength(body)) {
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.close();
        }

        // 获取响应码
        int responseCode = con.getResponseCode();
        // 打印输出信息
        log.info("Sending 'GET' request to URL : " + url);
        log.info("Response Code : " + responseCode);
        // 创建一个输入流缓冲区来读取响应信息
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        // 将响应信息读入字符串缓冲区
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        // 关闭输入流
        in.close();
        // 返回响应信息
        return response.toString();
    }

    public static void main(String[] args) {
        String url = "https://www.baidu.com";
        try {
            System.out.println(HttpUtil.get(url));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
