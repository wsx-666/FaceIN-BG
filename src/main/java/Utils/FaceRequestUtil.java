package Utils;

/**
 * Created by Dao on 2017/7/6.
 */
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javax.net.ssl.SSLException;
public class FaceRequestUtil {
/*
* 增加人脸
* 1.faceDetect(String path)
* 2.
*   a)get face_token from 1.
*   b)add face_token database
* 3.faceAddFace(String face_token)/faceAddFace(String[] face_tokens)添加人脸,观察返回值
* 删除人脸
* 1.get face_token from database
* 2.faceRemoveFace(String face_token)/faceRemoveFace(String[] face_tokens)删除人脸,观察返回值
* 搜索人脸
* 1.faceDetect(String path)
* 2.
*   a)get face_token from 1
*   b)add face_token database search record
* 3.faceSearch(String path)搜索人脸，观察返回值。confidence
* */
    public static void main(String[] args) throws Exception{
        //System.out.println(faceCompare("D:\\Shared\\Tmp\\1.jpg","D:\\Shared\\Tmp\\2.jpg"));
        //faceSetCreate();
        //faceDetect("D:\\Shared\\Tmp\\2.jpg");
        //faceAddFace("deb43041cbe020e66f04011facf86abd");//1
        //faceAddFace("49b44174619f7b5b20c701f3a4a66904");//2
        //faceAddFace("d1a85097375bf543d0d383a31dc993b6");//2
        faceSearch("D:\\Shared\\Tmp\\2.jpg");
    }
    /*
    * 分析人脸
    * param:path 图片路径
    * return:图片信息数组，包含face_token
    * */
    public static String faceDetect(String path)throws Exception{
        File file = new File(path);
        byte[] buff = getBytesFromFile(file);
        String url = Constant.faceppDetectURL;
        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, byte[]> byteMap = new HashMap<String, byte[]>();
        map.put("api_key", Constant.faceppAPIKey);
        map.put("api_secret", Constant.faceppAPISecret);
        byteMap.put("image_file", buff);
        try {
            byte[] bacd = post(url, map, byteMap);
            String str = new String(bacd);
            System.out.println(str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    * FaceSet中添加人脸
    * param:face_token/face_tokens/path
    * return:result from URL
    * */
    public static String faceAddFace(String face_token) throws Exception{return faceSetAddFace(new String[] {face_token});}
    public static String faceAddFace(String[] face_tokens) throws Exception{return faceSetAddFace(face_tokens);}
    //public static String faceAddFacePath(String path) throws Exception{}

    private static String faceSetAddFace(String[] face_tokens) throws Exception{
        String url = Constant.faceppFaceSetAddURL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("api_key", Constant.faceppAPIKey);
        map.put("api_secret", Constant.faceppAPISecret);
        map.put("outer_id", Constant.faceppFaceSetOuterId);
        StringBuilder sb=new StringBuilder();
        {
            boolean mark = false;
            for (String temp : face_tokens)
                if (mark) {
                    sb.append("," + temp);
                } else {
                    sb.append(temp);
                    mark = true;
                }
        }
        map.put("face_tokens",sb.toString());
        try {
            byte[] bacd = post(url, map, null);
            String str = new String(bacd);
            System.out.println(str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * FaceSet中remove人脸
    * param:face_token/face_tokens
    * return:result from URL.Please Foucus on "error_message".if success then it is not exist.
    * */
    public static String faceRemoveFace(String face_token) throws Exception{return faceSetRemoveFace(new String[] {face_token});}
    public static String faceRemoveFace(String[] face_tokens) throws Exception{return faceSetRemoveFace(face_tokens);}
    private static String faceSetRemoveFace(String[] face_tokens) throws Exception {
        String url = Constant.faceppFaceSetRemoveURL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("api_key", Constant.faceppAPIKey);
        map.put("api_secret", Constant.faceppAPISecret);
        map.put("outer_id", Constant.faceppFaceSetOuterId);
        StringBuilder sb = new StringBuilder();
        {
            boolean mark = false;
            for (String temp : face_tokens)
                if (mark) {
                    sb.append("," + temp);
                } else {
                    sb.append(temp);
                    mark = true;
                }
        }
        map.put("face_tokens", sb.toString());
        try {
            byte[] bacd = post(url, map, null);
            String str = new String(bacd);
            System.out.println(str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    * 创建默认faceSet
    * PS:已创建，禁止使用
    * */
    private static String faceSetCreate() throws Exception{
        String url = Constant.faceppFaceSetCreateURL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("api_key", Constant.faceppAPIKey);
        map.put("api_secret", Constant.faceppAPISecret);
        map.put("display_name", Constant.faceppFaceSetDisplayName);
        map.put("outer_id", Constant.faceppFaceSetOuterId);
        try {
            byte[] bacd = post(url, map, null);
            String str = new String(bacd);
            System.out.println(str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    * 搜索face
    * param:faceToken 保存数据库中的face唯一标识
    * return:
    * */
    public static String faceSearch(String path)throws Exception{
        String url = Constant.faceppSearchURL;
        File file = new File(path);
        byte[] buff = getBytesFromFile(file);
        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, byte[]> byteMap = new HashMap<String, byte[]>();
        map.put("api_key", Constant.faceppAPIKey);
        map.put("api_secret", Constant.faceppAPISecret);
        map.put("outer_id", Constant.faceppFaceSetOuterId);
        byteMap.put("image_file",buff);
        try {
            byte[] bacd = post(url, map, byteMap);
            String str = new String(bacd);
            System.out.println(str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    * 人脸比对
    * param:path1 人脸图片1路径
    *       path2 人脸图片2路径
    * return:比对结果包括置信值
    * */
    public static String faceCompare(String path1,String path2) throws Exception {
        File file = new File(path1);
        File file2 = new File(path2);
        byte[] buff = getBytesFromFile(file);
        byte[] buff2 = getBytesFromFile(file2);
        String url = Constant.faceppCompareURL;
        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, byte[]> byteMap = new HashMap<String, byte[]>();
        map.put("api_key", Constant.faceppAPIKey);
        map.put("api_secret", Constant.faceppAPISecret);
        byteMap.put("image_file1", buff);
        byteMap.put("image_file2", buff2);
        try {
            byte[] bacd = post(url, map, byteMap);
            String str = new String(bacd);
            System.out.println(str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private final static int CONNECT_TIME_OUT = 30000;
    private final static int READ_OUT_TIME = 50000;
    private static String boundaryString = getBoundary();
/*
* URL POST
* */
    protected static byte[] post(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws Exception {
        HttpURLConnection conne;
        URL url1 = new URL(url);
        conne = (HttpURLConnection) url1.openConnection();
        conne.setDoOutput(true);
        conne.setUseCaches(false);
        conne.setRequestMethod("POST");
        conne.setConnectTimeout(CONNECT_TIME_OUT);
        conne.setReadTimeout(READ_OUT_TIME);
        conne.setRequestProperty("accept", "*/*");
        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        conne.setRequestProperty("connection", "Keep-Alive");
        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            obos.writeBytes("--" + boundaryString + "\r\n");
            obos.writeBytes("Content-Disposition: form-data; name=\"" + key
                    + "\"\r\n");
            obos.writeBytes("\r\n");
            obos.writeBytes(value + "\r\n");
        }
        if(fileMap != null && fileMap.size() > 0){
            Iterator fileIter = fileMap.entrySet().iterator();
            while(fileIter.hasNext()){
                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
                obos.writeBytes("--" + boundaryString + "\r\n");
                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
                        + "\"; filename=\"" + encode(" ") + "\"\r\n");
                obos.writeBytes("\r\n");
                obos.write(fileEntry.getValue());
                obos.writeBytes("\r\n");
            }
        }
        obos.writeBytes("--" + boundaryString + "--" + "\r\n");
        obos.writeBytes("\r\n");
        obos.flush();
        obos.close();
        InputStream ins = null;
        int code = conne.getResponseCode();
        try{
            if(code == 200){
                ins = conne.getInputStream();
            }else{
                ins = conne.getErrorStream();
            }
        }catch (SSLException e){
            e.printStackTrace();
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int len;
        while((len = ins.read(buff)) != -1){
            baos.write(buff, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        ins.close();
        return bytes;
    }
    private static String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }
        return sb.toString();
    }
    private static String encode(String value) throws Exception{
        return URLEncoder.encode(value, "UTF-8");
    }

    private static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }
}