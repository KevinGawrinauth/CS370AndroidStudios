package edu.qc.seclass.storesupplyapplication;
import static edu.qc.seclass.storesupplyapplication.utils.Helper.currentProduct;

import android.content.Intent;
import android.opengl.GLDebugHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

import edu.qc.seclass.storesupplyapplication.Database.DatabaseHelper;
import edu.qc.seclass.storesupplyapplication.adapter.ItemAdapter;
import edu.qc.seclass.storesupplyapplication.model.Item;
import edu.qc.seclass.storesupplyapplication.model.Product;
import edu.qc.seclass.storesupplyapplication.model.Store;
import edu.qc.seclass.storesupplyapplication.utils.Helper;

public class SearchActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {
    private ArrayList<Product> itemList=new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ItemAdapter itemAdapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchrecycler);
        db = new DatabaseHelper(getApplicationContext(),"StoreSupply",null,1);

        String floor = getIntent().getStringExtra("floor");
        int store = getIntent().getExtras().getInt("store");

        searchView = findViewById(R.id.searchview1);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        recyclerView = findViewById(R.id.RecyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


       itemList = new ArrayList<>();


        getProductFromStore(store,floor);





        //filterList(""); // Display the initial list of items

        // Add a button to the layout
        Button addButton = findViewById(R.id.btnAdd);
        Button backButton = findViewById(R.id.btnBack);
        // Set a click listener on the button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the btnAdd method here
                Intent intent = new Intent(SearchActivity.this, AddActivity.class);
                String floor = getIntent().getStringExtra("floor");
                int store = getIntent().getExtras().getInt("store");

                intent.putExtra("floor", floor);
                intent.putExtra("store", store);
                Helper.currentList= itemList;
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the Back method here
                Intent intent = new Intent(SearchActivity.this, CategoryActivity.class);
                String store = getIntent().getStringExtra("store");
                intent.putExtra("store", store);
                startActivity(intent);
            }
        });


    }

    private void getProductFromStore(int store, String floor)
    {
       Store storeModel = db.getStore(store,floor);

       if (floor.equals("Laminate"))
       {

           itemList.addAll( storeModel.getLaminate());
           if (itemList.size()==0)
           {
               itemList.add(new Product(0,"Ceramic Laminate"));
               itemList.add(new Product(1,"Glass Laminate"));
               itemList.add(new Product(2,"Glossy Laminate"));
               itemList.add(new Product(3,"Hard Laminate"));
               itemList.add(new Product(4,"Flat Laminate"));
           }
           Helper.currentCategory=DatabaseHelper.CATEGORY_laminate;
           Helper.laminateList=itemList;
       }


           else if (floor.equals("Stone"))
        {
            itemList.addAll(storeModel.getStone());
            if (itemList.size()==0)
            {
                itemList.add(new Product(0,"Granite"));
                itemList.add(new Product(1,"Marble"));
                itemList.add(new Product(2,"Limestone"));
                itemList.add(new Product(3,"Slate"));
            }
            Helper.currentCategory=DatabaseHelper.CATEGORY_Stone;

            Helper.stoneList=itemList;
        }

       else if (floor.equals("Tile"))
        {
            if (itemList.size()==0)
            {
                itemList.add(new Product(0,"Ceramic Tile"));
                itemList.add(new Product(1,"Glass Tile"));
                itemList.add(new Product(2,"Porcelain Tile"));
                itemList.add(new Product(3,"Stone Tile"));
                itemList.add(new Product(4,"Mosaic Tile"));
            }


            itemList.addAll(storeModel.getTile());
            Helper.currentCategory=DatabaseHelper.CATEGORY_Tile;

            Helper.tileList=itemList;
        }


       else if (floor.equals("Wood"))
       {


           Helper.currentCategory=DatabaseHelper.CATEGORY_Wood;
           itemList .addAll( storeModel.getWood());

           if (itemList.size()==0)
           {
               itemList.add(new Product(0,"Birch Wood"));
               itemList.add(new Product(1,"Cedar Wood"));
               itemList.add(new Product(2,"Mahogany Wood"));
               itemList.add(new Product(3,"Pine Wood"));
               itemList.add(new Product(4,"Oak Wood"));
           }
           Helper.woodList=itemList;
       }


       else if (floor.equals("Vinyl"))
       {

           Helper.currentCategory=DatabaseHelper.CATEGORY_Vinyl;
           itemList.addAll( storeModel.getVinyl());
           if (itemList.size()==0)
           {
               itemList.add(new Product(0,"White Vinyl "));
               itemList.add(new Product(1,"Black Plank "));
               itemList.add(new Product(2,"Gold Vinyl"));

           }
           Helper.vinylList=itemList;
       }
        Helper.currentList= itemList;

        itemAdapter = new ItemAdapter(itemList, this);
        itemAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(itemAdapter);
    }

    private void filterList(String text) {
        List<Product> filteredList = new ArrayList<>();
        if (itemList!=null)
        {
            for (Product item : itemList) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            }
            itemAdapter.setFilteredList(filteredList);
            itemAdapter.notifyDataSetChanged();
        }

    }



    @Override
    public void onItemClick(Product item,int pos) {
        String floor = getIntent().getStringExtra("floor");
        int store = getIntent().getExtras().getInt("store");
        currentProduct=item;

        Intent intent = new Intent(SearchActivity.this, DisplayPageActivity.class);
        intent.putExtra("floor", floor);
        intent.putExtra("store", store);
        intent.putExtra("pos", pos);
        startActivity(intent);
    }
}