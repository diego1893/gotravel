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
import com.monsordi.gotravel.shop.DetailActivity;
import com.monsordi.gotravel.shop.Travel;
import com.monsordi.gotravel.shop.TravelAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DialogGoTravel.DialogGoTravelTasks,
        AdapterView.OnItemClickListener,TravelAdapter.OnClickListener{

    private static final int REQUEST_CART_ADD = 0;
    private static final int REQUEST_CART_DELETE = 1;

    @BindView(R.id.main_toolbar) Toolbar toolbar;
    @BindView(R.id.main_listview) ListView listView;

    private ArrayList<Travel> travelList;
    private ArrayList<Travel> cartList;
    private MyPreference preference;
    private String token;

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
            Toast.makeText(this, getString(R.string.welcome), Toast.LENGTH_SHORT).show();
            token = preference.getToken();
            createList();
        }
    }

    //****************************************************************************************************************

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CART_ADD && resultCode == RESULT_OK){
            int position = data.getIntExtra(getString(R.string.position_key),-1);
            int numberOfAddings = data.getIntExtra(getString(R.string.addings),-1);
            for(int i=0;i<numberOfAddings;i++){
            cartList.add(travelList.get(position));
            }
        } else if (requestCode == REQUEST_CART_DELETE && resultCode == RESULT_OK){
            cartList = (ArrayList<Travel>) data.getSerializableExtra(getString(R.string.cart_list_key));
        }
    }


    //****************************************************************************************************************

    private void createList(){
        travelList = new ArrayList<>();
        cartList = new ArrayList<>();

        travelList.add(new Travel("Machu Picchu","Blah blah",549.99f,new int[]{R.drawable.peru_machu,R.drawable.peru_ge,R.drawable.peru_llama},"Perú"));
        travelList.add(new Travel("Vienna Opera","Blah blah",789.99f,new int[]{R.drawable.austria_opera,R.drawable.vienna_schonbrunn,R.drawable.vienna_sacher},"Austria"));
        travelList.add(new Travel("China Wall","Blah blah",659.99f,new int[]{R.drawable.china_wall,R.drawable.china_palace,R.drawable.china_beijing},"China"));
        travelList.add(new Travel("Chichen Itza","Blah blah",599.99f,new int[]{R.drawable.mexico_chichen,R.drawable.mexico_hierve,R.drawable.mexico_coloradas},"México"));
        travelList.add(new Travel("Petra","Blah blah",749.99f,new int[] {R.drawable.jord_petra,R.drawable.jord_2,R.drawable.jord_oasis},"Jordania"));
        travelList.add(new Travel("Budapest Parliament","Blah blah",899.99f,new int[]{R.drawable.hungary_parliament,R.drawable.hungary_bastion,R.drawable.hungary_chain},"Hungary"));
        travelList.add(new Travel("Colosseum","Blah blah",1299.99f,new int[]{R.drawable.italy_colosseum,R.drawable.italy_firenze,R.drawable.italy_cinque_terre},"Italy"));

        TravelAdapter travelAdapter = new TravelAdapter(this,travelList,this);
        listView.setOnItemClickListener(this);
        listView.setAdapter(travelAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(getString(R.string.travel_key),travelList.get(position));
        intent.putExtra(getString(R.string.position_key),position);
        startActivityForResult(intent, REQUEST_CART_ADD);
    }

    @Override
    public void OnClick(int position) {
        Toast.makeText(this,getString(R.string.added_to_cart),Toast.LENGTH_SHORT).show();
        cartList.add(travelList.get(position));
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
