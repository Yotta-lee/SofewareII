package client.Presentation.SalesmanUI.BillMake;

import client.BL.Saleman.SalemanConsumerManageblservice.Consumer;
import client.BL.Saleman.SalemanConsumerManageblservice.ConsumerManageController;
import client.BL.Saleman.SalemanSaleblservice.SelloutBill;
import client.BL.Saleman.SalemanSaleblservice.SelloutBillMakeController;
import client.BL.Saleman.SalemanStockinblservice.StockinBill;
import client.BL.Saleman.SalemanStockinblservice.StockinBillMakeController;
import client.RMI.link;
import client.Vo.buyinVO;
import client.Vo.consumerVO;
import client.Vo.selloutVO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.Po.consumerPO;
import server.Po.buyinPO;
import server.Po.selloutPO;

import javax.security.auth.callback.LanguageCallback;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Leonarda on 2017/12/6.
 */
public class newBillUI extends Application{

    TreeItem<String> rootNode;
    TreeItem<String> consumerNode;
    TreeItem<String> buyinNode;
    TreeItem<String> selloutNode;

    TabPane tabs=new TabPane();

    ConsumerManageController consumerManageController=new ConsumerManageController();
    StockinBillMakeController stockinBillMakeController=new StockinBillMakeController();
    SelloutBillMakeController selloutBillMakeController=new SelloutBillMakeController();

    ArrayList<Consumer> consumerList=new ArrayList<>();

    ArrayList<StockinBill> stockinList=new ArrayList<>();

    ArrayList<SelloutBill> selloutBillList=new ArrayList<>();


    HBox hb=new HBox();
    Scene scene=new Scene(hb,1400,650);

    public static void main(String[] args){
        link.linktoServer();
        launch(args);
    }

    public newBillUI(){
        this.rootNode=new TreeItem<>("工作目录");
        this.consumerNode=new TreeItem<>("客户列表");
        this.buyinNode=new TreeItem<>("进货单列表");
        this.selloutNode=new TreeItem<>("销售单列表");
    }

    public void start(Stage stage) throws RemoteException {

        List<consumerVO> consumervolist=consumerManageController.show();
        for(consumerVO vo:consumervolist){
            Consumer a=consumerManageController.VOtoconsumer(vo);
            consumerList.add(a);
        }

        List<buyinVO> buyinvolist=stockinBillMakeController.show();
        for(buyinVO vo:buyinvolist){
            StockinBill a=stockinBillMakeController.votoBill(vo);
            stockinList.add(a);
        }

        List<selloutVO> selloutvoList=selloutBillMakeController.show();
        for(selloutVO vo:selloutvoList){
            SelloutBill a=selloutBillMakeController.VoTosellout(vo);
            selloutBillList.add(a);
        }

        MenuBar menuBar=new MenuBar();
        Menu menu1=new Menu("菜单");
        Menu menu2=new Menu("新建");
        Menu menu3=new Menu("帮助");
        menuBar.getMenus().addAll(menu1,menu2,menu3);



        consumerNode.setExpanded(true);
        for(Consumer consumer:consumerList){
            TreeItem<String> empleaf=new TreeItem<>(consumer.getConsumerID());
            consumerNode.getChildren().add(empleaf);
        }


        ContextMenu consumerMenu=new ContextMenu();
        MenuItem newMenuitem=new MenuItem("新建客户");
        consumerMenu.getItems().add(newMenuitem);

        stage.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent me)->{
            if(me.getButton()== MouseButton.SECONDARY||me.isControlDown()){
                consumerMenu.show(hb,me.getScreenX(),me.getScreenY());
            }
            else{
                consumerMenu.hide();
            }
        });

        newMenuitem.setOnAction((ActionEvent e)->{
            TreeItem<String> a=new TreeItem<>("consumer"+String.valueOf(consumerNode.getChildren().size()+1));
            consumerNode.getChildren().add(a);
            Tab newTab=new Tab();
            newTab.setText(a.getValue());
            newTab.setContent(newConsumerPane(a));
            tabs.getTabs().add(newTab);
        });



        buyinNode.setExpanded(true);
        TreeItem<String> in=new TreeItem<>("进货单");;
        TreeItem<String> cancel=new TreeItem<>("进货退货单");;
        for(StockinBill stockin:stockinList){
            TreeItem<String> empleaf=new TreeItem<>(stockin.getBuyinID());
            if((stockin.getBuyinKind()).equals("0")){
                in.getChildren().add(empleaf);
            }
            else{
                cancel.getChildren().add(empleaf);
            }
        }
        buyinNode.getChildren().addAll(in,cancel);


        selloutNode.setExpanded(true);
        TreeItem<String> selloutin=new TreeItem<String>("销售单");
        TreeItem<String> selloutCancel=new TreeItem<>("销售退货单");
        for(SelloutBill sellout:selloutBillList){
            TreeItem<String> empleaf=new TreeItem<>(sellout.getSelloutID());
            if(sellout.getSelloutID().equals("0")){
                selloutin.getChildren().add(empleaf);
            }
            else{
                selloutCancel.getChildren().add(empleaf);
            }
        }
        selloutNode.getChildren().addAll(selloutin,selloutCancel);



        rootNode.getChildren().addAll(consumerNode,buyinNode,selloutNode);


        stage.setTitle("进货销售人员");
        stage.setWidth(1400);
        stage.setHeight(650);
        scene.setFill(Color.WHITE);

        TreeView<String> treeView=new TreeView<>(rootNode);
        hb.getChildren().add(treeView);


        TreeView<String> contree=new TreeView<>(consumerNode);
        TreeView<String> buytree=new TreeView<>(buyinNode);
        TreeView<String> selltree=new TreeView<>(selloutNode);

        treeView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue)->{
            if(newValue.isLeaf()){
                if(newValue.getParent().getValue().equals("进货单")||newValue.getParent().getValue().equals("进货退货单")){
                    Tab newTab=new Tab();
                    newTab.setText(newValue.getValue());
                    newTab.setContent(BuyinBillPane(newValue.getValue()));
                    tabs.getTabs().add(newTab);
                }
                else if(newValue.getParent().getValue().equals("销售单")||newValue.getParent().getValue().equals("销售退货单")){
                    Tab newTab=new Tab();
                    newTab.setText(newValue.getValue());
                    newTab.setContent(SelloutPane(newValue.getValue()));
                    tabs.getTabs().add(newTab);
                }
                else if(newValue.getParent().getValue().equals("客户列表")){
                    Tab newTab=new Tab();
                    newTab.setText(newValue.getValue());
                    newTab.setContent(ConsumerPane(newValue.getValue()));
                    tabs.getTabs().add(newTab);
                }else{
                }
            }
        });



        tabs.setTabMinHeight(30);
        tabs.setTabMinWidth(70);
        hb.getChildren().add(tabs);


        stage.setScene(scene);
        stage.show();
    }

    private Pane ConsumerPane(String name){
        Consumer thisconsumer=new Consumer();
        for(Consumer consumer:consumerList){
            if(consumer.getConsumerID().equals(name)){
                thisconsumer=consumer;
                break;
            }
        }

        consumerVO vo=consumerManageController.consumerToVO(thisconsumer);

        GridPane gridPane=new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(12);
        gridPane.setMinHeight(450);
        gridPane.setMinWidth(1050);


        Label idlabel=new Label("客户ID：");
        Text id=new Text();
        id.setText(thisconsumer.getConsumerID());

        Label kindlabel=new Label("类型：");
        Text kind=new Text();
        if(Double.parseDouble(thisconsumer.getConsumerKind())==0){
            kind.setText("进货商");
        }else{
            kind.setText("销售商");
        }

        Label levelLabel=new Label("客户级别：");
        Text level=new Text();
        level.setText("VIP"+thisconsumer.getConsumerLevel());

        Label nameLabel=new Label("姓名：");
        Text coname=new Text();
        coname.setText(thisconsumer.getConsumerName());

        Label phoneLabel=new Label("联系方式：");
        Text phone=new Text();
        phone.setText(thisconsumer.getConsumerPhone());

        Label mailLabel=new Label("邮编：");
        Text mail=new Text();
        mail.setText(thisconsumer.getConsumerMail());

        Label emailLabel=new Label("电子邮箱：");
        Text email=new Text();
        email.setText(thisconsumer.getConsumerEmail());

        Label moneyLabel=new Label("应收额度：");
        Text receivemoney=new Text();
        receivemoney.setText(thisconsumer.getConsumerReveiveMoney());

        Label receiveLabel=new Label("应收：");
        Text receive=new Text();
        receive.setText(thisconsumer.getConsumerReceive());

        Label payLabel=new Label("应付：");
        Text pay=new Text();
        pay.setText(thisconsumer.getConsumerPay());

        Label accoutLabel=new Label("客户账号：");
        Text account=new Text();
        account.setText(thisconsumer.getAccout());

        Label salemanLabel=new Label("销售员：");
        Text saleman=new Text();
        saleman.setText(thisconsumer.getSalesman());

        gridPane.add(idlabel,0,0);
        gridPane.add(id,1,0);
        gridPane.add(kindlabel,0,1);
        gridPane.add(kind,1,1);
        gridPane.add(levelLabel,0,2);
        gridPane.add(level,1,2);
        gridPane.add(nameLabel,0,3);
        gridPane.add(coname,1,3);
        gridPane.add(phoneLabel,0,4);
        gridPane.add(phone,1,4);
        gridPane.add(mailLabel,0,5);
        gridPane.add(mail,1,5);
        gridPane.add(emailLabel,0,6);
        gridPane.add(email,1,6);
        gridPane.add(moneyLabel,0,7);
        gridPane.add(receivemoney,1,7);
        gridPane.add(receiveLabel,0,8);
        gridPane.add(receive,1,8);
        gridPane.add(payLabel,0,9);
        gridPane.add(pay,1,9);
        gridPane.add(accoutLabel,0,10);
        gridPane.add(account,1,10);
        gridPane.add(salemanLabel,0,11);
        gridPane.add(saleman,1,11);

        Button edit=new Button("编辑");
        Button delete=new Button("删除");

        gridPane.add(edit,3,12);
        gridPane.add(delete,4,12);

        edit.setOnAction((ActionEvent a1)->{
            String conid=id.getText();
            String conkind=id.getText();
            String conlevel=id.getText();
            String conname=coname.getText();
            String conphone=phone.getText();
            String conmail=phone.getText();
            String conemail=email.getText();
            String conreceivemoney=receivemoney.getText();
            String conaccount=account.getText();
            String consaleman=saleman.getText();

            gridPane.getChildren().removeAll(id,kind,level,coname,phone,mail,email,receivemoney,account,saleman,edit,delete);

            TextField id1=new TextField();
            id1.setPromptText(conid);
            gridPane.add(id1,1,0);

            TextField kind1=new TextField();
            kind1.setPromptText(conkind);
            gridPane.add(kind1,1,1);

            TextField level1=new TextField();
            level1.setPromptText(conlevel);
            gridPane.add(level1,1,2);

            TextField name1=new TextField();
            name1.setPromptText(conname);
            gridPane.add(name1,1,3);

            TextField phone1=new TextField();
            phone1.setPromptText(conphone);
            gridPane.add(phone1,1,4);

            TextField mail1=new TextField();
            mail1.setPromptText(conmail);
            gridPane.add(mail1,1,5);

            TextField email1=new TextField();
            email1.setPromptText(conemail);
            gridPane.add(email1,1,6);

            TextField receivemoney1=new TextField();
            receivemoney1.setPromptText(conreceivemoney);
            gridPane.add(receivemoney1,1,7);

            TextField account1=new TextField();
            account1.setPromptText(conaccount);
            gridPane.add(account1,1,10);

            TextField salesman1=new TextField();
            salesman1.setPromptText(consaleman);
            gridPane.add(salesman1,1,11);

            Button confirm=new Button("确认修改");
            Button cancel=new Button("取消");

            gridPane.add(confirm,3,12);
            gridPane.add(cancel,4,12);

            confirm.setOnAction((ActionEvent e1)->{

            });

            cancel.setOnAction((ActionEvent e2)->{

            });
        });

        delete.setOnAction((ActionEvent a2)->{
            try {
                consumerManageController.deleteConsumer(vo);
                gridPane.getChildren().clear();

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        return gridPane;
    }

    private Pane newConsumerPane(TreeItem<String> a) {

        GridPane gridPane=new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(12);
        gridPane.setMinHeight(450);
        gridPane.setMinWidth(1050);

        Label idlabel=new Label("客户ID：");
        Text id=new Text();
        id.setText(a.getValue());

        Label kindlabel=new Label("类型：");
        TextField kind=new TextField();

        Label levelLabel=new Label("客户级别：");
        TextField level=new TextField();

        Label nameLabel=new Label("姓名：");
        TextField coname=new TextField();

        Label phoneLabel=new Label("联系方式：");
        TextField phone=new TextField();

        Label mailLabel=new Label("邮编：");
        TextField mail=new TextField();

        Label emailLabel=new Label("电子邮箱：");
        TextField email=new TextField();

        Label moneyLabel=new Label("应收额度：");
        TextField receivemoney=new TextField();

        Label receiveLabel=new Label("应收：");
        Text receive=new Text();
        receive.setText("0");

        Label payLabel=new Label("应付：");
        Text pay=new Text();
        pay.setText("0");

        Label accoutLabel=new Label("客户账号：");
        TextField account=new TextField();

        Label salesmanLabel=new Label("销售员：");
        TextField salesman=new TextField();

        gridPane.add(idlabel,0,0);
        gridPane.add(id,1,0);
        gridPane.add(kindlabel,0,1);
        gridPane.add(kind,1,1);
        gridPane.add(levelLabel,0,2);
        gridPane.add(level,1,2);
        gridPane.add(nameLabel,0,3);
        gridPane.add(coname,1,3);
        gridPane.add(phoneLabel,0,4);
        gridPane.add(phone,1,4);
        gridPane.add(mailLabel,0,5);
        gridPane.add(mail,1,5);
        gridPane.add(emailLabel,0,6);
        gridPane.add(email,1,6);
        gridPane.add(moneyLabel,0,7);
        gridPane.add(receivemoney,1,7);
        gridPane.add(receiveLabel,0,8);
        gridPane.add(receive,1,8);
        gridPane.add(payLabel,0,9);
        gridPane.add(pay,1,9);
        gridPane.add(accoutLabel,0,10);
        gridPane.add(account,1,10);
        gridPane.add(salesmanLabel,0,11);
        gridPane.add(salesman,1,11);

        Button build=new Button("新建");
        Button cancel=new Button("取消");

        gridPane.add(build,3,12);
        gridPane.add(cancel,4,12);

        build.setOnAction((ActionEvent e)->{
            double kindnum;
            if(kind.getText().equals("进货商")){
                kindnum=0;
            }else{
                kindnum=1;
            }
            consumerVO vo=new consumerVO(
                    id.getText(),
                    kindnum,
                    Double.parseDouble(level.getText()),
                    coname.getText(),
                    phone.getText(),
                    mail.getText(),
                    email.getText(),
                    Double.parseDouble(receivemoney.getText()),
                    Double.parseDouble(receive.getText()),
                    Double.parseDouble(pay.getText()),
                    salesman.getText(),
                    account.getText()
            );

            try {
                consumerManageController.addConsumer(vo);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }

        });

        cancel.setOnAction((ActionEvent e)->{

        });
        return gridPane;
    }

    private Pane BuyinBillPane(String name){
        StockinBill thisstockinbill=new StockinBill();
        for(StockinBill buyinBill:stockinList){
            if(buyinBill.getBuyinID().equals(name)){
                thisstockinbill=buyinBill;
                break;
            }
        }

        buyinVO vo=stockinBillMakeController.billtovo(thisstockinbill);

        GridPane gridPane=new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(12);
        gridPane.setMinHeight(450);
        gridPane.setMinWidth(1050);

        Label kindlabel=new Label("单据类型：");
        Text kind=new Text();
        if(Double.parseDouble(thisstockinbill.getBuyinKind())==0){
            kind.setText("进货单");
        }
        else{
            kind.setText("进货退货单");
        }

        Label idLabel=new Label("单据ID：");
        Text id=new Text();
        id.setText(thisstockinbill.getBuyinID());

        Label operaterLabel=new Label("操作员：");
        Text operater=new Text();
        operater.setText(thisstockinbill.getBuyinOperater());

        Label offerLabel=new Label("供应商：");
        Text offer=new Text();
        offer.setText(thisstockinbill.getBuyinOffer());

        Label StorehouseLabel=new Label("仓库：");
        Text storehouse=new Text();
        storehouse.setText(thisstockinbill.getBuyinStoreHouse());

        Label GoodsList=new Label("出货商品清单");


        return gridPane;
    }

    private Pane SelloutPane(String name){
        SelloutBill thisselloutBill=new SelloutBill();
        for(SelloutBill bill:selloutBillList){
            if(bill.getSelloutID().equals(name)){
                thisselloutBill=bill;
                break;
            }
        }

        selloutVO vo=selloutBillMakeController.selloutToVo(thisselloutBill);

        GridPane gridPane=new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(12);
        gridPane.setMinHeight(450);
        gridPane.setMinWidth(1050);

        Label label=new Label("单据类型");


        return gridPane;
    }
}