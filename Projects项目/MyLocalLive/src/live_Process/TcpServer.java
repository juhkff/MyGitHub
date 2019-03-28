package live_Process;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class TcpServer {
    private ServerSocket server;
    private Socket client = null;
    private Thread thread = null;

    public TcpServer(int port) {
        super();
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public TcpServer() {
        // TODO Auto-generated constructor stub
    }

    public void startWork() {
        this.thread = new Thread(new serverThread(server));
        this.thread.start();
    }

    public void stopWork() {
        try {
            if (this.server != null)
                this.server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.thread != null) {
            this.thread.interrupt();
        }
    }

    private class serverThread implements Runnable {
        private ServerSocket server;
        private Socket client;

        public serverThread(ServerSocket server) {
            super();
            this.server = server;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                try {
                    if (!this.server.isClosed()) {
                        client = this.server.accept(); // 等待客户端的连接
                        System.out.println("与客户端连接成功!");
                        new Thread(new TransmitThread(client)).start();
                    } else
                        Thread.currentThread().interrupt();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }

        private class TransmitThread implements Runnable {
            private Socket client;
            private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle scRectangle = new java.awt.Rectangle(screenSize);
            Robot robot;
            BufferedImage image;

            public TransmitThread(Socket socket) {
                super();
                this.client = socket;
                try {
                    this.robot = new Robot();
                } catch (AWTException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    PrintStream out = new PrintStream(this.client.getOutputStream()); // 获取Socket的输入流,用来向客户端发送数据
                    BufferedReader buf = new BufferedReader(new InputStreamReader(this.client.getInputStream())); // 获取Socket的输入流,用来接收从客户端发送过来的数据
                    if (buf.readLine().equals("TestConnect")) {
                        for (int i = 0; i < 50; i++)
                            out.println("Succeed" + i);
                    }
                    out.println("GO");
                    // OutputStream outputStream = this.client.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(this.client.getOutputStream());
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] b;
                    int i = 0;
                    while (true) {
                        i++;
                        //System.out.print("进行第" + i + "次截屏\t\t");
                        byteArrayOutputStream.reset();
                        image = robot.createScreenCapture(scRectangle);

                        ImageIO.write(image, "JPG", byteArrayOutputStream);
                        b = byteArrayOutputStream.toByteArray();
                        // new FileOutputStream("D:\\新建文件夹 (3)\\新建文件夹\\" + i + ".jpg").write(b);
                        // ImageIO.write(image, "JPG", outputStream);
                        dataOutputStream.write(b);
                        /*
                         * try { Thread.sleep(500); } catch (InterruptedException e) { // TODO
                         * Auto-generated catch block e.printStackTrace(); }
                         */
                        System.out.println(i + "次截屏成功");
                        Thread.sleep(100);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                    if (thread != null) {
                        thread.interrupt();
                    }
                    try {
                        if (server != null)
                            server.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    Thread.currentThread().interrupt();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
