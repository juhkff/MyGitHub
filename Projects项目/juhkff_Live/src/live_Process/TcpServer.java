package live_Process;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
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
		if (this.thread != null)
			this.thread.interrupt();
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
					client = this.server.accept(); // �ȴ��ͻ��˵�����
					System.out.println("��ͻ������ӳɹ�!");
					new Thread(new TransmitThread(client)).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
					PrintStream out = new PrintStream(this.client.getOutputStream()); // ��ȡSocket��������,������ͻ��˷�������
					BufferedReader buf = new BufferedReader(new InputStreamReader(this.client.getInputStream())); // ��ȡSocket��������,�������մӿͻ��˷��͹���������
					if (buf.readLine().equals("TestConnect")) {
						for (int i = 0; i < 50; i++)
							out.println("Succeed" + i);
					}
					out.println("GO");
					OutputStream outputStream = this.client.getOutputStream();
					int i = 0;
					while (true) {
						i++;
						System.out.print("���е�" + i + "�ν���\t\t");
						image = robot.createScreenCapture(scRectangle);
						ImageIO.write(image, "JPG", outputStream);
						/*
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
						System.out.println(i + "�ν����ɹ�");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}
}
