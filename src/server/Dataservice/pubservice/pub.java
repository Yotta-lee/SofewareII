package server.Dataservice.pubservice;

import shared.ResultMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 13:55 2017/11/26
 */
public interface pub extends Remote{
    ResultMessage addObject(Object object,int type) throws RemoteException;
    ResultMessage deleteObject(Object object,int type) throws RemoteException;
    ResultMessage modifyObject(Object object,int type) throws RemoteException;
}
