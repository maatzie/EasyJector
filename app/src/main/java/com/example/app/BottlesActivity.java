package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app.adapters.BottlesRecyclerViewAdapter;
import com.example.app.database.pojo.Bottle;
import com.example.app.database.sqlite.BottleTableHandler;

import java.util.List;

public class BottlesActivity extends AppCompatActivity {

    private RecyclerView bottlesRecyclerView;
    private BottleTableHandler bottleTableHandler;
    public Integer editBottleID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottles);


        bottleTableHandler = new BottleTableHandler(getBaseContext());
        List<Bottle> bottles = bottleTableHandler.getBottles();
        initRecyclerView(bottles);
        initAddButtonClick();
        initCancelButtonClick();
    }

    public void initRecyclerView(List<Bottle> bottles){
        bottlesRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_bottles);
        bottlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BottlesRecyclerViewAdapter adapter = new BottlesRecyclerViewAdapter(bottles, this);
        bottlesRecyclerView.setAdapter(adapter);
        bottlesRecyclerView.setNestedScrollingEnabled(false);

    }

    private void initAddButtonClick(){
        Button button = (Button) findViewById(R.id.button_addBottle);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean isError = false;
                //Bottle ID handler
                EditText editText_bottleID = (EditText) findViewById(R.id.editText_bottleID);
                String bottleID = editText_bottleID.getText().toString();
                if(bottleID.equals("")){
                    editText_bottleID.setError("You need to enter a Bottle ID!");
                    isError = true;
                }
                //Bottle Name handler
                EditText editText_bottleName = (EditText) findViewById(R.id.editText_bottleName);
                String bottleName = editText_bottleName.getText().toString();
                if(bottleName.equals("")){
                    editText_bottleName.setError("You need to enter a Bottle Name!");
                    isError = true;
                }
                //Volume handler
                EditText editText_bottleVolume = (EditText) findViewById(R.id.editText_bottleVolume);
                String volume = editText_bottleVolume.getText().toString();
                if(volume.equals("")){
                    editText_bottleVolume.setError("You need to enter a Volume!");
                    isError = true;
                }
                else if(!volume.matches("\\d+")){
                    editText_bottleVolume.setError("Volume must be a number!");
                    isError = true;
                }
                //Quantity handler
                EditText editText_BottleQuantity = (EditText) findViewById(R.id.editText_BottleQuantity);
                String quantity = editText_BottleQuantity.getText().toString();
                if(quantity.equals("")){
                    editText_BottleQuantity.setError("You need to enter a Quantity!");
                    isError = true;
                }
                else if(!quantity.matches("-?\\d+")){
                    editText_BottleQuantity.setError("Quantity must be a number!");
                    isError = true;
                }

                if(!isError){
                    if(editBottleID == null){
                        int exit = bottleTableHandler.addBottle(bottleID, bottleName, Integer.parseInt(volume), Integer.parseInt(quantity));
                        if(exit == 1) //success
                            Toast.makeText(getApplicationContext(),"Bottle has been added!", Toast.LENGTH_SHORT).show();
                        else if(exit == 0) //error
                            Toast.makeText(getApplicationContext(),"Error occurred while adding a bottle!", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        int exit = bottleTableHandler.updateBottle(editBottleID, bottleID, bottleName, Integer.parseInt(volume), Integer.parseInt(quantity));
                        editBottleID = null;
                        Button cancelButton = (Button) findViewById(R.id.button_cancelEditingBottle);
                        cancelButton.setVisibility(View.GONE);
                        if(exit == 1) //success
                            Toast.makeText(getApplicationContext(),"Bottle has been edited!", Toast.LENGTH_SHORT).show();
                        else if(exit == 0) //error
                            Toast.makeText(getApplicationContext(),"Error occurred while editing a bottle!", Toast.LENGTH_SHORT).show();
                    }
                    // refresh patients list
                    initRecyclerView(bottleTableHandler.getBottles());
                    // clear all inputs
                    editText_bottleID.getText().clear();
                    editText_bottleName.getText().clear();
                    editText_bottleVolume.getText().clear();
                    editText_BottleQuantity.getText().clear();

                }
            }
        });
    }

    public void initCancelButtonClick() {
        Button cancelButton = (Button) findViewById(R.id.button_cancelEditingBottle);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText_bottleID = (EditText) findViewById(R.id.editText_bottleID);
                EditText editText_bottleName = (EditText) findViewById(R.id.editText_bottleName);
                EditText editText_bottleVolume = (EditText) findViewById(R.id.editText_bottleVolume);
                EditText editText_BottleQuantity = (EditText) findViewById(R.id.editText_BottleQuantity);

                // clear all inputs
                editText_bottleID.getText().clear();
                editText_bottleName.getText().clear();
                editText_bottleVolume.getText().clear();
                editText_BottleQuantity.getText().clear();

                editBottleID = null;

                Button cancelButton = (Button) findViewById(R.id.button_cancelEditingBottle);
                cancelButton.setVisibility(View.GONE);

            }
        });
    }
}