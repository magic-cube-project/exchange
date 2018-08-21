import com.squareup.okhttp.*;

import java.io.IOException;


public class main {
    public static void main( String[] args){

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"appId\": \"leyou\",\n  \"appSecret\": \"1eYou@8*93\",\n  \"scope\": \"partner\"\n}");
        Request request = new Request.Builder()
                .url("http://leyou.586886.com/api/ThirdParty/GetAccessToken")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String res =  response.body().string();
            System.out.println("结果:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
