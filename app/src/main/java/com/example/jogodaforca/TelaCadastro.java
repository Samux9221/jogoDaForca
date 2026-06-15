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

        btnCadastrar = findViewById(R.id.button2);
        btnCadastrar.setOnClickListener(this);

        btnExcluir = findViewById(R.id.button5);
        btnExcluir.setOnClickListener(this);

        btnListar = findViewById(R.id.button3);
        btnListar.setOnClickListener(this);

        grupo = findViewById(R.id.radioGroup);
        grupo.setOnCheckedChangeListener(this);

        bd = new Bd(TelaCadastro.this);
    }

    private void caixaConfirmacaoLimpeza() {
        new AlertDialog.Builder(this)
                .setTitle("Atenção!")
                .setMessage("Tem certeza que deseja apagar TODAS as palavras cadastradas? Essa ação não poderá ser desfeita.")
                .setPositiveButton("Sim, apagar tudo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bd.limparTodasAsPalavras();
                        Toast.makeText(TelaCadastro.this, "Banco de dados limpo com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onClick(View v) {
        if (v == btnCadastrar) {
            String texto = palavraDigitada.getText().toString().trim(); //esse .trim() evita que tenha espaço vazio sem caraceteres
            String dica = dicaDigitada.getText().toString().trim();

            // 1. Validação do texto da palavra
            if (texto.isEmpty()) {
                Toast.makeText(this, "Faltou escrever uma palavra, leso", Toast.LENGTH_SHORT).show();
                return; // Para a execução aqui e não tenta salvar
            }

            // 2. Validação da categoria (RadioGroup)
            int idSelecionado = grupo.getCheckedRadioButtonId(); // Pega o ID do rádio que está marcado
            if (idSelecionado == -1) { // -1 significa que nenhum rádio está selecionado
                Toast.makeText(this, "Faltou marcar categoria, leso", Toast.LENGTH_SHORT).show();
                return; // Para a execução aqui
            }

            // 3. Se passou pelas validações, descobre o texto do RadioButton selecionado
            RadioButton radioSelecionado = findViewById(idSelecionado);
            String categoriaSelecionada = radioSelecionado.getText().toString();

            // 4. Cria o objeto Palavra e popula com todos os dados corretos
            Palavra palavra1 = new Palavra();
            palavra1.setPalavraDigitada(texto);
            palavra1.setDica(dica);
            palavra1.setCategoria(categoriaSelecionada); // Agora salvando a categoria dinamicamente!

            // Calcula o nível automaticamente baseado no tamanho
            int nivel = calcularNivel(texto);
            palavra1.setNivel(nivel);


            // 5. Salva de fato no banco de dados (Apenas UMA vez)
            bd.salvarPalavra(palavra1);

            // 6. Limpa os campos da tela para o próximo cadastro
            palavraDigitada.setText("");
            dicaDigitada.setText("");
            grupo.clearCheck(); // Desmarca os RadioButtons

            Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
        }

        if (v == btnListar) {
            startActivity(new Intent(this, TelaRecycler.class));
        }

        if (v == btnExcluir) {
            caixaConfirmacaoLimpeza();
        }
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
        if (group == grupo && checkedId != -1) {
            RadioButton temporario = findViewById(checkedId);
            Toast.makeText(TelaCadastro.this, temporario.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}