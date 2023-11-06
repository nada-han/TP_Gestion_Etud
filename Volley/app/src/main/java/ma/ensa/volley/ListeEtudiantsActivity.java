package ma.ensa.volley;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListeEtudiantsActivity extends AppCompatActivity {

    private List<Student> studentsList = new ArrayList<>();
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_etudiants);

        // Ajoutez le code pour le bouton "Go Back"
//        Button buttonBack = findViewById(R.id.buttonBack);
//        buttonBack.setOnClickListener(v -> {
//            // Code pour revenir en arrière ici
//            finish();
//        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentAdapter = new StudentAdapter(studentsList);
        recyclerView.setAdapter(studentAdapter);

        // Appelle la méthode pour récupérer tous les étudiants
        fetchAllStudents();


    }

    private void fetchAllStudents() {
        String apiUrl = "http://10.0.2.2:8082/api/v1/student";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseStudents(response);
                        studentAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListeEtudiantsActivity.this, "Erreur de récupération des étudiants", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    private void parseStudents(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject studentObject = jsonArray.getJSONObject(i);
                String name = studentObject.getString("name");
                String email = studentObject.getString("email");
                String phone = studentObject.getString("phone");

                Student student = new Student(name, email, phone);
                studentsList.add(student);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
