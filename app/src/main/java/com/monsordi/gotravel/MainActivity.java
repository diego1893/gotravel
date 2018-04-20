package com.monsordi.gotravel;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.monsordi.gotravel.dialog.DialogGoTravel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DialogGoTravel.DialogGoTravelTasks,
        AdapterView.OnItemClickListener{

    private static final int REQUEST_CART_ADD = 0;
    private static final int REQUEST_CART_DELETE = 1;

    @BindView(R.id.main_toolbar) Toolbar toolbar;
    @BindView(R.id.main_listview) ListView listView;

    private MyPreference preference;
    private String token;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.home));

        preference = new MyPreference(this);

        /*Checks if there is a current active user. If yes, a welcome greeting appears on the
         screen. On the contrary, the user is taken to the SignInActivity*/
        if(preference.isFirsTime()){
            startActivity(new Intent(this,SignInActivity.class));
            finish();
        } else {
            token = preference.getToken();
            Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
            id = preference.getId();
            createList();
        }
    }

    //****************************************************************************************************************

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    //****************************************************************************************************************

    private void createList(){

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    //****************************************************************************************************************

    //Methods related to the menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            /*When clicking the sign out button, the user is warned through a custom dialog
            if he really wants to complete the action.*/
            case R.id.menu_sign_out:
                DialogGoTravel dialogGoTravel = new DialogGoTravel(this,this);
                dialogGoTravel.showGoTravelDialog(getString(R.string.sign_out));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    //****************************************************************************************************************

    //The next two following methods are the ones related to the selection in the appearing dialog.

    //Closes dialog
    @Override
    public void doCancelTask(Dialog dialog) {
        dialog.dismiss();
    }


    //Signs out and navigates to the SignInActivity
    @Override
    public void doOkTask(Dialog dialog) {
        preference.setOld(false);
        startActivity(new Intent(this,SignInActivity.class));
        finish();
    }
}
