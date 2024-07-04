package edu.qc.seclass.storesupplyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;

import edu.qc.seclass.storesupplyapplication.Database.DatabaseHelper;
import edu.qc.seclass.storesupplyapplication.model.Product;
import edu.qc.seclass.storesupplyapplication.utils.Helper;

public class AddActivity extends AppCompatActivity {


    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpage);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext(),"StoreSupply",null,1);


        TextView type = (TextView) findViewById((R.id.textType));
        TextView material = (TextView) findViewById(R.id.nameMaterial);
        TextView species = (TextView) findViewById(R.id.nameSpecies);
        TextView waterProof = (TextView) findViewById(R.id.isWaterProof);


        EditText productName = (EditText) findViewById(R.id.textName);

        EditText productStock = (EditText) findViewById(R.id.textStock);
        EditText productMaterial = (EditText) findViewById(R.id.textMaterial);
        EditText productSpecies = (EditText) findViewById(R.id.textSpecies);
        EditText productColor = (EditText) findViewById(R.id.textColor);
        EditText productSize = (EditText) findViewById(R.id.textSize);
        EditText productBrand = (EditText) findViewById(R.id.textBrand);
        EditText productPrice = (EditText) findViewById(R.id.textPrice);
        EditText resistWater = (EditText) findViewById(R.id.textProof);



        btn2 = findViewById(R.id.backList);


        int store = getIntent().getExtras().getInt("store");
        String floor = getIntent().getStringExtra("floor");
        String name = getIntent().getStringExtra("itemName");


//If statements per type needed
        if(floor.equals("Tile")){
            species.setHint("");
            waterProof.setHint("");

            productName.setHint(name);
            type.setHint(floor);
            productStock.setHint("__ in stock");
            productMaterial.setHint("Material");
            productSpecies.setHint("");
            productColor.setHint("color");
            productSize.setHint("Size");
            productBrand.setHint("Brand");
            productPrice.setHint("$ Price.00");
            resistWater.setHint("");


        }

        else if (floor.equals("Stone")) {
            species.setText("");
            waterProof.setText("");

            productName.setText(name);
            type.setText(floor);
            productStock.setHint("__ in stock");
            productMaterial.setHint("Material");
            productSpecies.setHint("");
            productColor.setHint("color");
            productSize.setHint("Size");
            productBrand.setHint("Brand");
            productPrice.setHint("$ Price.00");
            resistWater.setHint("");



        }

        else if(floor.equals("Wood")){
            material.setHint("");
            waterProof.setHint("");

            productName.setHint(name);
            type.setText(floor);
            productStock.setHint("__ in stock");
            productMaterial.setHint("");
            productSpecies.setHint("Oak");
            productColor.setHint("color");
            productSize.setHint("Size");
            productBrand.setHint("Brand");
            productPrice.setHint("$ Price.00");
            resistWater.setHint("");



        }

        else if(floor.equals("Laminate")){
            species.setHint("");

            productName.setHint(name);
            type.setText(floor);
            productStock.setHint("__ in stock");
            productMaterial.setHint("Marble");
            productSpecies.setHint("");
            productColor.setHint("color");
            productSize.setHint("Size");
            productBrand.setHint("Brand");
            productPrice.setHint("$ Price.00");
            resistWater.setHint("WP");
        }

        else if(floor.equals("Vinyl")){
            material.setHint("");
            species.setHint("");

            productName.setHint(name);
            type.setText(floor);
            productStock.setHint("__ in stock");
            productMaterial.setHint("");
            productSpecies.setHint("");
            productColor.setHint("color");
            productSize.setHint("Size");
            productBrand.setHint("Brand");
            productPrice.setHint("$ Price.00");
            resistWater.setHint("WR");


        }

        findViewById(R.id.addList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add to SQLite Database

                if (productName.getText().toString().equals(""))
                    Toast.makeText(AddActivity.this, "please enter Product Name ", Toast.LENGTH_SHORT).show();

                else    if (productStock.getText().toString().equals(""))
                    Toast.makeText(AddActivity.this, "please enter Product Stock ", Toast.LENGTH_SHORT).show();
                else    if (productSpecies.getText().toString().equals(""))
                    Toast.makeText(AddActivity.this, "please enter Product Species ", Toast.LENGTH_SHORT).show();

                else    if (productColor.getText().toString().equals(""))
                    Toast.makeText(AddActivity.this, "please enter Product Color ", Toast.LENGTH_SHORT).show();

                else    if (productSize.getText().toString().equals(""))
                    Toast.makeText(AddActivity.this, "please enter Product Size ", Toast.LENGTH_SHORT).show();

                else    if (productBrand.getText().toString().equals(""))
                    Toast.makeText(AddActivity.this, "please enter Product Brand ", Toast.LENGTH_SHORT).show();

                else    if (productPrice.getText().toString().equals(""))
                    Toast.makeText(AddActivity.this, "please enter Product Price ", Toast.LENGTH_SHORT).show();


                else
                {
                    Product product = new Product(Helper.currentList.size(),productName.getText().toString(),store,
                            Integer.parseInt(productStock.getText().toString()),productSpecies.getText().toString(),productMaterial.getText().toString(),
                            productColor.getText().toString(),productSize.getText().toString(),productBrand.getText().toString(),Double.parseDouble(productPrice.getText().toString()),false);


                    Helper.currentList.add(product);
                    Gson gson = new Gson();

                    String inputString= gson.toJson( Helper.currentList);
                    String wood= gson.toJson(Helper.woodList);
                    String vinyl= gson.toJson(Helper.vinylList);
                    String stone= gson.toJson(Helper.stoneList);
                    String tile= gson.toJson(Helper.tileList);
                    String laminate= gson.toJson(Helper.laminateList);

               boolean result= db.AddProduct(inputString,store,Helper.currentCategory,

               wood,vinyl,stone,tile,laminate
               );

               if (result)
               {
                   Toast.makeText(AddActivity.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(AddActivity.this, SearchActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent.putExtra("store", store);
                   intent.putExtra("floor",floor);
                   startActivity(intent);

                   finish();

               }
               else
                   Toast.makeText(AddActivity.this, "Failed Added", Toast.LENGTH_SHORT).show();

                }



            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Back
                Intent intent = new Intent(AddActivity.this, SearchActivity.class);
                intent.putExtra("store", store);
                intent.putExtra("floor",floor);
                startActivity(intent);


            }
        });


    }


}