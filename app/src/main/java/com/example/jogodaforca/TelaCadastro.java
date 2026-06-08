package com.example.jogodaforca;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaCadastro extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private EditText palavraDigitada, dicaDigitada;
    private Button btnCadastrar, btnListar, btnExcluir;
    private RadioGroup grupo;
    private String categoriaSelecionada, palavra;
    private Bd bd;

    private int nivelDificuldade;
    @SuppressLint("MissingInflatedId")
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
        dicaDigitada = findViewById(R.id.textDica);

        //referenciando ao layout e já tornando sensivel ao toque
        btnCadastrar = findViewById(R.id.button2);
        btnCadastrar.setOnClickListener(this);

        btnExcluir = findViewById(R.id.button5);
        btnExcluir.setOnClickListener(this);

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

            if (temTextoDigitado && temRadioChecado) {
                Palavra palavra1 = new Palavra();
                palavra1.setPalavraDigitada(texto);

                palavra1.setDica(dicaDigitada.getText().toString());

                // 1. Calcula o nível passando a string digitada
                int nivel = calcularNivel(texto);

                palavra1.setNivel(nivel);

                // Se a sua classe Palavra também salvar a categoria, não esqueça de setar ela aqui!
                // palavra1.setCategoria(categoriaSelecionada);

                bd.salvarPalavra(palavra1);

                palavraDigitada.setText("");

                // Um detalhe: faltou o .show() no seu código original para o Toast aparecer!
                Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
            }
        }
        if(v == btnListar){
            startActivity(new Intent(this, TelaRecycler.class));
        }

        /*if(v == btnExcluir){
            new AlertDialog.Builder(this)
                    .setTitle("Atenção!")
                    .setMessage("Tem certeza que deseja apagar todas as palavras cadastradas? Isso não pode ser desfeito.")
                    .setPositiveButton("Sim, apagar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Executa a limpeza se ele confirmar
                            bd.limparTodasAsPalavras();
                            listaPalavras.clear();
                            iniciarJogo();
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }*/
    }

    public int calcularNivel(String palavraFornecida) {
        int tamanho = palavraFornecida.length();

        if (tamanho > 0 && tamanho <= 4) {
            return 1; // Fácil (Até 4 letras)
        } else if (tamanho >= 5 && tamanho <= 7) {
            return 2; // Médio (5 a 7 letras)
        } else {
            return 3; // Difícil (8 ou mais letras)
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