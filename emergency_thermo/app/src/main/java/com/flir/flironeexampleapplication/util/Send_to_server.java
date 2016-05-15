package com.flir.flironeexampleapplication.util;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import com.flir.flironeexampleapplication.R;
import com.flir.flironeexampleapplication.live_chat.LiveChat_frag;
import com.github.nkzawa.socketio.client.Socket;

/**
 * Created by albertan on 5/14/16.
 */
public class Send_to_server  extends AsyncTask<String, Integer, String> {

    Activity activity;
    View rootView;
    String  pro_pic_url;
    private Socket mSocket;



    public Send_to_server(String pro_pic_url,  Activity activity){

        this.activity = activity;

        this.pro_pic_url = pro_pic_url;

        rootView = ((Activity) activity).getWindow().getDecorView().findViewById(android.R.id.content);

       Toast tt = Toast.makeText(activity, "uri : "+pro_pic_url, Toast.LENGTH_SHORT);
        tt.show();

    }

    @Override
    protected String doInBackground(String... params) {

        try {


            File sourceFile = new File(pro_pic_url.toString());

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            FileBody fileBody = new FileBody(sourceFile);

            if(!pro_pic_url.isEmpty()) {

                builder.addPart("file", fileBody);

            }

          /*  builder.addTextBody("username", "username", ContentType.create("text/plain", MIME.UTF8_CHARSET));
            builder.addTextBody("email", "email", ContentType.create("text/plain", MIME.UTF8_CHARSET));
            builder.addTextBody("password",  "password", ContentType.create("text/plain", MIME.UTF8_CHARSET));
*/

            HttpClient httpClient = new DefaultHttpClient();

            httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
                    System.getProperty("http.agent"));

            HttpPost httpPost = new HttpPost("http://tanggoal.com/hack/flir");

            httpPost.setEntity( builder.build());


            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            String is;

            try {

                BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                String body = "";
                String content = "";

                while ((body = rd.readLine()) != null) {
                    content += body + "\n";
                }

                return content;
            } catch (IOException e) {

                // TODO Auto-generated catch block
                e.printStackTrace();

            }


        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return null;

    }

    @Override
    protected void onPostExecute(String result){

        super.onPostExecute(result);




      /*  LiveChat_frag live = new LiveChat_frag();

        CheckBox checkBox = (CheckBox) rootView.findViewById(R.id.image_checked);

        live.attemptSend(pro_pic_url, checkBox);*/


        Toast t= Toast.makeText(activity, "onpost finish "+result, Toast.LENGTH_SHORT);
        t.show();

        try {

            JSONObject json = new JSONObject(result);


            // String course_index =result;

            if(!result.isEmpty()){

               /* Dialog d = new Dialog(activity);
                d.setTitle(""+result);
                TextView t = new TextView(activity);
                t.setText(""+result);
                d.setContentView(t);
                d.show();*/


            }

            //run the set stuff





        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }//excetue

    public boolean success(){


        return true;
    }


}
