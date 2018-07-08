package wrapper.thread.paint;

import wrapper.StaticVariable;

import java.io.*;
import java.net.Socket;

public class PaintThread implements Runnable{
    private Socket socket;

    @Override
    public void run() {
            try {
                this.socket = new Socket(StaticVariable.FILE_SERVER_IP, StaticVariable.PAINT_SERVER_SOCKET_PORT);
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
