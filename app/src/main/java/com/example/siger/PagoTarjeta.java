package com.example.siger;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.model.StripeIntent;
import com.stripe.android.payments.paymentlauncher.PaymentLauncher;
import com.stripe.android.view.CardInputWidget;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PagoTarjeta extends AppCompatActivity {

    // ip de mi ordenador 192.168.0.18
    private static final String BACKEND_URL = "http://192.168.0.18:4242";
    EditText numTarget;
    CardInputWidget cardInputWidget;
    Button payButton ;

    //paymenIntentClienteSecret espara iniciar la trasaccion
    private String paymenIntentClienteSecret;

    //declaro el stripe
    private Stripe stripe;

    Double cantDouble=null;

    private OkHttpClient httpClient;

    static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pago_tarjeta);

        numTarget = findViewById(R.id.amount_id);
        cardInputWidget = findViewById(R.id.cardInputWidget);
        payButton = findViewById(R.id.payButton);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Transaccion en progreso");
        progressDialog.setCancelable(false);
        httpClient=new OkHttpClient();

        //Inicializar
        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull("pk_test_51ISLoeDrYpYnN0xnqW7bZ0tJKmtxUEdYOhD8AXoO10S9aMSXZ8Hk6e7EXJvKpn476isXZXgdG5R5TAj7aVXceJZo00bIx1MjgM")
        );

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cantDouble==null){

                    Toast.makeText(getApplicationContext(), "Error: campos vacios", Toast.LENGTH_SHORT).show();
                }else{

                    //obtener Cantidad.
                    cantDouble = Double.valueOf(numTarget.getText().toString());
                    //Hacer la llamada al pago para obtner la clave paymenintenClienteSecret
                    progressDialog.show();
                    startCheckout();

                }



            }
        });
    }

    private void startCheckout() {
        {

            //Crear un PaymentIntent para llamar al punto final del servidor
            MediaType mediaType = MediaType.get("application/json; charset=utf8");
            double cant = cantDouble * 100;
            Map<String, Object> pagoMap = new HashMap<>();
            Map<String, Object> itemMap = new HashMap<>();
            List<Map<String, Object>> itemList = new ArrayList<>();
            pagoMap.put("divisa", "INR");
            itemMap.put("Cantidad", cant);
            itemList.add(itemMap);
            pagoMap.put("items", itemList);
            String json = new Gson().toJson(pagoMap);
            RequestBody body = RequestBody.create(json, mediaType);
            Request request = new Request.Builder()
                    .url(BACKEND_URL)
                    .post(body)
                    .build();
            httpClient.newCall(request)
                    .enqueue(new PayCallback(this));
        }
    }

    private static final class PayCallback implements Callback{

        @NonNull
        private final WeakReference<PagoTarjeta> activityRef;
        PayCallback(@NonNull PagoTarjeta pago){
            activityRef =new WeakReference<>(pago);
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            progressDialog.dismiss();
            final PagoTarjeta pago = activityRef.get();
            if (pago == null){
                return;
            }
            pago.runOnUiThread(()->
                    Toast.makeText(pago, "Error"+e.toString(), Toast.LENGTH_SHORT).show()
            );

        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
              final PagoTarjeta pago = activityRef.get();
              if (pago == null){
                  return;
              }
              if (!response.isSuccessful()){
                  pago.runOnUiThread(() ->
                          Toast.makeText(pago,"Error"+response.toString(),Toast.LENGTH_SHORT).show()

                  );
              }else{
                  pago.onPaymentSuccess(response);
              }


        }
    }

    private void onPaymentSuccess(Response response) throws IOException{
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String,String>>(){}.getType();
        Map<String,String> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).string(),type
        );

        paymenIntentClienteSecret=responseMap.get("ClienteSecreto");
        //Despues de obtener el secreto del cliente de pago inicia la transaccion
        //Se obtiene detalles de la tarjeta
        PaymentMethodCreateParams params=cardInputWidget.getPaymentMethodCreateParams();

        if (params !=null){
            //Usamos paymentIntenClienteSecret para iniciar la transaccion

            ConfirmPaymentIntentParams confirmParams=ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(params, paymenIntentClienteSecret);

            //Iniciar pago
            stripe.confirmPayment(PagoTarjeta.this,confirmParams);
        }
        Log.i("TAG", "onPaymentSuccess: "+paymenIntentClienteSecret);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent dato){
     super.onActivityResult(requestCode,resultCode,dato);

     //Manejar el resultado de stripe.confirmPayment
        stripe.onPaymentResult(requestCode,dato,new PaymentResultCallback(this));

    }

    private final class PaymentResultCallback implements ApiResultCallback<PaymentIntentResult>{
        @NonNull private final WeakReference<PagoTarjeta> activityRef;
        PaymentResultCallback(@NonNull PagoTarjeta pago){
            activityRef = new WeakReference<>(pago);
        }

        //Pago no completado
        @Override
        public void onError(@NonNull Exception e) {
        progressDialog.dismiss();
        final PagoTarjeta pago = activityRef.get();
        if(pago==null){
            return;
        }

        //Solicitud de pago fallida intentelo de nuevo
            pago.displaAlert("Error",e.toString());

        }

        //Si el pago es correcto
        @Override
        public void onSuccess(@NonNull PaymentIntentResult paymentIntentResult) {

            progressDialog.dismiss();
            final PagoTarjeta pago = activityRef.get();
            if (pago == null){
                return;
            }
            PaymentIntent paymentIntent = paymentIntentResult.getIntent();
            StripeIntent.Status status = paymentIntent.getStatus();
            if (status == StripeIntent.Status.Succeeded){
                //Pago completado con exito

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Toast toast = Toast.makeText(pago,"Orden exitosa",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }else if(status == StripeIntent.Status.RequiresPaymentMethod){
                    // PAgo fallido intente con otro medio de pago
                pago.displaAlert(
                        "Pago Fallido", Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );

            }

        }


    }

    private void displaAlert(@NonNull String pago_fallido,@NonNull String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(pago_fallido);
                //.setMessage(message);
        builder.setPositiveButton("Ok",null);
        builder.create().show();
    }


}


