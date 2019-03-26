package live_UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import org.omg.CORBA.PRIVATE_MEMBER;

import live_Data.StaticData;
import live_Process.TcpClient;
import live_Process.TcpServer;

public class UI_HomePage extends JFrame {
	private String userName;
	private String liveName;
	private int liveRoomNumber = 0;

	private Thread thread_watch = null;
	private Thread thread_broadSocket = null;
	private Thread thread_live = null;

	// private Set<String> liveUserNameSet = new HashSet<>();
	private Map<String, RoomLabel> liveRoomMap = new HashMap<>();

	private final String BROADCAST_IP = "230.0.0.1"; // �㲥IP
	private final int BROADCAST_INT_PORT = 40005; // ��ͬ��port��Ӧ��ͬ��socket���Ͷ˺ͽ��ն�

	// private TcpServer tcpServer = new TcpServer(StaticData.getServerPort());
	private TcpServer tcpServer = new TcpServer();
	private TcpClient tcpClient = new TcpClient();

	MulticastSocket broadSocket; // ���ڽ��չ㲥��Ϣ
	DatagramSocket sender; // �������׽��� �൱����ͷ�����ڷ�����Ϣ
	InetAddress broadAddress; // �㲥��ַ

	private JPanel contentPanel = new JPanel();
	private JButton sButton = new JButton("�ۿ� / ˢ��"); // send
	private JButton rButton = new JButton("��ʼֱ��"); // receive
	private JButton cButton = new JButton("�˿�ѡ��");

	private JPanel sPanel = new JPanel();
	private JPanel rPanel = new JPanel();
	private int nowPage = 0;

	public UI_HomePage(String userName) {
		this.userName = userName;

		contentPanel.setLayout(null);
		sPanel.setLayout(null);
		rPanel.setLayout(null);

		add(sButton);
		add(rButton);
		add(cButton);
		add(sPanel);

		sButton.setBounds(30, 30, 120, 40);
		rButton.setBounds(180, 30, 120, 40);
		cButton.setBounds(600, 30, 120, 40);
		sPanel.setBounds(30, 100, 690, 340);
		sPanel.setBackground(Color.LIGHT_GRAY);
		sPanel.setBorder(new MatteBorder(2, 2, 2, 2, new Color(192, 192, 192)));

		getContentPane().add(contentPanel);

		sButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (nowPage == 1) {
					sendOffLiveMsg();
				}
				nowPage = 0;
				contentPanel.remove(rPanel);
				contentPanel.remove(sPanel);
				sPanel.removeAll(); // �Ƴ����з���
				liveRoomMap.clear();// ���mapӳ���ϵ
				liveRoomNumber = 0; // ���¼��㷿����
				contentPanel.add(sPanel);
				sPanel.setBounds(30, 100, 690, 340);
				sPanel.setBackground(Color.LIGHT_GRAY);
				sPanel.setBorder(new MatteBorder(2, 2, 2, 2, new Color(192, 192, 192)));
				// �����ѯ�¼�
				if (thread_live != null)
					thread_live.interrupt();
				if (thread_watch != null)
					thread_watch.interrupt(); // ?
				if (thread_broadSocket != null)
					thread_broadSocket.interrupt();
				tcpServer.stopWork();

				initialNetwork();
				join();
				thread_watch = startListenerThread_Watch();
				thread_broadSocket = startListenerBroadThread_Watch();
				thread_watch.start();
				thread_broadSocket.start();

				sendGetLiveMsg();
			}
		});

		rButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (nowPage == 1) {
					return;
				}
				nowPage = 1;
				contentPanel.remove(sPanel);
				contentPanel.add(rPanel);
				rPanel.setBounds(30, 100, 690, 340);
				rPanel.setBackground(Color.BLACK);
				rPanel.setBorder(new MatteBorder(2, 2, 2, 2, new Color(192, 192, 192)));
				if (thread_watch != null)
					thread_watch.interrupt();
				if (thread_live != null)
					thread_live.interrupt();

				initialNetwork();
				join();

				thread_live = startListenerThread_Live();
				thread_live.start();

				tcpClient = null;
				tcpServer = new TcpServer(StaticData.getServerPort());
				tcpServer.startWork();
			}
		});
	}

	void initialNetwork() {
		try {
			broadSocket = new MulticastSocket(BROADCAST_INT_PORT);
			broadAddress = InetAddress.getByName(BROADCAST_IP);

			sender = new DatagramSocket();
			System.out.println("���绷����ʼ���ɹ�");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("���绷����ʼ��ʧ��");
		}
	}

	void insertNewLiveRoom(String liveUserName, String liveName, String roomIP) {
		// sPanel:width-690,height-340
		if (liveRoomMap.containsKey(liveUserName))
			return;
		liveRoomNumber++;
		RoomLabel roomLabel = new RoomLabel(liveUserName, liveName, roomIP);
		roomLabel.setBounds(0, 2 * liveRoomNumber + (liveRoomNumber - 1) * 30, 690, 30);
		liveRoomMap.put(liveUserName, roomLabel);
		this.sPanel.add(roomLabel);
		this.sPanel.repaint();
		// this.contentPanel.repaint();
	}

	private class RoomLabel extends JLabel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String roomIP;
		private String liveUserName;
		private String liveName;
		private JLabel liveUserNameLabel;
		private JLabel liveNameLabel;
		private JButton enterButton;
		private JLabel offNotice;

		private RoomLabel(String liveUserName, String liveName, String roomIP) {
			super();
			this.roomIP = roomIP;
			this.liveUserName = liveUserName;
			this.liveName = liveName;
			this.liveUserNameLabel = new JLabel(liveUserName);
			this.liveNameLabel = new JLabel(liveName);
			this.enterButton = new JButton("����ֱ����");
			this.offNotice = new JLabel("�ѹز�");
			// each width 230
			this.liveUserNameLabel.setBounds(1, 0, 228, 30);
			this.liveNameLabel.setBounds(231, 0, 228, 30);
			this.enterButton.setBounds(460, 0, 228, 30);
			this.offNotice.setBounds(460, 0, 228, 30);

			this.liveUserNameLabel.setBackground(new Color(173, 216, 230));
			this.liveNameLabel.setBackground(new Color(173, 216, 230));
			this.enterButton.setBackground(new Color(255, 236, 139));
			this.offNotice.setBackground(new Color(173, 216, 230));

			this.offNotice.setForeground(Color.RED);

			this.add(liveUserNameLabel);
			this.add(liveNameLabel);
			this.add(enterButton);

			this.enterButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					// System.out.println(roomIP);
					tcpClient = new TcpClient();
					tcpClient.connectTcpServer(roomIP, StaticData.getServerPort());
					if (tcpClient.isConnected()) {
						// ���ӳɹ�
						UI_LiveRoom ui_LiveRoom = new UI_LiveRoom(tcpClient.getInputStream());
						ui_LiveRoom.startLive();
					}
				}
			});
		}

		public void setStatusAsOff() {
			this.remove(enterButton);
			this.add(offNotice);
		}
	}

	class broadSocket_Watch implements Runnable {
		private MulticastSocket socket;
		private DatagramPacket inPacket;
		private String[] message;

		public broadSocket_Watch(MulticastSocket socket) {
			super();
			this.socket = socket;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				inPacket = new DatagramPacket(new byte[1024], 1024);
				try {
					broadSocket.receive(inPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // ���չ㲥��Ϣ������Ϣ��װ��inPacket��
				message = new String(inPacket.getData(), 0, inPacket.getLength()).split("@");
				if (message[0].equals("offLive")) {
					String offUserName = message[1];
					deleteLiveRoom(offUserName);
				}
			}
		}
	}

	class GetPacket_Watch implements Runnable { // �½����߳�,��������
		private DatagramSocket socket;

		public GetPacket_Watch(DatagramSocket socket) {
			super();
			this.socket = socket;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			DatagramPacket inPacket;
			String message[] = null;
			while (true) {
				try {
					inPacket = new DatagramPacket(new byte[1024], 1024);
					socket.receive(inPacket); // ���չ㲥��Ϣ������Ϣ��װ��inPacket��
					message = new String(inPacket.getData()).split("@");
					// System.out.println(message);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("�̳߳���");
					e.printStackTrace();
				}
				if (message != null && message[0].charAt(0) == 'o') {
					switch (message[0]) {
					case "onLive":
						String liveUserName = message[1];
						String liveName = message[2];
						String roomIP = message[3];
						insertNewLiveRoom(liveUserName, liveName, roomIP);
						break;
					/*
					 * case "offLive": String offUserName = message[1]; deleteLiveRoom(offUserName);
					 * break;
					 */
					default:
						break;
					}
				}
			}
		}
	}

	class GetPacket_Live implements Runnable {
		private String userName;
		private String liveName;
		private DatagramSocket socket;

		public GetPacket_Live(String userName, String liveName, DatagramSocket socket) {
			super();
			this.userName = userName;
			this.liveName = liveName;
			this.socket = socket;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			DatagramPacket inPacket = null;
			DatagramPacket responsePacket;

			String message[] = null;
			while (true) {
				try {
					inPacket = new DatagramPacket(new byte[1024], 1024);
					broadSocket.receive(inPacket); // ���չ㲥��Ϣ������Ϣ��װ��inPacket��
					message = new String(inPacket.getData()).split("@");
					// System.out.println(message);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("�̳߳���");
					e.printStackTrace();
				}
				if (message != null && message[0].charAt(0) != 'o') {
					switch (message[0]) {
					case "isLive":
						String requestUserName = message[1];
						InetAddress requestUserIP = null;
						try {
							requestUserIP = InetAddress.getByName(message[2]);
							byte[] b = ("onLive@" + this.userName + "@" + this.liveName + "@" + StaticData.getIP())
									.getBytes();
							// responsePacket = new DatagramPacket(b, b.length, requestUserIP,
							// BROADCAST_INT_PORT);
							responsePacket = new DatagramPacket(b, b.length, inPacket.getAddress(), inPacket.getPort());
							this.socket.send(responsePacket);
							responsePacket = new DatagramPacket(b, b.length, requestUserIP, BROADCAST_INT_PORT);
							this.socket.send(responsePacket);
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					default:
						break;
					}
				}
			}
		}
	}

	Thread startListenerThread_Watch() {
		return new Thread(new GetPacket_Watch(this.sender)); // Ϊ�ۿ����½�һ���߳�,����ѭ�������˿���Ϣ
	}

	Thread startListenerBroadThread_Watch() {
		return new Thread(new broadSocket_Watch(this.broadSocket));
	}

	Thread startListenerThread_Live() {
		return new Thread(new GetPacket_Live(this.userName, this.liveName, this.sender)); // Ϊֱ�����½�һ���߳�,����ѭ�������˿���Ϣ
	}

	public void deleteLiveRoom(String offUserName) {
		// TODO Auto-generated method stub
		if (/* map_has(offUserName) */liveRoomMap.containsKey(offUserName)) {
			RoomLabel roomLabel = liveRoomMap.get(offUserName);
			roomLabel.setStatusAsOff();
			roomLabel.repaint();
			sPanel.repaint();
			liveRoomMap.remove(offUserName);
		}

	}

	/*
	 * private boolean map_has(String offUserName) { for (Map.Entry<String,
	 * RoomLabel> entry : this.liveRoomMap.entrySet()) { //
	 * Map.entry<Integer,String> ӳ�����-ֵ�ԣ� �м��������������������entry // entry.getKey()
	 * ;entry.getValue(); entry.setValue(); // map.entrySet() ���ش�ӳ���а�����ӳ���ϵ�� Set��ͼ��
	 * if (entry.getKey().equals(offUserName)) return true; } return false; }
	 */
	void join() {
		try {
			broadSocket.joinGroup(broadAddress); // ���뵽�鲥��ַ,�������ܽ��յ��鲥��Ϣ
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("�����鲥ʧ��");
			e.printStackTrace();
		}
	}

	void sendGetLiveMsg() {
		byte[] b = new byte[1024];
		DatagramPacket packet; // ���ݰ�,�൱�ڼ�װ��,��װ��Ϣ
		b = ("isLive@" + this.userName + "@" + StaticData.getIP()).getBytes();
		packet = new DatagramPacket(b, b.length, broadAddress, BROADCAST_INT_PORT); // �㲥��Ϣ��ָ���˿�
		try {
			sender.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("���ͳ���");
			e.printStackTrace();
		}
		System.out.println("�ѷ�������");
	}

	void returnOnLiveMsg(String ip) {
		byte[] b = new byte[1024];
		DatagramPacket packet;
		b = ("onLive@" + this.userName + "@" + this.liveName).getBytes();
		try {
			packet = new DatagramPacket(b, b.length, InetAddress.getByName(ip), BROADCAST_INT_PORT);
			sender.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("������Ӧ��Ϣʧ��");
			e.printStackTrace();
		}
		System.out.println("������Ӧ��Ϣ�ɹ�");
	}

	// ��������ĳ��������ʱ��Ҫ�㲥��������֪ͨ
	void sendOffLiveMsg() {
		byte[] b = new byte[1024];
		DatagramPacket packet;
		b = ("offLive@" + this.userName).getBytes();
		packet = new DatagramPacket(b, b.length, broadAddress, BROADCAST_INT_PORT);
		try {
			sender.send(packet);
			System.out.println("������");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("����������Ϣʧ��");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		UI_HomePage ui_A = new UI_HomePage("aaa");
		UI_HomePage ui_B = new UI_HomePage("bbb");

		ui_A.initialNetwork();
		ui_A.join();
		ui_A.startListenerThread_Watch().start();

		ui_B.initialNetwork();
		ui_B.join();
		ui_B.sendGetLiveMsg();
		try {
			Thread.sleep(5000);
			System.out.println("5s passed");
		} catch (InterruptedException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		ui_B.sendOffLiveMsg();

	}
}
