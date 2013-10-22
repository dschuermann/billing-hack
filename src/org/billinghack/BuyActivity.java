package org.billinghack;

import org.billinghack.google.util.IabHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BuyActivity extends Activity {

    public static final String TAG = "BillingHack";

    public static final String BUY_INTENT = "org.billinghack.BUY";
    public static final String EXTRA_PACKAGENAME = "packageName";
    public static final String EXTRA_PRODUCT_ID = "product";
    public static final String EXTRA_DEV_PAYLOAD = "payload";

    String pData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BUY_INTENT.equals(getIntent().getAction())) {
            Log.d(TAG, "Buy intent!");

            setContentView(R.layout.buy_dialog);

            String packageName = getIntent().getExtras().getString(EXTRA_PACKAGENAME);
            String productId = getIntent().getExtras().getString(EXTRA_PRODUCT_ID);
            String devPayload = getIntent().getExtras().getString(EXTRA_DEV_PAYLOAD);
            Log.d(TAG, "packageName: " + packageName);
            Log.d(TAG, "productId: " + productId);
            Log.d(TAG, "devPayload: " + devPayload);

            pData = "{'orderId':'12999763169054705758.1371079406387615', 'packageName':'"
                    + packageName
                    + "', 'productId':'"
                    + productId
                    + "', 'purchaseTime':1345678900000, 'purchaseToken' : '122333444455555', 'developerPayload':'"
                    + devPayload + "'}";
            Log.d(TAG, "INAPP_PURCHASE_DATA:\n" + pData);

            Button btnYes = (Button) findViewById(R.id.button_yes);
            Button btnNo = (Button) findViewById(R.id.button_no);

            btnYes.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent data = new Intent();
                    Bundle extras = new Bundle();
                    extras.putInt(IabHelper.RESPONSE_CODE, IabHelper.BILLING_RESPONSE_RESULT_OK);
                    extras.putString(IabHelper.RESPONSE_INAPP_PURCHASE_DATA, pData);
                    extras.putString(IabHelper.RESPONSE_INAPP_SIGNATURE, "");
                    data.putExtras(extras);

                    setResult(RESULT_OK, data);
                    finish();

                }
            });

            btnNo.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent data = new Intent();
                    Bundle extras = new Bundle();
                    extras.putInt(IabHelper.RESPONSE_CODE,
                            IabHelper.BILLING_RESPONSE_RESULT_USER_CANCELED);
                    data.putExtras(extras);

                    setResult(RESULT_CANCELED, data);
                    finish();
                }
            });
        } else {
            finish();
        }
    }

}
