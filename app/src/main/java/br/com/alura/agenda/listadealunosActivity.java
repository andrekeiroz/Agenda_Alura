package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class listadealunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadealunos);

        String[] alunos= {"Daniel","Ronaldo", "Jeferson", "Felipe","Ronaldo", "Jeferson", "Felipe","Ronaldo", "Jeferson", "Felipe","Ronaldo", "Jeferson", "Felipe","Ronaldo", "Jeferson", "Felipe"};
        ListView ListaAlunos = (ListView) findViewById(R.id.lista_alunos);
        ArrayAdapter<String> adapter = new ArrayAdapter <String> (this, android.R.layout.simple_list_item_1, alunos);
        ListaAlunos.setAdapter(adapter);

        Button novoAluno = (Button) findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToForm = new Intent(listadealunosActivity.this, FormularioActivity.class);
                startActivity(goToForm);
            }
        });
    }
}
