package com.example.tugasakhir.ui.Transaction;

import android.util.Log;

import com.example.tugasakhir.data.model.Cart.ResponseListCarts;
import com.example.tugasakhir.data.model.Product.ResponseProduct;
import com.example.tugasakhir.data.model.ResponseData;
import com.example.tugasakhir.data.model.Transaction.ResponseListTrans;
import com.example.tugasakhir.data.network.RetrofitClient;
import com.example.tugasakhir.util.Constant;

import org.stellar.sdk.AssetTypeNative;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;
import org.stellar.sdk.Network;
import org.stellar.sdk.PaymentOperation;
import org.stellar.sdk.Server;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.SubmitTransactionResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionPresenter {
    private static final String TAG = "TransactionPresenter" ;
    private TransactionView transactionView;

    public  TransactionPresenter (TransactionView transactionView){
        this.transactionView = transactionView;
    }

    public void getTransByUser(int id){
        RetrofitClient.getInstance()
                .getApi()
                .getTransByUser(id)
                .enqueue(new Callback<ResponseListTrans>() {
                    @Override
                    public void onResponse(Call<ResponseListTrans> call, Response<ResponseListTrans> response) {
                        if (response.isSuccessful()){
                            transactionView.successListTrans(response.body().getData());
                            Log.d(TAG, "onResponse: " + response.body().getData());
                        } else {
                            Log.e(TAG, "onResponse: "+ response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseListTrans> call, Throwable t) {
                        Log.e(TAG, "onResponse: "+ t.getMessage());
                    }
                });
    }

    public void getDetailTrans(int id){
        RetrofitClient.getInstance()
                .getApi()
                .getDetailByTrans(id)
                .enqueue(new Callback<ResponseListTrans>() {
                    @Override
                    public void onResponse(Call<ResponseListTrans> call, Response<ResponseListTrans> response) {
                        if (response.isSuccessful()){
                            Log.d(TAG, "onResponse: " + response.body().getData());
                        } else {
                            Log.e(TAG, "onResponse: "+ response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseListTrans> call, Throwable t) {
                        Log.e(TAG, "onResponse: "+ t.getMessage());
                    }
                });
    }

    public void addTransaction(String stellarId, String secSeed) throws IOException {
        String amount = transactionView.getAmount();
        String note = transactionView.getNote();

        Server server = new Server(Constant.STELLAR_TEST_SERVER_URL);
        KeyPair source = KeyPair.fromSecretSeed(secSeed);

        AccountResponse destinationUser = server.accounts().account(Constant.ID_DESTINATION);
        AccountResponse sourceAccount = server.accounts().account(stellarId);
        if (destinationUser.getAccountId().isEmpty()){
            transactionView.failed("Akun admin tidak tersedia");
        } else if (sourceAccount.getAccountId().isEmpty()){
            transactionView.failed("Akun stellar anda tidak tersedia");
        } else {
            Transaction transaction = new Transaction.Builder(sourceAccount, Network.TESTNET)
                    .addOperation(new PaymentOperation.Builder(Constant.ID_DESTINATION, new AssetTypeNative(), "10").build())
                    .addMemo(Memo.text(note))
                    .setTimeout(180)
                    .build();
            transaction.sign(source);

            try {
                SubmitTransactionResponse response = server.submitTransaction(transaction);
                Log.d(TAG, "->addTransaction: success");
                transactionView.successTransaction(response.getHash());
            } catch (Exception e) {
                Log.e(TAG, "->addTransaction: error : "+ e.getMessage());
                transactionView.failed("Transaksi gagal");
            }
        }
    }

    public void getProductById(int id){
        RetrofitClient.getInstance()
                .getApi()
                .getProductById(id)
                .enqueue(new Callback<ResponseProduct>() {
                    @Override
                    public void onResponse(Call<ResponseProduct> call, Response<ResponseProduct> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus()){
                                transactionView.successProduct(response.body().getData());
                                System.out.println(TAG + "->getProductById: " + response.body().getMessage());
                            } else {
                                System.out.println(TAG + "->getProductById: " + response.body().getMessage());
                                transactionView.failed(response.body().getMessage());
                            }
                        } else {
                            transactionView.failed(response.errorBody().toString());
                            Log.e(TAG, "->getProductById: onResponse: " + response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseProduct> call, Throwable t) {
                        transactionView.failed(t.getMessage());
                        Log.e(TAG, "->getProductById: onFailure: " + t.getMessage());
                    }
                });
    }

    public void addToCart(int id_user, int id_product, int much, int total){
        RetrofitClient.getInstance()
                .getApi()
                .addCart(id_user, id_product, much, total)
                .enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus()){
                                transactionView.successCart();
                                System.out.println(TAG + "->addToCart: " + response.body().getMessage());
                            } else {
                                System.out.println(TAG + "->addToCart: " + response.body().getMessage());
                                transactionView.failed(response.body().getMessage());
                            }
                        } else {
                            transactionView.failed(response.errorBody().toString());
                            Log.e(TAG, "->addToCart: onResponse: " + response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {

                    }
                });
    }
}
