package com.fuyun.network.tcpholepunching.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fuyun on 16-5-23.
 * 客户端
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        SocketAddress localAddr = new InetSocketAddress(1234);
        SocketAddress remoteAddr = new InetSocketAddress("vps.fuyun.com", 7070);
        socket.bind(localAddr);
        socket.setReuseAddress(true);
        socket.connect(remoteAddr, 10000);
        Scanner scanner = new Scanner(socket.getInputStream());
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> System.out.println(scanner.nextLine()));
        executorService.shutdown();

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println("echo");
        printWriter.println("close");
    }
}
