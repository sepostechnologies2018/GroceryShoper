package com.app_web.technology.groceryshopper.Activitys;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.VolleyMethods;
import java.util.HashMap;
public class Sign_up extends AppCompatActivity implements View.OnClickListener {

    Button btnSubmit;
    LinearLayout llHome, llBack;
    TextView txtCategoryName;
    TextView txtQuantity;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut;
    EditText edtFullName, edtMailId, edtPassword, edtCofirmPassword, edtPhoneNumber, edtHouseNumber, edtStreet, edtCity, edtPincode, edtState;
    private TextInputLayout inputLayoutEmail;
    TextView login;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        init();
        btnSubmit.setOnClickListener(this);
        imageView.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    //Method to reference XML widgets
    public void init() {
        btnSubmit = (Button) findViewById(R.id.btn_submit_registration);
        edtFullName = (EditText) findViewById(R.id.edt_full_name_registration);
        edtMailId = (EditText) findViewById(R.id.edt_email_id_registration);
        edtPassword = (EditText) findViewById(R.id.edt_password_registration);
        edtCofirmPassword = (EditText) findViewById(R.id.edt_confirm_password_registration);
        edtPhoneNumber = (EditText) findViewById(R.id.edt_phone_no_registration);
        edtHouseNumber = (EditText) findViewById(R.id.edt_house_registration);
        edtStreet = (EditText) findViewById(R.id.edt_street_registration);
        edtCity = (EditText) findViewById(R.id.edt_city_registration);
        edtPincode = (EditText) findViewById(R.id.edt_pin_code_registration);
        edtState = (EditText) findViewById(R.id.edt_state_registration);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llHome = (LinearLayout) findViewById(R.id.ll_home);
        txtCategoryName = (TextView) findViewById(R.id.txt_activity_name);
        txtQuantity = (TextView) findViewById(R.id.txt_item_quantity);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        edtMailId.addTextChangedListener(new MyTextWatcher(edtMailId));
        imageView=(ImageView)findViewById(R.id.finish);
    }

    //Click Listener of XML Widgewts
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish: {
                finish();
            }
            break;
            case R.id.btn_submit_registration: {

                if (isRegistrationDataOK()) {
                    //Perform Action Registration Data is Ok
                    if (isPasswordMatching()) {
                        //Code if Password Matching
                        VolleyMethods vm = new VolleyMethods(this);
                        HashMap<String, String> userInfo = new HashMap<String, String>();
                        userInfo.put("full_name", edtFullName.getText().toString());
                        userInfo.put("mail_id", edtMailId.getText().toString());
                        userInfo.put("password", edtPassword.getText().toString());
                        userInfo.put("contact_no", edtPhoneNumber.getText().toString());
                        userInfo.put("house_no", edtHouseNumber.getText().toString());
                        userInfo.put("street", edtStreet.getText().toString());
                        userInfo.put("city", edtCity.getText().toString());
                        userInfo.put("post", edtPincode.getText().toString());
                        vm.registerUser(userInfo);
                    } else {

                    }
                } else {

                }
            }
        }
    }



    private boolean validateEmail() {
        String email = edtMailId.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(edtMailId);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    //Method to check is registration Data OK or not
    public boolean isRegistrationDataOK() {
        boolean isOk;

        if (edtFullName.getText().toString().trim().equals("") || edtFullName.getText().toString().equals("Full name")) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
            edtFullName.setError("Please enter name");
            isOk = false;
        } else {
            if (edtMailId.getText().toString().trim().equals("") || edtMailId.getText().toString().equals("Email id")) {
                Toast.makeText(this, "Please enter mail id", Toast.LENGTH_LONG).show();
                edtMailId.setError("Please enter mail id");
                isOk = false;
            } else {
                if (edtPassword.getText().toString().trim().equals("") || edtPassword.getText().toString().equals("Password")) {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
                    edtPassword.setError("Please enter password");
                    isOk = false;
                } else {
                    if (edtCofirmPassword.getText().toString().trim().equals("") || edtCofirmPassword.getText().toString().equals("Confirm password")) {
                        Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_LONG).show();
                        edtCofirmPassword.setError("Please enter confirm password");

                        isOk = false;
                    } else {
                        if (edtPhoneNumber.getText().toString().trim().equals("") || edtPhoneNumber.getText().toString().equals("Contact no")) {
                            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_LONG).show();
                            edtPhoneNumber.setError("Please enter phone number");
                            isOk = false;
                        } else {
                            //Checking Delivery Address
                            if (isDeliveryAddressOk()) {
                                //Perform Action Delivery Address is OK
                                isOk = true;
                            } else {
                                //Do Nothing Delivery address is not OK
                                isOk = false;
                            }
                        }
                    }
                }
            }
        }
        return isOk;
    }

    //Method to Check Delivery Address
    public boolean isDeliveryAddressOk() {
        boolean isDeliveryAddressOK;

        if (edtHouseNumber.getText().toString().trim().equals("") || edtHouseNumber.getText().toString().trim().equals("House")) {
            Toast.makeText(this, "Please enter house number", Toast.LENGTH_LONG).show();
            edtHouseNumber.setError("Please enter house number");
            isDeliveryAddressOK = false;
        } else {
            if (edtStreet.getText().toString().trim().equals("") || edtStreet.getText().toString().trim().equals("Street")) {
                Toast.makeText(this, "Please enter street", Toast.LENGTH_LONG).show();
                edtStreet.setError("Please enter street");
                isDeliveryAddressOK = false;
            } else {
                if (edtCity.getText().toString().trim().equals("") || edtCity.getText().toString().trim().equals("City")) {
                    Toast.makeText(this, "Please enter city", Toast.LENGTH_LONG).show();
                    edtCity.setError("Please enter city");
                    isDeliveryAddressOK = false;
                } else {
                    if (edtPincode.getText().toString().trim().equals("") || edtPincode.getText().toString().trim().equals("Pin code")) {
                        Toast.makeText(this, "Please enter pin code", Toast.LENGTH_LONG).show();
                        edtPincode.setError("Please enter pin code");
                        isDeliveryAddressOK = false;
                    } else {
                        if (edtPincode.getText().toString().trim().equals("") || edtPincode.getText().toString().trim().equals("Pin code")) {
                            Toast.makeText(this, "Please enter pin code", Toast.LENGTH_LONG).show();
                            edtPincode.setError("Please enter Post code");
                            isDeliveryAddressOK = false;
                        } else {
                            //Delivery Address is OK
                            isDeliveryAddressOK = true;
                        }
                    }
                }
            }
        }
        return isDeliveryAddressOK;
    }

    //Code to check is password matching or not
    public boolean isPasswordMatching() {


        if (edtPassword.getText().toString().equals(edtCofirmPassword.getText().toString()))
            return true;
        else

            Toast.makeText(this, "Password not match", Toast.LENGTH_LONG).show();
        return false;
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

                case R.id.edt_email_id_registration:
                    validateEmail();
                    break;

            }
        }
    }
}
