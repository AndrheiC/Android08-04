package br.com.smarttech.acs.logitica;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText editNome;
    private Spinner spinnerPerecivel;
    private EditText editQtdEstoque;
    private EditText editPrice;
    private Spinner spinnerUnidadeMedida;
    private ListView listaProdutos;
    private FirebaseFirestore db;
    private List<String> produtos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        mAuth = FirebaseAuth.getInstance();
        editNome = findViewById(R.id.editNome);
        editPrice = findViewById(R.id.editPreco);
        spinnerUnidadeMedida = findViewById(R.id.spinnerUnidadeMedida);
        spinnerPerecivel = findViewById(R.id.spinnerPerecivel);
        editQtdEstoque = findViewById(R.id.editQtdEstoque);
        listaProdutos = findViewById(R.id.listProduto);
        produtos = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        recuperarDados();
    }

    public void recuperarDados(){
        db.collection("Produtos").orderBy("Price")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            produtos.clear();
                            listaProdutos.setAdapter(null);
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Map<String,Object> objeto = document.getData();
                                //for(Map<String,Object> dados : obj)
                                Produto prod = new Produto();
                                prod.setNome(objeto.get("Nome").toString());
                                prod.setQtdEstoque(objeto.get("QtdEstoque").toString());
                                prod.setValorUnitario(objeto.get("ValorUnitario").toString());
                                prod.setUnidadeMedida(objeto.get("UnidadeMedida").toString());
                                prod.setPerecivel(objeto.get("Perecivel").toString());
                                produtos.add(prod.toString());

                            }
                            ArrayAdapter myAdapter = new ArrayAdapter<String>(DashActivity.this,android.R.layout.simple_list_item_1,produtos);
                            listaProdutos.setAdapter(myAdapter);
                        }else{
                            Toast.makeText(DashActivity.this, "Não foi possivel recuperar os dados.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void salvarFirebase(View view) {
        Map<String,Object> produto = new HashMap<>();
        produto.put("Nome",editNome.getText().toString());
        produto.put("QtdEstoque",editQtdEstoque.getText().toString());
        produto.put("ValorUnitario",editPrice.getText().toString());
        produto.put("UnidadeMedida",spinnerUnidadeMedida.getSelectedItem().toString());
        produto.put("Perecivel",spinnerPerecivel.getSelectedItem().toString());


        db.collection("Produtos")
                .add(produto)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(DashActivity.this, "Produto " + documentReference.getId() + " Adicionado.", Toast.LENGTH_SHORT).show();
                        recuperarDados();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DashActivity.this, "Produto não adicionado.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void logout(View view) {
        mAuth.signOut();
        mainRedirect();
    }

    void mainRedirect(){
        Intent novaTela = new Intent(DashActivity.this,MainActivity.class);
        startActivity(novaTela);
    }
}
