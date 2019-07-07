package ir.mab.booksreviews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import ir.mab.booksreviews.history.BarcodeList;

public class SharedPref {

    private static SharedPref instance = null;

    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    private SharedPref(Context context){
        sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPref getInstance(Context context){

        if(instance == null)
            instance = new SharedPref(context);

        return instance;
    }

    public void putBarcodeList(BarcodeList barcodeList){
        Gson gson = new Gson();
        String barcodeListJson = gson.toJson(barcodeList,BarcodeList.class);
        editor.putString("barcode_list",barcodeListJson);
        editor.apply();
    }

    public BarcodeList getBarcodeList(){
        String barcodeListJson = sharedPreferences.getString("barcode_list",null);

        if (barcodeListJson == null)
            return new BarcodeList();
        Gson gson = new Gson();

        return gson.fromJson(barcodeListJson,BarcodeList.class);
    }
}
