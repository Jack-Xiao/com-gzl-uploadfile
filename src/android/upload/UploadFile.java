package org.apache.cordova.upload;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Jack on 16/5/26.
 */
public class UploadFile extends CordovaPlugin {
    public static final int UPLOAD_REQUEST = 1;
    public static final String ACTION_UPLOAD_PHOTO = "uploadFile";
    private final String IMAGE_TYPE = "image/*";
    private CallbackContext mCallbackContext;


    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        //Context context = this.cordova.getActivity().getApplicationContext();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if(ACTION_UPLOAD_PHOTO.equals(action)){
            //callbackContext.success();
            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType(IMAGE_TYPE);
            mCallbackContext = callbackContext;
            this.cordova.getActivity().startActivityForResult(getAlbum,UPLOAD_REQUEST);
            return true;
        }
        return super.execute(action, args, callbackContext);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == UPLOAD_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                Bitmap bitmap;
                ContentResolver resolver = this.cordova.getActivity().getContentResolver();
                Uri uri = intent.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(resolver,uri);
                } catch (IOException e) {
                    e.printStackTrace();
                    mCallbackContext.error(e.getMessage());
                    return;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                mCallbackContext.success(baos.toByteArray());
            }
        }

    }
}
