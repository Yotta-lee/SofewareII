package server.RMIservice;

import client.Vo.saleVO;
import server.Data.Codata.CoaccountDB;
import server.Data.Consumerdata.ConsumerDB;
import server.Data.Financedata.accountInitDB;
import server.Data.Financedata.moneyListDB;
import server.Data.Financedata.saleDB;
import server.Data.Goodsdata.GoodsDB;
import server.Data.Goodsdata.GoodsKindsDB;
import server.Data.Logdata.logDB;
import server.Data.Userdata.userDB;
import server.Data.pub.publicDB;
import server.Dataservice.Billdataservice.*;
import server.Dataservice.Codataservice.Coaccount;
import server.Dataservice.Consumerdataservice.Consumer;
import server.Dataservice.Cutdataservice.cut;
import server.Dataservice.Financedataservice.accountInit;
import server.Dataservice.Financedataservice.goodsoutList;
import server.Dataservice.Financedataservice.moneyList;
import server.Dataservice.Financedataservice.sale;
import server.Dataservice.Goodsdataservice.Goods;
import server.Dataservice.Goodsdataservice.GoodsKinds;
import server.Dataservice.Goodsdataservice.stockGoods;
import server.Dataservice.Logdataservice.log;
import server.Dataservice.Packdataservice.pack;
import server.Dataservice.Userdataservice.user;
import server.Dataservice.Pubservice.pub;
import shared.ResultMessage;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class DataRemoteObject extends UnicastRemoteObject implements accountInit,
        moneyBill, selloutBill, stockOverflowBill, stockwarningBill, moneyList, stockGoods,
        Coaccount, Consumer, Goods, GoodsKinds, log, cut, pack, user, buyinBill, giftBill, server.Dataservice.Pubservice.pub, sale, goodsoutList {

    /**

     *

     */

    private static final long serialVersionUID = 4029039744279087114L;
    private moneyList moneyList;
    private Coaccount coaccount;
    private Consumer consumer;
    private Goods goods;
    private GoodsKinds goodsKinds;
    private log log;
    private pub pub;
    private user user;
    private sale sale;
    private accountInit accountInit;

    DataRemoteObject() throws RemoteException {

        moneyList = new moneyListDB();
        coaccount = new CoaccountDB();
        consumer = new ConsumerDB();
        goods = new GoodsDB();
        goodsKinds = new GoodsKindsDB();
        log = new logDB();
        pub = new publicDB();
        user = new userDB();
        sale = new saleDB();
        accountInit = new accountInitDB();


    }


    @Override
    public List goodsFindGoodsByKeyword(String keyword) throws RemoteException {
        return goods.goodsFindGoodsByKeyword(keyword);
    }

    @Override
    public List goodsFindByKind(String kind) throws RemoteException {
        return goods.goodsFindByKind(kind);
    }


    @Override
    public List goodsKindsFindByKeyWord(String keyword) throws RemoteException {
        return goodsKinds.goodsKindsFindByKeyWord(keyword);
    }


    @Override
    public List logstockGlance(String from, String to) throws RemoteException {
        return log.logstockGlance(from, to);
    }

    @Override
    public List showbillDetail(String from, String to, String name, String consumer, String operator, String base) throws RemoteException {
        return log.showbillDetail(from, to, name, consumer, operator, base);
    }

    @Override
    public ResultMessage addObject(Object object, int type) throws RemoteException {
        return pub.addObject(object, type);
    }

    @Override
    public ResultMessage deleteObject(Object object, int type) throws RemoteException {
        return pub.deleteObject(object, type);
    }

    @Override
    public ResultMessage modifyObject(Object object, int type) throws RemoteException {
        return pub.modifyObject(object, type);
    }

    @Override
    public List findAll(int type) throws RemoteException {
        return pub.findAll(type);
    }

    @Override
    public List findbyNO(int type, String no) throws RemoteException {
        return pub.findbyNO(type, no);
    }

    @Override
    public ResultMessage exportToExcel(int type, String path) throws IOException, InterruptedException {
        return pub.exportToExcel(type, path);
    }


    @Override
    public List findbySaleVO(saleVO saleVO) throws RemoteException {
        return sale.findbySaleVO(saleVO);
    }

    @Override
    public List search(String detail) throws RemoteException {
        return sale.search(detail);
    }

    @Override
    public List login(String username, String password) throws RemoteException {
        return user.login(username, password);
    }

    @Override
    public String getpasswordByName(String username) throws RemoteException {
        return user.getpasswordByName(username);
    }

    @Override
    public String getJobByName(String username) throws RemoteException {
        return user.getJobByName(username);
    }

    @Override
    public void FaceService(String username) throws RemoteException {

    }

    @Override
    public String getNameByFaceTag() throws RemoteException {
        return user.getNameByFaceTag();
    }

    @Override
    public List getPastAccount(String year) throws RemoteException {
        return accountInit.getPastAccount(year);
    }

    @Override
    public List getPastConsumer(String year) throws RemoteException {
        return accountInit.getPastConsumer(year);
    }

    @Override
    public List getPastGoods(String year) throws RemoteException {
        return accountInit.getPastGoods(year);
    }

    @Override
    public List findAccount(String keyword) throws RemoteException {
        return coaccount.findAccount(keyword);
    }

    @Override
    public void Build(String year) throws RemoteException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        accountInit.Build(year);
    }

    @Override
    public void deleteByNO(String no) throws RemoteException {
        moneyList.deleteByNO(no);
    }

    @Override
    public List findConsumer(String keyword) throws RemoteException {
        return consumer.findConsumer(keyword);
    }
}