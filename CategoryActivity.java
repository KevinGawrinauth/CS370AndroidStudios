package edu.qc.seclass.storesupplyapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        int store = getIntent().getExtras().getInt("store");

        CardView woodCardView = findViewById(R.id.CardViewLaminate);
        woodCardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, SearchActivity.class);
                intent.putExtra("store",store);
                intent.putExtra("floor", "Laminate");
                startActivity(intent);
            }
        });
        CardView TileCardView = findViewById(R.id.CardViewTile);
        TileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, SearchActivity.class);
                intent.putExtra("store",store);
                intent.putExtra("floor", "Tile");
                startActivity(intent);
            }
        });
        CardView StoneCardView = findViewById(R.id.CardViewStone);
        StoneCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, SearchActivity.class);
                intent.putExtra("store",store);
                intent.putExtra("floor", "Stone");
                startActivity(intent);
            }
        });
        CardView VinylCardView = findViewById(R.id.CardViewVinyl);
        VinylCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, SearchActivity.class);
                intent.putExtra("store",store);
                intent.putExtra("floor", "Vinyl");
                startActivity(intent);
            }});
        CardView WoodCardView = findViewById(R.id.CardViewWood);
        WoodCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, SearchActivity.class);
                intent.putExtra("store",store);
                intent.putExtra("floor", "Wood");
                startActivity(intent);
            }});

        Button backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the Back method here
                Intent intent = new Intent(CategoryActivity.this, StoreActivity.class);
                startActivity(intent);
            }
        });

    }
}