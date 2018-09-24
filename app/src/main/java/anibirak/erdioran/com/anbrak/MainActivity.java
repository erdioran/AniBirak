package anibirak.erdioran.com.anbrak;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;



import anibirak.erdioran.com.anbrak.Fragments.HomeFragment;
import anibirak.erdioran.com.anbrak.Fragments.NotificationsFragment;
import anibirak.erdioran.com.anbrak.Fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener{

    private ActionBar actionBar;
    private Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        BottomNavigationView navigation = findViewById(R.id.bottomNavigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFagment(new HomeFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.optionsMenu=menu;
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.actions_main,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                refresh();
                break;
            case R.id.direct_message:
                direcMessage();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    public void refresh(){
        setRefreshActionButtonState(true);  //Progress bar refresh iconla değişecek
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefreshActionButtonState(false); //3.sn sonra refresh iconu geri gelecek
            }
        },3000);
    }

    //refresh icona tıklandıktan sonra progress bar gözükmesi için
    public void setRefreshActionButtonState(final boolean refreshing) {
        if (optionsMenu!=null){
            final MenuItem logoItem=optionsMenu.findItem(R.id.search);
            if (logoItem!=null){
                if (refreshing){
                    logoItem.setActionView(R.layout.actionbar_refresh_progress);
                }else {
                    logoItem.setActionView(null);
                }
            }
        }
    }

    public void direcMessage(){
        Intent intent=new Intent(getApplicationContext(),DirectMessage.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.navigation_home :
                fragment=new HomeFragment();
                break;
            case R.id.navigation_profile :
                fragment=new ProfileFragment();
                break;
            case R.id.navigation_notifications :
                fragment=new NotificationsFragment();
                break;
        }
        return loadFagment(fragment);
    }


    private boolean loadFagment(Fragment fragment){

        if (fragment !=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer,fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() { }
}
