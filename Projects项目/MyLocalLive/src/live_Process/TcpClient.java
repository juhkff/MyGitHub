package live_Process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpClient {
	private Socket client = null;
	private boolean isConnected = false;

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public Socket getClient() {
		return client;
	}

	public InputStream getInputStream() {
		try {
			return client.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void connectTcpServer(InetAddress address, int port) {
		try {
			client = new Socket(address, port);
			PrintStream out = new PrintStream(client.getOutputStream()); // 获取Socket的输出流,用来发送数据到服务端
			BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream())); // 获取Socket的输入流,用来接收从服务端发送过来的数据
			test(buf, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connectTcpServer(String address, int port) {
		InetAddress iAddress = null;
		try {
			iAddress = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connectTcpServer(iAddress, port);
	}

	private void test(BufferedReader buf, PrintStream out) {
		String echo;
		out.println("TestConnect");
		while (true) {
			try {
				echo = buf.readLine();
				isConnected = true;
				System.out.println(echo);
				if (echo.equals("GO"))
					break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
