package bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2017/12/30.
 */
public class TimeAgent {

    public static void premain(String arguments,
                               Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(ElementMatchers.nameEndsWith("Timed"))
                .transform(
                        new AgentBuilder.Transformer() {
                            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
                               return   builder.method(ElementMatchers.any())
                                        .intercept(MethodDelegation.to(TimingInterceptor.class));
                            }
                        }
                ).installOn(instrumentation);
    }


    public static class TimingInterceptor {
        @BindingPriority(9)
        @RuntimeType
        public static Object intercept(@This Object proxy, @SuperMethod Method originAccessor, @AllArguments Object[] allArguments) {
            long start = System.currentTimeMillis();
            try {
                try {
                    originAccessor.setAccessible(true);
                    originAccessor.invoke(proxy,allArguments);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } finally {
                System.out.println(originAccessor + " took " + (System.currentTimeMillis() - start));
            }

            return null;
        }

        //for static method
        @RuntimeType
        public static Object intercept(@SuperMethod Method originAccessor,
                                       @AllArguments Object[] args) throws Throwable {
            //forward to overloaded intercept method.
            return intercept(null, originAccessor, args);
        }

    }






}
