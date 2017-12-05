package client.Presentation.AccountantUI.BillFill;

import client.BL.Accountant.FinancialAccountbl.Account;
import client.BL.Accountant.FinancialPaybl.FinancialPayController;
import client.BL.Accountant.FinancialReceivebl.AccountBill;
import client.BL.Accountant.FinancialReceivebl.Consumer;
import client.BL.Accountant.FinancialReceivebl.FinancialReceiveController;
import client.RMI.link;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class PayUI extends Application {

    final String[] imageNames = new String[]{"账户列表", "客户列表", "付款单草稿","已审批","正在审批"};
    final TitledPane[] tps = new TitledPane[imageNames.length];
    final TableView[] table = new TableView[5];



    private final TableView<Account> accounttable = new TableView<>();
    private final ObservableList<Account> accountdata =
            FXCollections.observableArrayList(
                    new Account("A", "B", "C"),
                    new Account("Q", "W", "E"));
    private final TableView<Consumer> consumertable = new TableView<>();
    private final ObservableList<Consumer> consumerdata =
            FXCollections.observableArrayList(
                    new Consumer("A", "B", "C","A", "B", "C","B", "C"),
                    new Consumer("b", "B", "C","A", "B", "C","B", "C"));

    private final TableView<AccountBill> draftbilltable = new TableView<>();
    private final ObservableList<AccountBill> draftbilldata =
            FXCollections.observableArrayList();

    private final TableView<AccountBill> UnderPromotionbilltable = new TableView<>();
    private final ObservableList<AccountBill> UnderPromotionbilldata =
            FXCollections.observableArrayList();

    private final TableView<AccountBill> AlreadyPromotionbilltable = new TableView<>();
    private final ObservableList<AccountBill> AlreadyPromotionbilldata =
            FXCollections.observableArrayList();



    final HBox hb = new HBox();
    final VBox vb1 = new VBox();
    final VBox vb2 = new VBox();

    FinancialPayController PayController  = new FinancialPayController();

    public static void main(String[] args) {
        link.linktoServer();
        launch(args);
    }

    @Override public void start(Stage stage) {
        stage.setTitle("制定付款单");
        Scene scene = new Scene(new Group(), 800, 250);

//////////////////////////////////////////////////////////////////////////////////////////////////////////
        accounttable.setEditable(true);
        TableColumn<Account, String> IDCol =
                new TableColumn<>("账户编号");
        TableColumn<Account, String> NameCol =
                new TableColumn<>("账户名称");
        TableColumn<Account, String> MoneyCol =
                new TableColumn<>("账户余额");
        IDCol.setMinWidth(100);
        IDCol.setCellValueFactory(
                param -> param.getValue().accountID);
        NameCol.setMinWidth(100);
        NameCol.setCellValueFactory(
                param -> param.getValue().accountName);
        MoneyCol.setMinWidth(200);
        MoneyCol.setCellValueFactory(
                param -> param.getValue().money);
        try {
            ArrayList<Account> list =PayController.getAllAccount();
            accountdata.clear();
            accountdata.addAll(list);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        accounttable.setItems(accountdata);
        accounttable.getColumns().addAll(IDCol, NameCol, MoneyCol);

///////////////////////////////////////////////////////////////////////////////////////////////////////
        TableColumn<Consumer, String> ConsumerIDCol =
                new TableColumn<>("客户编号");
        ConsumerIDCol.setMinWidth(100);
        ConsumerIDCol.setCellValueFactory(
                param -> param.getValue().consumerID);
        TableColumn<Consumer, String> ConsumerNameCol =
                new TableColumn<>("客户名称");
        ConsumerNameCol.setMinWidth(100);
        ConsumerNameCol.setCellValueFactory(
                param -> param.getValue().consumerName);
        TableColumn<Consumer, String> ConsumerLevelCol =
                new TableColumn<>("客户星级");
        ConsumerLevelCol.setMinWidth(100);
        ConsumerLevelCol.setCellValueFactory(
                param -> param.getValue().consumerLevel);
        TableColumn<Consumer, String> StaffCol =
                new TableColumn<>("业务员");
        StaffCol.setMinWidth(100);
        StaffCol.setCellValueFactory(
                param -> param.getValue().staff);
        TableColumn<Consumer, String> InOutGapCol =
                new TableColumn<>("收付差额");
        InOutGapCol.setMinWidth(100);
        InOutGapCol.setCellValueFactory(
                param -> param.getValue().inOutGap);
        TableColumn<Consumer, String> DueINCol =
                new TableColumn<>("应收差额");
        DueINCol.setMinWidth(100);
        DueINCol.setCellValueFactory(
                param -> param.getValue().dueIN);
        TableColumn<Consumer, String> ActualINCol =
                new TableColumn<>("实收金额");
        ActualINCol.setMinWidth(100);
        ActualINCol.setCellValueFactory(
                param -> param.getValue().actualIN);
        TableColumn<Consumer, String> DuePayCol =
                new TableColumn<>("应收额度");
        DuePayCol.setMinWidth(100);
        DuePayCol.setCellValueFactory(
                param -> param.getValue().duePay);

        try {
            ArrayList<Consumer> list =PayController.getAllConsumer();
            consumerdata.clear();
            consumerdata.addAll(list);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        consumertable.setItems(consumerdata);
        consumertable.getColumns().addAll(ConsumerIDCol,ConsumerNameCol,ConsumerLevelCol,StaffCol,InOutGapCol,DueINCol,ActualINCol,DuePayCol);

////////////////////////////////////////////////////////////////////////////////////////////

        TableColumn<AccountBill, String> BillIDCol =
                new TableColumn<>("单据编号");
        TableColumn<AccountBill, String> BillTypeCol =
                new TableColumn<>("单据类型");
        TableColumn<AccountBill, String> BillEditCol =
                new TableColumn<>("编辑单据");
        BillIDCol.setMinWidth(100);
        BillIDCol.setCellValueFactory(
                param -> param.getValue().keyno);
        BillTypeCol.setMinWidth(100);
        BillTypeCol.setCellValueFactory(
                param -> param.getValue().kind);
        BillEditCol.setMinWidth(200);
        BillEditCol.setCellFactory((col) -> {
            TableCell<AccountBill, String> cell = new TableCell<AccountBill, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Button editBtn = new Button("编辑收款单");
                        this.setGraphic(editBtn);
                        editBtn.setOnMouseClicked((me) -> {
                            String keyno = draftbilldata.get(this.getIndex()).getKeyno().toString();
                            try {
                                PayController.ReEditBill(keyno);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }

                        });
                    }
                }

            };
            return cell;
        });


        try {
            ArrayList<AccountBill> list =PayController.getAllDraftPay();
//            System.out.println("Draft "+list.size()+" "+list.get(0).getKeyno());
            draftbilldata.clear();
            draftbilldata.addAll(list);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        draftbilltable.setItems(draftbilldata);
        draftbilltable.getColumns().addAll(BillIDCol,BillTypeCol,BillEditCol);


//////////////////////////////////////////////////////////////////////////////////////////////



        TableColumn<AccountBill, String> BillIDCol1 =
                new TableColumn<>("单据编号");
        TableColumn<AccountBill, String> BillTypeCol1 =
                new TableColumn<>("单据类型");
        TableColumn<AccountBill, String> BillDetailCol1 =
                new TableColumn<>("详细内容");
        BillIDCol1.setMinWidth(100);
        BillIDCol1.setCellValueFactory(
                param -> param.getValue().keyno);
        BillTypeCol1.setMinWidth(100);
        BillTypeCol1.setCellValueFactory(
                param -> param.getValue().kind);
        BillDetailCol1.setMinWidth(200);
        BillDetailCol1.setCellFactory((col) -> {
            TableCell<AccountBill, String> cell = new TableCell<AccountBill, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Button detailBtn = new Button("详细信息");
                        this.setGraphic(detailBtn);
                        detailBtn.setOnMouseClicked((me) -> {
                            String keyno = draftbilldata.get(this.getIndex()).getKeyno().toString();
                            try {
                                PayController.ReEditBill(keyno);/////////////////////////////////////////////////
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }

                        });
                    }
                }

            };
            return cell;
        });

        try {
            ArrayList<AccountBill> list =PayController.getAllPromotedPay();
//            System.out.println("AlR "+list.size()+" "+list.get(0).getKeyno());
            AlreadyPromotionbilldata.clear();
            AlreadyPromotionbilldata.addAll(list);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        AlreadyPromotionbilltable.setItems(AlreadyPromotionbilldata);
        AlreadyPromotionbilltable.getColumns().addAll(BillIDCol1,BillTypeCol1,BillDetailCol1);

/////////////////////////////////////////////////////////////////////////////////////////////


        TableColumn<AccountBill, String> BillIDCol2 =
                new TableColumn<>("单据编号");
        TableColumn<AccountBill, String> BillTypeCol2 =
                new TableColumn<>("单据类型");
        TableColumn<AccountBill, String> BillDetailCol2 =
                new TableColumn<>("详细内容");
        BillIDCol2.setMinWidth(100);
        BillIDCol2.setCellValueFactory(
                param -> param.getValue().keyno);
        BillTypeCol2.setMinWidth(100);
        BillTypeCol2.setCellValueFactory(
                param -> param.getValue().kind);
        BillDetailCol2.setMinWidth(200);
        BillDetailCol2.setCellFactory((col) -> {
            TableCell<AccountBill, String> cell = new TableCell<AccountBill, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Button detailBtn = new Button("详细信息");
                        this.setGraphic(detailBtn);
                        detailBtn.setOnMouseClicked((me) -> {
                            String keyno = draftbilldata.get(this.getIndex()).getKeyno().toString();
                            try {
                                PayController.ReEditBill(keyno);/////////////////////////////////////////////////
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }

                        });
                    }
                }

            };
            return cell;
        });

        try {
            ArrayList<AccountBill> list =PayController.getAllUnderPromotedPay();
//            System.out.println("Under "+list.size()+" "+list.get(0).getKeyno());
            UnderPromotionbilldata.clear();
            UnderPromotionbilldata.addAll(list);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        UnderPromotionbilltable.setItems(UnderPromotionbilldata);
        UnderPromotionbilltable.getColumns().addAll(BillIDCol2,BillTypeCol2,BillDetailCol2);


//////////////////////////////////////////////////////////////////////////////////////////////
        table[0] = accounttable;
        table[1] = consumertable;
        table[2] = draftbilltable;
        table[3] = AlreadyPromotionbilltable;
        table[4] = UnderPromotionbilltable;
        // --- Accordion
        final Accordion accordion = new Accordion ();
        for (int i = 0; i < imageNames.length; i++) {
            tps[i] = new TitledPane(imageNames[i],table[i]);
        }
        accordion.getPanes().addAll(tps);

        final Button refresh = new Button("刷新列表");
        refresh.setOnAction(e -> {
            refresh();
        });

        final Button newBill = new Button("新建收款单");

        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(20, 0, 0, 20));
        hbox.getChildren().setAll(refresh,newBill);

        VBox vb = new VBox();
        vb.getChildren().setAll(accordion,hbox);

        Group root = (Group)scene.getRoot();
        root.getChildren().add(vb);
        stage.setScene(scene);
        stage.show();
    }



    public void refresh() {
        try {
            ArrayList<Account> list1 =PayController.getAllAccount();
            accountdata.clear();
            accountdata.addAll(list1);
            ArrayList<Consumer> list2 =PayController.getAllConsumer();
            consumerdata.clear();
            consumerdata.addAll(list2);
            ArrayList<AccountBill> list3 =PayController.getAllDraftPay();
            draftbilldata.clear();
            draftbilldata.addAll(list3);
            ArrayList<AccountBill> list4 =PayController.getAllPromotedPay();
            AlreadyPromotionbilldata.clear();
            AlreadyPromotionbilldata.addAll(list4);
            ArrayList<AccountBill> list5 =PayController.getAllUnderPromotedPay();
            UnderPromotionbilldata.clear();
            UnderPromotionbilldata.addAll(list5);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
