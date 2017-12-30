package bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2017/12/30.
 */
public class HelloWorld {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(HelloWorld.class.getClassLoader())
                .getLoaded();
       // assertThat(dynamicType.newInstance().toString(), is("Hello World!"));

       String str= dynamicType.newInstance().toString();
        System.out.println(str);

    }


}
