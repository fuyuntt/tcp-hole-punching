package com.fuyun.network.tcpholepunching.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by fuyun on 16-5-22.
 *
 * 服务端程序
 */
public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new EchoAddr(7070));
        executorService.submit(new EchoAddr(7071));
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("所有的任务都退出了.");
    }

    private static class EchoAddr implements Runnable {
        private int listenPort;

        EchoAddr(int listenPort) {
            this.listenPort = listenPort;
        }

        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(listenPort);
                System.out.println("正在监听端口:" + listenPort);
                outer: while (true) {
                    Socket socket = serverSocket.accept();
                    String place = socket.getInetAddress().getHostAddress() + ";" + socket.getPort();
                    System.out.println("port " + listenPort + " 收到连接:" + place);
                    Scanner scanner = new Scanner(socket.getInputStream());
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    while (true) {
                        String command = scanner.nextLine();
                        if ("echo".equals(command)) {
                            printWriter.println(place);
                            printWriter.flush();
                        } else if ("close".equals(command)) {
                            System.out.println("关闭连接:" + place);
                            socket.close();
                            break;
                        } else if ("quit".equals(command)) {
                            socket.close();
                            break outer;
                        } else {
                            System.out.println("无效命令:" + command);
                        }
                    }
                }
                System.out.println("停止监听端口:" + listenPort);
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
