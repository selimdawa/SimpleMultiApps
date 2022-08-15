package com.flatcode.simplemultiapps.PdfReader;

import static java.net.HttpURLConnection.HTTP_OK;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.flatcode.simplemultiapps.PdfReader.Activity.PdfReaderActivity;
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.VOID;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.SSLException;

public class DownloadPDFFile extends AsyncTask<String, Void, Object> {

    private final WeakReference<PdfReaderActivity> mainActivityWR;

    public DownloadPDFFile(PdfReaderActivity activity) {
        mainActivityWR = new WeakReference<>(activity);
    }

    @Override
    protected Object doInBackground(String... strings) {
        String url = strings[0];
        HttpURLConnection httpConnection = null;

        try {
            httpConnection = (HttpURLConnection) new URL(url).openConnection();
            httpConnection.connect();
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HTTP_OK) {
                return VOID.readBytesToEnd(httpConnection.getInputStream());
            } else {
                Log.e("DownloadPDFFile", "Error during http request, response code : " + responseCode);
                return responseCode;
            }
        } catch (IOException e) {
            Log.e("DownloadPDFFile", "Error cannot get file at URL : " + url, e);
            return e;
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        PdfReaderActivity activity = mainActivityWR.get();

        if (activity != null) {
            activity.hideProgressBar();

            if (result == null) {
                Toast.makeText(activity, R.string.toast_generic_download_error, Toast.LENGTH_LONG).show();
            } else if (result instanceof Integer) {
                Toast.makeText(activity, R.string.toast_http_code_error, Toast.LENGTH_LONG).show();
            } else if (result instanceof SSLException) {
                Toast.makeText(activity, R.string.toast_ssl_error, Toast.LENGTH_LONG).show();
            } else if (result instanceof IOException) {
                Toast.makeText(activity, R.string.toast_generic_download_error, Toast.LENGTH_LONG).show();
            } else if (result instanceof byte[]) {
                activity.saveToFileAndDisplay((byte[]) result);
            }
        }
    }
}
