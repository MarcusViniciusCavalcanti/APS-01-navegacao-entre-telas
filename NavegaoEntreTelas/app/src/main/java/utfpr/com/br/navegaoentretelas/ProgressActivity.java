package utfpr.com.br.navegaoentretelas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

/**
 * @author Developer Vinicius Cavalcanti
 * @version 0.1.0
 * @since 0.1.0 in 18/08/18
 */
public class ProgressActivity extends AppCompatActivity {
    private TextView textView;
    private Thread thread;

    private final Animation in = new AlphaAnimation(0.0f, 1.0f);
    private final Animation out = new AlphaAnimation(1.0f, 0.0f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        textView = findViewById(R.id.textView);

        in.setDuration(2000);
        out.setDuration(1000);

        thread = new ProgressThread(new MessageHandler(this));
        thread.start();
    }

    private void loginIsSuccess() {
        if (thread.isAlive()) {
            thread.interrupt();
        }

        Intent intent = new Intent(ProgressActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

    private void postMessage(String msg) {
        textView.startAnimation(out);
        textView.setText(msg);
        textView.startAnimation(in);
    }

    static class MessageHandler extends Handler {
        private final ProgressActivity parent;

        public static final int CONNECTED_SERVER = 0;
        public static final int CHECK_CREDENTIALS = 1;
        public static final int SUCCESS_LOGIN = 2;

        MessageHandler(ProgressActivity parent) {
            this.parent = parent;
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case CONNECTED_SERVER:
                    parent.postMessage(parent.getString(R.string.connected_from_server));
                    break;
                case CHECK_CREDENTIALS:
                    parent.postMessage(parent.getString(R.string.checked_credentials));
                    break;
                case SUCCESS_LOGIN:
                    parent.loginIsSuccess();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
