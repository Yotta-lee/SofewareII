package client.BLservice.Accountant.FinancialPayblservice;

import client.BL.Accountant.FinancialAccountbl.Account;
import client.BL.Accountant.FinancialReceivebl.AccountBill;
import client.BL.Accountant.FinancialReceivebl.Consumer;
import client.BL.Accountant.FinancialReceivebl.FinancialBill;
import client.BL.Accountant.FinancialReceivebl.MoneyList;
import server.Po.consumerPO;
import server.Po.moneyListPO;
import server.Po.moneyPO;
import shared.ResultMessage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface FinancialPayInterface {
    /**
     * 提交单据
     *
     * @param financialBill
     * @return
     * @throws RemoteException
     */
    public ResultMessage summit(FinancialBill financialBill) throws RemoteException;
    /**
     * 再次提交
     *
     * @param financialBill
     * @return
     * @throws RemoteException
     */
    public ResultMessage resummit(FinancialBill financialBill) throws RemoteException;
    /**
     * 保存草稿
     *
     * @param financialBill
     * @return
     * @throws RemoteException
     */
    public ResultMessage saveAsDraft(FinancialBill financialBill) throws RemoteException;
    /**
     * 编辑草稿
     *
     * @param Keyno
     * @return
     * @throws RemoteException
     */
    public FinancialBill ReEditBill(String Keyno) throws RemoteException;
    /**
     * 类型转化
     * @param po
     * @return
     * @throws RemoteException
     */
    public FinancialBill PoToFinancialBill(moneyPO po) throws RemoteException;
    /**
     * PO转换
     *
     * @param list
     * @return
     * @throws RemoteException
     */
    public ArrayList<MoneyList> PoToMoneyLists(List<moneyListPO> list) throws RemoteException;
    /**
     * 所有已经审批的单据
     *
     * @return
     * @throws RemoteException
     */
    public ArrayList<AccountBill> getAllPromotedPay() throws RemoteException;

    /**
     * 正在审批的单据
     * @return
     * @throws RemoteException
     */
    public ArrayList<AccountBill> getAllUnderPromotedPay() throws RemoteException;
    /**
     * 保存条目列表
     *
     * @param moneyLists
     * @throws RemoteException
     */
    public void saveMoneyList(ArrayList<MoneyList> moneyLists) throws RemoteException;
    /**
     * 草稿单据
     *
     * @return
     * @throws RemoteException
     */

    public ArrayList<AccountBill> getAllDraftPay() throws RemoteException;
    /**
     * PO转换
     * @param po
     * @return
     * @throws RemoteException
     */
    public AccountBill PoToAccountBill(moneyPO po) throws RemoteException;
    /**
     * 筛选草稿
     *
     * @param pos
     * @return
     * @throws RemoteException
     */
    public ArrayList<moneyPO> PickDraftPay(List<moneyPO> pos) throws RemoteException;
    /**
     * 类型转换
     *
     * @param financialBill
     * @return
     * @throws RemoteException
     */
    public moneyPO FinancialBillToMoneyPO(FinancialBill financialBill) throws RemoteException;
    /**
     * 账户列表
     *
     * @return
     * @throws RemoteException
     */
    public ArrayList<Account> getAllAccount() throws RemoteException;
    /**
     *
     * 客户列表
     *
     * @return
     * @throws RemoteException
     */
    public ArrayList<Consumer> getAllConsumer() throws RemoteException;
    /**
     * 类型转化
     *
     * @param po
     * @return
     */
    public Consumer PoToConsumer(consumerPO po) throws RemoteException;

}
