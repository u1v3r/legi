package sk.rdy.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by rdy on 10.7.2013.
 */
public class ApiFactory {

    public static GeneralApi initApi(String appName, Class<? extends GeneralApi> apiClass){

        try {
            Constructor<? extends GeneralApi> constructor = apiClass.getDeclaredConstructor(String.class);
            return  (GeneralApi) constructor.newInstance(appName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

}
