package com.bluedogs.simbora.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bluedogs.simbora.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class InicialActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private MarkerOptions markerOptions;
    private Marker marker;
    private Polyline caminho;
    private AlertDialog alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Toolbar toolbar = (Toolbar) findViewById(R.id.inicial_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mMap = mapFragment.getMap();
        markerOptions = new MarkerOptions();
        caminho = mMap.addPolyline(new PolylineOptions().add(new LatLng(0,0)).add(new LatLng(1,1)));
        caminho.setVisible(false);
        marker = mMap.addMarker(new
                MarkerOptions().position(new LatLng(0f, 0f)).visible(false));

        FloatingActionButton fabPedir,fabOferecer;
        fabPedir  = (FloatingActionButton) findViewById(R.id.inicial_fab_pedir);
        fabOferecer = (FloatingActionButton) findViewById(R.id.inicial_fab_oferecer);
        fabPedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!marker.isVisible())
                    Toast.makeText(getApplicationContext(), "Marque uma posicao", Toast.LENGTH_SHORT).show();
                else
                    alertDialogPedirCarona();
            }
        });

        fabOferecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!marker.isVisible())
                    Toast.makeText(getApplicationContext(), "Marque uma posicao", Toast.LENGTH_SHORT).show();
                else
                    alertDialogOferecerCarona();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    private void alertDialogPedirCarona() {
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.alertdialog_inicial_pedir, null);

        EditText origem =(EditText) view.findViewById(R.id.alertdialog_inicial_edt_coordorigem),
                destino =(EditText) view.findViewById(R.id.alertdialog_inicial_edt_coorddestino);
        origem.setText("My position");
        destino.setText("Local Escolhido");


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pedindo Carona");
        builder.setView(view);
        builder.setPositiveButton("Confirmar Carona", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Pedido feito com sucesso !",Toast.LENGTH_SHORT).show();
                caminho.setVisible(false);
                marker.setVisible(false);
            }
        });
        builder.setNegativeButton("Cancelar Carona", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Pedido cancelado !",Toast.LENGTH_SHORT).show();
                caminho.setVisible(false);
                marker.setVisible(false);
            }
        });
        alerta = builder.create();
        alerta.show();
    }


    private void alertDialogOferecerCarona() {
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.alertdialog_inicial_oferecer, null);

        EditText origem =(EditText) view.findViewById(R.id.alertdialog_oferecer_edt_coordorigem),
                destino =(EditText) view.findViewById(R.id.alertdialog_oferecer_edt_coorddestino);
        origem.setText("My position");
        destino.setText("Local Escolhido");


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Oferecendo Carona");
        builder.setView(view);
        builder.setPositiveButton("Confirmar Carona", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Pedido feito com sucesso !",Toast.LENGTH_SHORT).show();
                caminho.setVisible(false);
                marker.setVisible(false);
            }
        });
        builder.setNegativeButton("Cancelar Carona", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Pedido cancelado !",Toast.LENGTH_SHORT).show();
                caminho.setVisible(false);
                marker.setVisible(false);
            }
        });
        alerta = builder.create();
        alerta.show();
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setTrafficEnabled(true);
        //mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        caminho.setVisible(false);
                        marker.setVisible(false);
                        marker = mMap.addMarker(new
                                MarkerOptions().position(new LatLng(0f, 0f)).visible(false));
                    }
                });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                markerOptions = markerOptions.anchor(0.0f, 1.0f).position(latLng);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                marker.remove();
                marker = mMap.addMarker(markerOptions.title("Posição Escolhida")
                        .snippet(latLng.latitude + "," + latLng.longitude).draggable(true));
                caminho.remove();
                caminho = mMap.addPolyline(new PolylineOptions().add(new LatLng(mMap.getMyLocation().getLatitude(),
                        mMap.getMyLocation().getLongitude())).add(marker.getPosition()));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,ConfiguracaoActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            startActivity(new Intent(this,PerfilActivity.class));
        } else if (id == R.id.nav_notifications) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_novidades) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

