package br.com.alura.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final int CODE_CAMERA = 123;
    private FormularioHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Aluno aluno = (Aluno)  intent.getSerializableExtra("aluno");
        if (aluno !=null) {
            helper.preencheFormulario(aluno);
        }

        Button botaoCamera = (Button) findViewById(R.id.formulario_botao);

        botaoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                vaiParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(caminhoFoto)));
                startActivityForResult(vaiParaCamera, CODE_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Activity.RESULT_OK) {
            if (resultCode == CODE_CAMERA) {

                ImageView foto = (ImageView) findViewById(R.id.foto_aluno_form);
                Bitmap bm = BitmapFactory.decodeFile(caminhoFoto);
                Bitmap novobm = Bitmap.createScaledBitmap(bm, 300, 300, true);
                foto.setImageBitmap(novobm);
                foto.setScaleType(ImageView.ScaleType.FIT_XY);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch(item.getItemId()){
           case R.id.menu_formulario_ok:
               Aluno aluno = helper.pegaAluno();
               AlunoDAO dao = new AlunoDAO(this);
               if (aluno.getId() != 0)  {
                   dao.altera(aluno);
               } else {
                   dao.insere(aluno);
               }
               dao.close();
               Toast.makeText(FormularioActivity.this, "Aluno "+ aluno.getNome() +" salvo!", Toast.LENGTH_SHORT).show();
               finish();
               break;
       }
        return super.onOptionsItemSelected(item);

    }
}
