package bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2017/12/30.
 */
public class IntercepterTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<? extends Function> dynamicType = new ByteBuddy()
                .subclass(Function.class)
                .method(ElementMatchers.named("apply"))
                .intercept(MethodDelegation.to(new GeneralInterceptor()))
                .make()
                .load(IntercepterTest.class.getClassLoader())
                .getLoaded();
       // assertThat((String) dynamicType.newInstance().apply("Byte Buddy"), is("Hello from Byte Buddy"));

        dynamicType.newInstance().apply("Byte Buddy","hello");

    }

   public static class Function{
        void apply(String s1,String s2){
            System.out.println(s1);
            System.out.println(s2);
        }
    }


    public static class GeneralInterceptor {
        @RuntimeType
        public Object intercept(@This Object proxy, @SuperMethod Method originAccessor, @AllArguments Object[] allArguments,
                                @Origin Method method) {
            System.out.println(method.getName());
            // intercept any method of any signature
            System.out.println("intercept");
            try {
                originAccessor.invoke(proxy,allArguments);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }


            return null;
        }
    }




}


