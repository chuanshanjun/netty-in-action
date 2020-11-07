package nia.chapter4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author:ChuanShanJun
 * @date:2020/11/1
 * @description:
 */
public class PlainOioServerYoung {
    public void server(int port) throws IOException {
        // 将服务器绑定到指定端口
        final ServerSocket socket = new ServerSocket(port);
        for (;;) {
            // 接收连接
            final Socket clientSocket = socket.accept();
            System.out.println("Accept connection from " + clientSocket);
            // 创建一个新的线程来处理该连接
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OutputStream out;
                    try {
                        out = clientSocket.getOutputStream();
                        // 将消息写给已连接的客户端
                        out.write("Hi \r\n".getBytes(Charset.forName("UTF-8")));
                        out.flush();
                        // 关闭连接
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start(); // 启动连接
        }
    }
}
