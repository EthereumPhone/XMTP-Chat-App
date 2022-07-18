package org.ethereumphone.testxmtp3;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.liquidplayer.javascript.JSContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //JSContext context = new JSContext();
        String content = "";
        try {
            content = getAssetContent("init.js");
        } catch (IOException e) {
            e.printStackTrace();
        }
        wv = new WebView(this);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setDomStorageEnabled(true); // Turn on DOM storage
        wv.getSettings().setAppCacheEnabled(true); //Enable H5 (APPCache) caching
        wv.getSettings().setDatabaseEnabled(true);


        System.out.println("Array length: "+content.split("\r\n|\r|\n").length);
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                android.util.Log.d("WebView", consoleMessage.message());
                return true;
            }
        });
        //context.evaluateScript(content);
        //wv.loadUrl("https://google.com");

        StringBuilder output = new StringBuilder();
        output.append("<script type='text/javascript' type='module'>\n");
        output.append(content);
        output.append("</script>");
        wv.loadDataWithBaseURL("file:///android_asset/index.html", output.toString(), "text/html", "utf-8", null);
        /*
        wv.evaluateJavascript(content, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                System.out.println("WebView: "+s);
            }
        });
         */
    }

    private String getAssetContent(String filename) throws IOException {
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(filename), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                stringBuilder.append(mLine);
                stringBuilder.append(System.getProperty("line.separator"));
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return "";
    }

    public void sendMessage(View view){
        EditText editText = findViewById(R.id.textInput);
        EditText addressInput = findViewById(R.id.editTextTextPersonName2);
        String content = "";
        try {
            content = getAssetContent("sendMessage.js");
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder output = new StringBuilder();
        output.append("<script type='text/javascript' type='module'>\n");
        output.append(content);
        //output.append("import {sendMessage} from \"index.js\";sendMessage(\""+editText.getText().toString()+"\", \"0x2374eFc48c028C98e259a7bBcba336d6acFF103c\");\n");
        output.append("</script>\n");
        String jsOut = output.toString().replace("%message%", editText.getText().toString()).replace("%target%", addressInput.getText().toString());
        //output.append("<script type='text/javascript' type='module'>\n");
        //output.append("</script>\n");
        this.wv.loadDataWithBaseURL("file:///android_asset/index.html", jsOut, "text/html", "utf-8", null);
        //this.wv.loadUrl("javascript:sendMessage(\""+editText.getText().toString()+"\", \"0x2374eFc48c028C98e259a7bBcba336d6acFF103c\");");
    }


}