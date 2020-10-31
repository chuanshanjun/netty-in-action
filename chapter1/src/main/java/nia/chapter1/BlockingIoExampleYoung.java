package nia.chapter1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author:ChuanShanJun
 * @date:2020/10/31
 * @description:
 */
public class BlockingIoExampleYoung {

    public void Server(int portNum) throws IOException {
        // 创建一个新的ServerSocket，用来监听指定端口上的连接请求
        ServerSocket serverSocket = new ServerSocket(portNum);
        // 对accept()方法的调用将被阻塞，直到创建一个新的连接
        Socket clientSocket = serverSocket.accept();
        // 这些流对象都派生于该套接字的流对象
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        String request, response;
        // 处理循环开始
        while ((request = in.readLine()) != null) {
            // 如果客户端发送了"Done"，则退出处理循环
            if ("Done".equals(request)) {
                break;
            }
            // 请求被传递给服务器处理方法
            response = process(request);
            // 服务器的响应被发送给了客户端
            out.println(response);
            // 继续执行处理循环
        }
    }

    public String process(String request) {
        return "Processed";
    }
}
