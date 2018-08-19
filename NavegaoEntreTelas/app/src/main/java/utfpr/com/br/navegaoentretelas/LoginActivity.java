package utfpr.com.br.navegaoentretelas;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Developer Vinicius Cavalcanti
 * @version 0.1.0
 * @since 0.1.0 in 18/08/18
 */
public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;

    private TextInputLayout layoutEmail;
    private TextInputLayout layoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);

        layoutEmail = findViewById(R.id.input_layout_email);
        layoutPassword = findViewById(R.id.input_layout_password);

        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        Button signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(click -> submitForm());
    }


    private void submitForm() {
        if (validateEmail() && validatePassword() ) {
            Intent intent = new Intent(LoginActivity.this, ProgressActivity.class);
            startActivity(intent);
        }
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (!isValidEmail(email)) {
            layoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else if (email.isEmpty()) {
            layoutEmail.setError(getString(R.string.err_msg_email_is_empty));
            requestFocus(inputEmail);
        } else {
            layoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            layoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            layoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.email:
                    validateEmail();
                    break;
                case R.id.password:
                    validatePassword();
                    break;
            }
        }
    }
}
