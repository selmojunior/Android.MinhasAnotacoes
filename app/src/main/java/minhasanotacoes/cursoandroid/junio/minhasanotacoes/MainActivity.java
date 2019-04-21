package minhasanotacoes.cursoandroid.junio.minhasanotacoes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText texto;
    private Button botaoSalvar;

    private static final String NOME_ARQUIVO = "arquivo_anotacoes.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (EditText) findViewById(R.id.textoId);
        botaoSalvar = (Button) findViewById(R.id.botaoSalvarId);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoDigitado = texto.getText().toString();
                gravarNoArquivo(textoDigitado);
                Toast.makeText(getApplicationContext(), "Anotação salva com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        //Recuperando o que foi gravado
        if (lerArquivo() != null) {
            texto.setText(lerArquivo());
        }

    }

    private void gravarNoArquivo(String texto) {
        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(NOME_ARQUIVO, Context.MODE_PRIVATE));
            outputStreamWriter.write(texto);
            outputStreamWriter.close();

        } catch (IOException e) {
            Log.v("MainActivity", e.toString());
        }
    }

    private String lerArquivo() {
        String resultado = "";
        try {
            //abrir o arquivo
            InputStream arquivo = openFileInput(NOME_ARQUIVO);

            if (arquivo != null) {

                //ler arquivo
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);

                //gerando um buffer do arquivo
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //recuperar texto do arquivo
                String linhaArquivo = "";
                while (( linhaArquivo = bufferedReader.readLine() ) != null) {
                    resultado += linhaArquivo;
                }

                //fechar arquivo
                arquivo.close();
            }

        } catch (IOException e) {
            Log.v("MainActivity", e.toString());
        }
        return resultado;
    }

}
