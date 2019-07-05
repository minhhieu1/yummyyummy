package com.minhhieu.yummyyummy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import com.minhhieu.yummyyummy.model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Database {
    //    private static final String name="yummy.db";
//    private static final int version=1;
//    public Database(Context context) {
//        super(context, name, null, version);
//    }
//    public List<Order> getCart(){
//        SQLiteDatabase db=getReadableDatabase();
//        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
//
//        String[] sqlSelect={"ProductName", "ProductID", "Quantity", "Price", "Discount"};
//        String sqlTable="OrdelDetail";
//        qb.setTables(sqlTable);
//        Cursor c=qb.query(db, sqlSelect, null, null, null, null, null);
//        final List<Order> result=new ArrayList<>();
//        if(c.moveToFirst()){
//            do{
//                result.add(new Order(c.getString(c.getColumnIndex("ProductID")),
//                        c.getString(c.getColumnIndex("ProductName")),
//                        c.getString(c.getColumnIndex("Quantity")),
//                        c.getString(c.getColumnIndex("Price")),
//                        c.getString(c.getColumnIndex("Discount"))
//
//                ));
//            }while (c.moveToNext());
//        }
//        return result;
//    }
//
//    public void addToCart(Order order){
//        SQLiteDatabase db=getReadableDatabase();
//        String query=String.format("Insert into OrderDetail(ProductID, ProductName, Quantity, Price, Discount) VALUE('%s','%s','%s','%s','%s');",
//                order.getProductID(),
//                order.getProductName(),
//                order.getQuantity(),
//                order.getPrice(),
//                order.getDiscount());
//        db.execSQL(query);
//    }
//    public void cleanCart(Order order){
//        SQLiteDatabase db=getReadableDatabase();
//        String query=String.format("Delete from OrderDetail");
//        db.execSQL(query);
//    }
    String DATABASE_NAME = "yummy.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase db = null;

    Context context;
    
    public Database(Context context){
        this.context=context;
        processSQLite();
    }

    private void processSQLite() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            try{
                CopyDatabaseFromAsset();
                Toast.makeText(context, "Copy successful !!!", Toast.LENGTH_SHORT).show();

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void CopyDatabaseFromAsset() {
        try{
            InputStream databaseInputStream = context.getAssets().open(DATABASE_NAME);

            String outputStream = getPathDatabaseSystem();

            File file = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!file.exists()){
                file.mkdir();
            }

            OutputStream databaseOutputStream = new FileOutputStream(outputStream);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = databaseInputStream.read(buffer)) > 0){
                databaseOutputStream.write(buffer,0,length);
            }


            databaseOutputStream.flush();
            databaseOutputStream.close();
            databaseInputStream.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private String getPathDatabaseSystem() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }
    public  List<Order> getCart(){
        List<Order> result=new ArrayList<>();

        db=context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor=db.query("OrderDetail",
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            int ID=cursor.getInt(0);
            String ProductID=cursor.getString(1);
            String ProductName=cursor.getString(2);
            String Quantity=cursor.getString(3);
            String Price=cursor.getString(4);
            String Discount=cursor.getString(5);
            Order order=new Order(ProductID, ProductName, Quantity, Price, Discount);
            result.add(order);
        }

        return result;
    }
    public void addToCart(Order order){
        db=context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        ContentValues contentValues=new ContentValues();
        contentValues.put("ProductID", order.getProductID());
        contentValues.put("ProductName", order.getProductName());
        contentValues.put("Quantity", order.getQuantity());
        contentValues.put("Price", order.getPrice());
        contentValues.put("Discount", order.getDiscount());
        db.insert("OrderDetail", null, contentValues);
    }
    public void cleanCart(){
        db=context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        db.delete("OrderDetail", null,null);

    }
}
