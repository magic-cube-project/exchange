import com.bean.AppUserLink;
import com.model.OpenUser;

public class main {
    public static void main( String[] args){
        AppUserLink o= OpenUser.add(1,1);
        System.out.println(o.getOpenid());
    }
}
