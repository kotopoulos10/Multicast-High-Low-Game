
public class readGameWriteTerminalChatThread implements Runnable {

	public ChatterUtilities chatUtilities;

	public readGameWriteTerminalChatThread(ChatterUtilities chatUtilities) {
		this.chatUtilities = chatUtilities;
	}

	public void run() {
		String fromSocket = null;
		try {
			while (true) {
				fromSocket = chatUtilities.readFromGameSocket();
				// System.out.println(Thread.currentThread().getName() + "
				// receiving");
//				System.out.println(chatUtilities.getName() + " receiving from port # " + chatUtilities.chatPORT);
				chatUtilities.writeToTerminal(fromSocket);
			}

		} catch (Exception E) {
		}
	}
}
