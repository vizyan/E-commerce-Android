package com.example.tugasakhir.ui.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.Cart.DataCart;
import com.example.tugasakhir.data.model.Product.DataProduct;
import com.example.tugasakhir.data.model.Stellar.ExchangeBtc.Averages;
import com.example.tugasakhir.data.model.User.DataUser;
import com.example.tugasakhir.ui.Auth.SigninActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.List;

public class DashboardActivity extends AppCompatActivity implements DashboardView {

    private DashboardPresenter dashboardPresenter;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Toolbar toolbar;

    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_shopping_cart_black,
            R.drawable.ic_person_black
    };

    String balance, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }

        initPresenter();
        initView();
        initActionBar();
        initTabAdapter();
    }

    private void initPresenter(){
        this.dashboardPresenter = new DashboardPresenter(this);
    }

    private void initView(){
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        toolbar = findViewById(R.id.toolbar);
    }

    private void initActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
    }

    private void initTabAdapter(){
        adapter = new TabAdapter(getSupportFragmentManager(), this);
        adapter.addFragment(new HomeFragment(), "Home", tabIcons[0]);
        adapter.addFragment(new CartFragment(), "Keranjang", tabIcons[1]);
        adapter.addFragment(new UserFragment(), "Akun", tabIcons[2]);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        highLightCurrentTab(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                initTab(position);
                highLightCurrentTab(position);
                initTitle(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initTab(int position) {
        if (position!=0){
            initPreference();
        }
    }

    private void initTitle(int position){
        if (position==0){
            getSupportActionBar().setTitle("Home");
        } else if (position==1){
            getSupportActionBar().setTitle("Keranjang");
        } else {
            getSupportActionBar().setTitle("Profil");
        }
    }

    private void initPreference(){
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        String userPref = preferences.getString("dataUser", "");
        password = preferences.getString("password", "");
        balance = preferences.getString("balance", "");

        if (userPref.isEmpty()){
            Intent intent = getIntent();
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);

            Intent signIn = new Intent(this, SigninActivity.class);
            startActivity(signIn);
        } else {
            Gson gson = new Gson();
            DataUser dataUser = gson.fromJson(userPref, DataUser.class);
            dashboardPresenter.login(dataUser.getEmail(), password);
            dashboardPresenter.getCartByUser(dataUser.getId());
        }
    }

    private void highLightCurrentTab(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adapter.getTabView(i));
        }

        TabLayout.Tab tab = tabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(adapter.getSelectedTabView(position));
    }

    private void savePreference(DataUser dataUser, String balance){
        Gson gson = new Gson();
        String DataUser = gson.toJson(dataUser);
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password", password);
        editor.putString("balance", balance);
        editor.putString("dataUser", DataUser);
        editor.commit();
        editor.apply();
    }

    @Override
    public void successProduct(List<DataProduct> data) {

    }

    @Override
    public void successUser(DataUser dataUser, String balance) {
//        savePreference(dataUser, balance);
    }

    @Override
    public void successExchange(Averages averages) {

    }

    @Override
    public void successCart(List<DataCart> data) {

    }

    @Override
    public void success(Integer data) {

    }

    @Override
    public void failed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
