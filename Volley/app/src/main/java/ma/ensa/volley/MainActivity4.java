package ma.ensa.volley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity4 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private EditText name, email, phone;
    private Button bnAdd;
    private Toolbar toolbar; // Ajoute cette ligne pour déclarer la Toolbar
    RequestQueue requestQueue;
    String insertUrl = "http://10.0.2.2:8082/api/v1/student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        bnAdd = findViewById(R.id.bnAdd);
        toolbar = findViewById(R.id.toolbar); // Initialise la Toolbar

        bnAdd.setOnClickListener(this);

        // Navigation Drawer code
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_item1:
                // Démarrer l'activité correspondante pour l'item 1
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.nav_item2:
                // Démarrer l'activité correspondante pour l'item 2
                startActivity(new Intent(this, MainActivity2.class));
                break;

            case R.id.nav_item3:
                // Démarrer l'activité correspondante pour l'item 2
                startActivity(new Intent(this, MainActivity3.class));
                break;

            case R.id.nav_item4:
                // Démarrer l'activité correspondante pour l'item 2
                startActivity(new Intent(this, MainActivity4.class));
                break;

            case R.id.nav_item5:
                // Démarrer l'activité correspondante pour l'item 2
                startActivity(new Intent(this, ListeEtudiantsActivity.class));
                break;

            default:
                // Ne rien faire par défaut
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        // Ton code de gestion de clic pour ajouter un étudiant
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", name.getText().toString());
            jsonBody.put("email", email.getText().toString());
            jsonBody.put("phone", phone.getText().toString());

            // Récupère la filière sélectionnée dans le Spinner
            Spinner spinnerFiliere = findViewById(R.id.spinnerFiliere);
            String selectedFiliere = spinnerFiliere.getSelectedItem().toString();

            jsonBody.put("filiere", selectedFiliere);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                insertUrl, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("resultat", response + "");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Erreur", error.toString());
            }
        });

        requestQueue.add(request);
    }


}
