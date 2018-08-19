package utfpr.com.br.navegaoentretelas;

import android.os.Message;

/**
 * @author Developer Vinicius Cavalcanti
 * @version 0.1.0
 * @since 0.1.0 in 18/08/18
 */
public class ProgressThread extends Thread {

    private final ProgressActivity.MessageHandler messageHandler;

    public ProgressThread(ProgressActivity.MessageHandler messageHandler) {
        super();
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        sendMessageForActivity(ProgressActivity.MessageHandler.CONNECTED_SERVER);

        try {
            Thread.sleep(2000);
            sendMessageForActivity(ProgressActivity.MessageHandler.CHECK_CREDENTIALS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
            sendMessageForActivity(ProgressActivity.MessageHandler.SUCCESS_LOGIN);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageForActivity(int connectedServer) {
        messageHandler.sendMessage(Message.obtain(messageHandler, connectedServer));
    }
}
