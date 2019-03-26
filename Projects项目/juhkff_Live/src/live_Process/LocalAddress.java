package live_Process;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class LocalAddress {
	private static InetAddress inetAddress = getLocalHostLanAddress();
	private static String localIP = inetAddress.getHostAddress();
	private static String localHostName = inetAddress.getHostName();

	public LocalAddress() {
		InetAddress ip = null;
		try {
			ip = getLocalHostLanAddress("");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		localIP = ip.getHostAddress();
		localHostName = ip.getHostName();
	}

	private static InetAddress getLocalHostLanAddress() {
		try {
			return getLocalHostLanAddress("");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inetAddress;
	}

	public static String getLocalIP() {
		return localIP;
	}

	public static String getLocalHostName() {
		return localHostName;
	}

	private static InetAddress getLocalHostLanAddress(String nothing) throws UnknownHostException {
		try {
			InetAddress candidateAddress = null;
			// �������� ����ӿ�
			for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
				NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
				// �����еĽӿ����ٱ���IP
				System.out.println(iface.getName());
				//if(iface.getName().startsWith("eth"))
				if(iface.getDisplayName().startsWith("VM"))
					continue;
				for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
					InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
					if (!inetAddr.isLoopbackAddress()) { // �ų�loopback���͵�ַ
						if (inetAddr.isSiteLocalAddress()||inetAddr.getHostAddress().startsWith("121.")) {
							// �����site-local��ַ����������
							return inetAddr;
						} else if (candidateAddress == null) {
							// site-local���͵ĵ�ַδ�����֣��ȼ�¼��ѡ��ַ
							candidateAddress = inetAddr;
						}
					}
				}
			}
			if (candidateAddress != null) {
				return candidateAddress;
			}

			// ���û�з���non-loopback��ַ,ֻ�������ѡ�ķ���
			InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
			if (jdkSuppliedAddress == null)
				throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
			return jdkSuppliedAddress;
		} catch (Exception e) {
			UnknownHostException unknownHostException = new UnknownHostException(
					"Failed to determine LAN address: " + e);
			unknownHostException.initCause(e);
			throw unknownHostException;
		}
	}
}
