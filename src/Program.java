import com.iveely.framework.net.AsynServer;
import com.iveely.framework.net.AsynServer.IHandler;
import com.iveely.framework.net.Packet;
import com.iveely.framework.net.AsynClient;

/**
 * author name: Iveely Liu
 * contact  me: sea11510@mail.ustc.edu.cn
 * description:
 */

/**
* @description 
* @author Iveely Liu
*/
/**
 * 
 */
public class Program {

    public static class Handler implements com.iveely.framework.net.AsynServer.IHandler {

        /*
         * (non-Javadoc)
         * 
         * @see com.iveely.framework.net.AsynServer.IHandler#process(com.iveely.
         * framework.net.Packet)
         */
        @Override
        public Object process(Object data) {
            System.out.println(data);
            return "Hi";
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.iveely.framework.net.AsynServer.IHandler#caught(java.lang.String)
         */
        @Override
        public void caught(String exception) {
            // TODO Auto-generated method stub

        }
    }

    public static class Handler2 implements com.iveely.framework.net.AsynClient.IHandler {

        /*
         * (non-Javadoc)
         * 
         * @see com.iveely.framework.net.AsynClient.IHandler#receive(java.lang.
         * String)
         */
        @Override
        public void receive(Object info) {
            System.out.println(info);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.iveely.framework.net.AsynClient.IHandler#caught(java.lang.String)
         */
        @Override
        public void caught(String exception) {
            // TODO Auto-generated method stub

        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        AsynServer server = new AsynServer(10086, new Handler());
        server.open();
//        AsynClient client = new AsynClient("127.0.0.1", 10086, new Handler2());
//        client.s("Hello");
//        client.send("Hello2");

    }

}
