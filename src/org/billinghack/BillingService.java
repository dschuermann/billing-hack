package org.billinghack;

import java.util.ArrayList;

import org.billinghack.google.util.IabHelper;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.android.vending.billing.IInAppBillingService;

public class BillingService extends Service {

    public static final String TAG = "BillingHack";

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IInAppBillingService.Stub mBinder = new IInAppBillingService.Stub() {

        @Override
        public int isBillingSupported(int apiVersion, String packageName, String type)
                throws RemoteException {
            Log.d(TAG, "isBillingSupported");

            return IabHelper.BILLING_RESPONSE_RESULT_OK;
        }

        @Override
        public Bundle getSkuDetails(int apiVersion, String packageName, String type,
                Bundle skusBundle) throws RemoteException {
            Log.d(TAG, "getSkuDetails");

            Bundle bundle = new Bundle();
            bundle.putInt(IabHelper.RESPONSE_CODE, IabHelper.BILLING_RESPONSE_RESULT_OK);
            bundle.putStringArrayList(IabHelper.RESPONSE_GET_SKU_DETAILS_LIST,
                    new ArrayList<String>());
            return bundle;
        }

        @Override
        public Bundle getBuyIntent(int apiVersion, String packageName, String sku, String type,
                String developerPayload) throws RemoteException {
            Log.d(TAG, "getBuyIntent");
            Log.d(TAG, "apiVersion: " + apiVersion);
            Log.d(TAG, "packageName: " + packageName);
            Log.d(TAG, "sku: " + sku);
            Log.d(TAG, "type: " + type);
            Log.d(TAG, "developerPayload: " + developerPayload);

            Bundle bundle = new Bundle();
            bundle.putInt(IabHelper.RESPONSE_CODE, IabHelper.BILLING_RESPONSE_RESULT_OK);

            PendingIntent pendingIntent;
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), BuyActivity.class);
            intent.setAction(BuyActivity.BUY_INTENT);
            intent.putExtra(BuyActivity.EXTRA_PACKAGENAME, packageName);
            intent.putExtra(BuyActivity.EXTRA_PRODUCT_ID, sku);
            intent.putExtra(BuyActivity.EXTRA_DEV_PAYLOAD, developerPayload);
            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            bundle.putParcelable(IabHelper.RESPONSE_BUY_INTENT, pendingIntent);

            return bundle;
        }

        @Override
        public Bundle getPurchases(int apiVersion, String packageName, String type,
                String continuationToken) throws RemoteException {
            Log.d(TAG, "getPurchases");

            Bundle bundle = new Bundle();
            bundle.putInt(IabHelper.RESPONSE_CODE, IabHelper.BILLING_RESPONSE_RESULT_OK);
            bundle.putStringArrayList(IabHelper.RESPONSE_INAPP_ITEM_LIST, new ArrayList<String>());
            bundle.putStringArrayList(IabHelper.RESPONSE_INAPP_PURCHASE_DATA_LIST,
                    new ArrayList<String>());
            bundle.putStringArrayList(IabHelper.RESPONSE_INAPP_SIGNATURE_LIST,
                    new ArrayList<String>());

            return bundle;
        }

        @Override
        public int consumePurchase(int apiVersion, String packageName, String purchaseToken)
                throws RemoteException {
            Log.d(TAG, "consumePurchase");

            return IabHelper.BILLING_RESPONSE_RESULT_OK;
        }

    };
}
