package live_Data;

import live_Process.LocalAddress;

public class StaticData {
	private static String IP = LocalAddress.getLocalIP();
	private static String HOSTNAME = LocalAddress.getLocalHostName();
	private static final int CLIENT_PORT = 20023;
	private static final int SERVER_PORT = 20024;

	public static String getIP() {
		return IP;
	}

	public static String getHOSTNAME() {
		return HOSTNAME;
	}

	public static int getClientPort() {
		return CLIENT_PORT;
	}

	public static int getServerPort() {
		return SERVER_PORT;
	}

	public static void main(String[] args) {
		// System.out.println(new StaticData().getIP());
		// System.out.println(new StaticData().getHOSTNAME());
		System.out.println(LocalAddress.getLocalIP());
		System.out.println(LocalAddress.getLocalHostName());
	}
}
