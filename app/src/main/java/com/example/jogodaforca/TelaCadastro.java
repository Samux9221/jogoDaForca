package com.example.jogodaforca;

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

    }

    @Override
    public void onClick(View v) {

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