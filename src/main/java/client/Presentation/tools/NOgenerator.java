package client.Presentation.tools;

import client.RMI.link;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 11:28 2017/12/4
 */
public class NOgenerator {
    /**
     * @author:pis
     * @description: 编号产生器
     * @date: 11:28 2017/12/4
     */
    public static String generate(int type) throws RemoteException, IntrospectionException, InvocationTargetException, IllegalAccessException {
        StringBuilder no;
        List POS = link.getRemoteHelper().getPub().findAll(type);
        if (POS.isEmpty())
            no = new StringBuilder("00001");
        else {
            int maxint = 0;
            BeanInfo fromBean = Introspector.getBeanInfo(POS.get(POS.size() - 1).getClass(), Object.class);
            PropertyDescriptor[] fromProperty = fromBean.getPropertyDescriptors();
            for (PropertyDescriptor i : fromProperty) {
                if (i.getName().equals("keyno")) {
                    maxint = spilt(POS, maxint, i);
                }
            }
            maxint++;
            no = new StringBuilder(String.valueOf(maxint));
            while (no.length() < 5)
                no.insert(0, "0");
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

        return df.format(new Date()) + "-" + no.toString();
    }

    private static int spilt(List POS, int maxint, PropertyDescriptor i) throws IllegalAccessException, InvocationTargetException {
        String temp;
        for (Object PO : POS) {
            temp = ((String) i.getReadMethod().invoke(PO));
            String split[] = temp.split("-");
            int tempint = Integer.parseInt(split[2]);
            if (tempint > maxint)
                maxint = tempint;
        }
        return maxint;
    }

    public static String generateaLog(int type) throws RemoteException, IntrospectionException, IllegalAccessException, InvocationTargetException {
        return "log" + "-" + NOgenerator.generate(type);
    }

    public static String generateMoneyList(int type) throws RemoteException, IntrospectionException, InvocationTargetException, IllegalAccessException {
        StringBuilder no;
        List POS = link.getRemoteHelper().getPub().findAll(type);
        if (POS.isEmpty())
            no = new StringBuilder("00001");
        else {
            String temp;
            int maxint = 0;
            BeanInfo fromBean = Introspector.getBeanInfo(POS.get(POS.size() - 1).getClass(), Object.class);
            PropertyDescriptor[] fromProperty = fromBean.getPropertyDescriptors();
            for (PropertyDescriptor i : fromProperty) {
                if (i.getName().equals("keyid")) {
                    maxint = spilt(POS, maxint, i);
                }
            }
            maxint++;
            no = new StringBuilder(String.valueOf(maxint));
            while (no.length() < 5)
                no.insert(0, "0");
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

        return df.format(new Date()) + "-" + no.toString();
    }
}
