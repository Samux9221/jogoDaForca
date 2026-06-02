package com.example.jogodaforca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaCadastro extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private EditText palavraDigitada;
    private Button btnCadastrar, btnListar;
    private RadioGroup grupo;
    private String categoriaSelecionada, palavra;
    private Bd bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        palavraDigitada = findViewById(R.id.textPalavra);

        //referenciando ao layout e já tornando sensivel ao toque
        btnCadastrar = findViewById(R.id.button2);
        btnCadastrar.setOnClickListener(this);

        //referenciando ao layout e já tornando sensivel ao toque
        btnListar = findViewById(R.id.button3);
        btnListar.setOnClickListener(this);

        //referenciando ao layout e já tornando sensivel ao toque (nesse caso, tem uma metodo de escutador de evento propio)
        grupo = findViewById(R.id.radioGroup);
        grupo.setOnCheckedChangeListener(this);

        //Tela cadastro tem um construtor que espera um banco de dados, por isso o THIS
        bd = new Bd(TelaCadastro.this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnCadastrar){
            String texto = palavraDigitada.getText().toString();

            boolean temTextoDigitado = false;

            //se o texto estiver vazio, esse metodo cuida disso para nós
            if(texto.isEmpty()){
                //esse toast coloca um aviso temporario na tela do usuário
                Toast.makeText(this, "Faltou escrever uma palavra, leso", Toast.LENGTH_SHORT).show();
            } else{
                temTextoDigitado = true;
            }

            RadioButton r = findViewById(R.id.radioButton1);
            RadioButton r1 = findViewById(R.id.radioButton2);
            RadioButton r2 = findViewById(R.id.radioButton3);
            RadioButton r3 = findViewById(R.id.radioButton4);
            RadioButton r4 = findViewById(R.id.radioButton5);


            boolean temRadioChecado = true;

            //verificando se existe alguma categoria selecionada, lembrando que estamos dentro do IF do botão cadastrar
            if(r.isChecked() || r1.isChecked() || r2.isChecked() || r3.isChecked() || r4.isChecked()){
                temRadioChecado = true;
            } else{
                Toast.makeText(this, "Faltou marcar categoria, leso", Toast.LENGTH_SHORT).show();
            }

            if(temTextoDigitado && temRadioChecado){
                //aqui pode salvar no dp, já verficamos se a categoria e texto foram digitados ou selecionados
                Palavra palavra1 = new Palavra();
                palavra1.setPalavraDigitada(texto);
                bd.salvarPalavra(palavra1);
                palavraDigitada.setText("");
                Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT);
            }
        }
        if(v == btnListar){
            startActivity(new Intent(this, TelaRecycler.class));
        }
    }

    public void calcularNivel(EditText palavra){
        String palavraFornecida = palavra.getText().toString();

        while(int i = 0; i < palavraFornecida.length(); i++){

        }
    }

    @Override
    public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
        if(group == grupo){
            RadioButton temporario = findViewById(checkedId);//crio localmente um radio button sendo instanciado pelo id que está sendo recebido
            Toast.makeText(TelaCadastro.this, temporario.getText().toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}