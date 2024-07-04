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


public class DisplayPageActivity extends AppCompatActivity {

    Button btn;
    Button btn2;
    DatabaseHelper db;
    Button btn3;
    private Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaypage);
     db = new DatabaseHelper(getApplicationContext(),"StoreSupply",null,1);

        product= Helper.currentProduct;
        TextView material = (TextView) findViewById(R.id.nameMaterial);
        TextView species = (TextView) findViewById(R.id.nameSpecies);
        TextView waterProof = (TextView) findViewById(R.id.isWaterProof);

        EditText productName = (EditText) findViewById(R.id.textName);
        EditText productType = (EditText) findViewById(R.id.textType);
        EditText productStock = (EditText) findViewById(R.id.textStock);
        EditText productMaterial = (EditText) findViewById(R.id.textMaterial);
        EditText productSpecies = (EditText) findViewById(R.id.textSpecies);
        EditText productColor = (EditText) findViewById(R.id.textColor);
        EditText productSize = (EditText) findViewById(R.id.textSize);
        EditText productBrand = (EditText) findViewById(R.id.textBrand);
        EditText productPrice = (EditText) findViewById(R.id.textPrice);
        EditText resistWater = (EditText) findViewById(R.id.textProof);

        btn = findViewById(R.id.updateList);
        btn2 = findViewById(R.id.deleteList);
        btn3 = findViewById(R.id.backList);

        int store = getIntent().getExtras().getInt("store");
        String floor = getIntent().getStringExtra("floor");
        int pos = getIntent().getExtras().getInt("pos");


        productSpecies.setText(""+product.getSpecies());
        resistWater.setText(""+product.isWaterProof());

        productName.setText(""+product.getName());
        productType.setText(floor);
        productStock.setText(""+product.getStock());
        productMaterial.setText(""+product.getMaterial());

        productColor.setText(""+product.getColor());
        productSize.setText(""+product.getSize());
        productBrand.setText(""+product.getBrand());
        productPrice.setText(""+product.getPrice());



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update

                if (productName.getText().toString().equals(""))
                    Toast.makeText(DisplayPageActivity.this, "please enter Product Name ", Toast.LENGTH_SHORT).show();

                else    if (productStock.getText().toString().equals(""))
                    Toast.makeText(DisplayPageActivity.this, "please enter Product Stock ", Toast.LENGTH_SHORT).show();
                else    if (productSpecies.getText().toString().equals(""))
                    Toast.makeText(DisplayPageActivity.this, "please enter Product Species ", Toast.LENGTH_SHORT).show();

                else    if (productColor.getText().toString().equals(""))
                    Toast.makeText(DisplayPageActivity.this, "please enter Product Color ", Toast.LENGTH_SHORT).show();

                else    if (productSize.getText().toString().equals(""))
                    Toast.makeText(DisplayPageActivity.this, "please enter Product Size ", Toast.LENGTH_SHORT).show();

                else    if (productBrand.getText().toString().equals(""))
                    Toast.makeText(DisplayPageActivity.this, "please enter Product Brand ", Toast.LENGTH_SHORT).show();

                else    if (productPrice.getText().toString().equals(""))
                    Toast.makeText(DisplayPageActivity.this, "please enter Product Price ", Toast.LENGTH_SHORT).show();


                else
                {

                    ///update



                    Helper.currentList.get(pos).setBrand(productBrand.getText().toString());
                    Helper.currentList.get(pos).setColor(productColor.getText().toString());
                    Helper.currentList.get(pos).setName(productName.getText().toString());
                    Helper.currentList.get(pos).setMaterial(productMaterial.getText().toString());
                    Helper.currentList.get(pos).setSize(productSize.getText().toString());
                    Helper.currentList.get(pos).setStock(Integer.parseInt(productStock.getText().toString()));
                    Helper.currentList.get(pos).setPrice(Double.parseDouble(productPrice.getText().toString()));
                    Helper.currentList.get(pos).setSpecies(productSpecies.getText().toString());

                    Gson gson = new Gson();

                    String inputString= gson.toJson(Helper.currentList);
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
                        Toast.makeText(DisplayPageActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DisplayPageActivity.this, SearchActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("store", store);
                        intent.putExtra("floor",floor);
                        startActivity(intent);

                        finish();

                    }
                    else
                        Toast.makeText(DisplayPageActivity.this, "Failed Updated", Toast.LENGTH_SHORT).show();

                }




            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Delete

                Helper.currentList.remove(pos);
                Helper.currentList.remove(product);

                Gson gson = new Gson();

                String inputString= gson.toJson(Helper.currentList);
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
                    Toast.makeText(DisplayPageActivity.this, "Removed Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DisplayPageActivity.this, SearchActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("store", store);
                    intent.putExtra("floor",floor);
                    startActivity(intent);

                    finish();

                }
                else
                    Toast.makeText(DisplayPageActivity.this, "Failed Removed", Toast.LENGTH_SHORT).show();



            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Back
                Intent intent = new Intent(DisplayPageActivity.this, SearchActivity.class);
                intent.putExtra("store", store);
                intent.putExtra("floor",floor);
                startActivity(intent);
            }
        });
    }

}



