import org.apache.commons.codec.binary.Base64;

public class main {
    public static void main( String[] args){
     String base64String="whuang123";
      byte[] result = Base64.encodeBase64(base64String.getBytes());
      String r = String.valueOf(result);
      System.out.println(r);
      String e = String.valueOf(Base64.decodeBase64(r));
      System.out.println(e);

    }
}
