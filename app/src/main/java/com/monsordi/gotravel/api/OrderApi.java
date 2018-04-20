package com.monsordi.gotravel.api;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monsordi.gotravel.dto.Almacen;
import com.monsordi.gotravel.dto.Orden;
import com.monsordi.gotravel.dto.PrestadorServicio;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego on 19/04/18.
 */

public class OrderApi implements Response.ErrorListener, Response.Listener<JSONObject> {

    public static final String ORDER = "orden/";

    private OrderListeners orderListeners;
    private ServicesListeners servicesListeners;

    public interface OrderListeners{
        void onErrorResponse(VolleyError error);
        void onResponse(Orden order);
    }

    public interface ServicesListeners{
        void onErrorResponse(VolleyError error);
        void onResponse(List<PrestadorServicio> services);
    }

    public OrderApi(OrderListeners orderListeners) {
        this.orderListeners = orderListeners;
    }
    public OrderApi(ServicesListeners servicesListeners){this.servicesListeners = servicesListeners;}

    public void createOrder(){
        String url = AppController.BASE_URL + ORDER + "crear";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        AppController.getInstance().addToRequestQueue(request);
    }

    public void searchOrderById(Long id){
        String url = AppController.BASE_URL + ORDER + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        AppController.getInstance().addToRequestQueue(request);
    }

    public void setUser(Long idUser,Long idOrder){
        String url = AppController.BASE_URL + ORDER + idOrder + "/asignar/usuario/?id=" +idUser;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        AppController.getInstance().addToRequestQueue(request);
    }

    public void setAttendant(Long idAttendant,Long idOrder){
        String url = AppController.BASE_URL + ORDER + idOrder + "/asignar/encargado/?id=" +idAttendant;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        AppController.getInstance().addToRequestQueue(request);
    }

    public void setCounselor(Long idCounselor,Long idOrder){
        String url = AppController.BASE_URL + ORDER + idOrder + "/asignar/asesor/?id=" +idCounselor;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        AppController.getInstance().addToRequestQueue(request);
    }

    public void setCelular(Long idCelular,Long idOrder){
        String url = AppController.BASE_URL + ORDER + idOrder + "/asignar/celular/?id=" +idCelular;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        AppController.getInstance().addToRequestQueue(request);
    }

    public void setWarehouse(Long idWarehouse,Long idOrder){
        String url = AppController.BASE_URL + ORDER + idOrder + "/asignar/almacen/?id=" +idWarehouse;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        AppController.getInstance().addToRequestQueue(request);
    }

    public void addService(Long idService,Long idOrder){
        String url = AppController.BASE_URL + ORDER + idOrder + "/agregar/servicio/?id=" +idService;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        AppController.getInstance().addToRequestQueue(request);
    }

    public void getServicesByOrder(Long idOrder){
        String url = AppController.BASE_URL + ORDER + idOrder + "/servicios";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,serviceOnResponseListener,servicesOnErrorListener);
        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        orderListeners.onErrorResponse(error);
    }

    @Override
    public void onResponse(JSONObject response) {
        Gson gson = new Gson();
        Orden order = gson.fromJson(response.toString(),Orden.class);
        orderListeners.onResponse(order);
    }

    Response.Listener<JSONObject> serviceOnResponseListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Type listType = new TypeToken<ArrayList<PrestadorServicio>>(){}.getType();
            List<PrestadorServicio> serviceList = new Gson().fromJson(response.toString(),listType);
            servicesListeners.onResponse(serviceList);
        }
    };

    Response.ErrorListener servicesOnErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            servicesListeners.onErrorResponse(error);
        }
    };



}
