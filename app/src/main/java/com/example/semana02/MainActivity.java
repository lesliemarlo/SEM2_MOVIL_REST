package com.example.semana02;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.semana02.entity.User;
import com.example.semana02.service.ServiceUser;
import com.example.semana02.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Spinner   spnUsuarios;
    ArrayAdapter<String> adaptadorUsuarios;
    ArrayList<String> listaUsuarios = new ArrayList<String>();

    Button   btnFiltrar;
    TextView txtResultado;

    //conecta al servicio REST
    ServiceUser serviceUser;

    private List<User> listaTotalUsuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Relaciona las variables con las variables de la GUI
        adaptadorUsuarios = new ArrayAdapter<String>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listaUsuarios);
        spnUsuarios = findViewById(R.id.spnUsuarios);
        spnUsuarios.setAdapter(adaptadorUsuarios);

        btnFiltrar = findViewById(R.id.btnFiltrar);
        txtResultado = findViewById(R.id.txtResultado);

        //Conecta al servicio REST
        serviceUser = ConnectionRest.getConnecion().create(ServiceUser.class);

        cargaUsuarios();

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spnUsuarios.getSelectedItem().toString();
                int position = spnUsuarios.getSelectedItemPosition();
                String id = item.split("-")[0];
                String nombre = item.split("-")[1];

                User objUserSeleccionada = listaTotalUsuarios.get(position);

                String salida =  "Users: \n\n";
                salida +=  "Position  " + position +"\n";
                salida +=  "Id  " + id +"\n";
                salida +=  "UserName  " + objUserSeleccionada.getUsername() +"\n";
                salida +=  "Email  " + objUserSeleccionada.getEmail() +"\n";
                salida +=  "Street  " + objUserSeleccionada.getAddress().getStreet() +"\n";
                salida +=  "Suite  " + objUserSeleccionada.getAddress().getSuite() +"\n";
                salida +=  "City  " + objUserSeleccionada.getAddress().getCity() +"\n";
                salida +=  "ZipCode  " + objUserSeleccionada.getAddress().getZipcode() +"\n";
                salida +=  "Latitud  " + objUserSeleccionada.getAddress().getGeo().getLat()+"\n";
                salida +=  "Longitud  " + objUserSeleccionada.getAddress().getGeo().getLng() +"\n";
                salida +=  "Telefon  " + objUserSeleccionada.getPhone() +"\n";
                salida +=  "Website  " + objUserSeleccionada.getWebsite() +"\n";





                txtResultado.setText(salida);

            }
        });


    }

    void cargaUsuarios(){
        Call<List<User>> call = serviceUser.listausuarios();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){
                    listaTotalUsuarios = response.body();
                    for(User x:listaTotalUsuarios){
                        listaUsuarios.add(x.getId() + " - " + x.getUsername());
                    }
                    adaptadorUsuarios.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }

}