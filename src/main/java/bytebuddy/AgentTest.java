package bytebuddy;

import net.bytebuddy.agent.ByteBuddyAgent;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2017/12/30.
 */
public class AgentTest {
    public static void main(String[] args) {
        TimeAgent.premain(null, ByteBuddyAgent.install());
        //.// TODO: 2017/12/30  instance test
        new   TestTimed().hello();
        //Todo.  static method test
        TestTimed.staticHello();
       // TestTimed.staticHello();
    }

    public static class TestTimed{

        void hello()  {
            System.out.println("I am hello");
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void  staticHello()  {
            System.out.println("I am static hello");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
