package com.wireddevs.kanjilist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wireddevs.kanjilist.database.itemHelper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner levelspinner;
    private itemHelper ih;
    private boolean memorizemode;
    private GridView gridview;
    private TextAdapter ta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gridview = (GridView) findViewById(R.id.studiogrid);
        ta=new TextAdapter(this);
        gridview.setAdapter(ta);
        levelspinner=(Spinner)findViewById(R.id.selectlevel_spinner);
        levelspinner.setOnItemSelectedListener(this);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(memorizemode){
                    memorizemode=false;
                    Toast.makeText(MainActivity.this,"Normal mode",Toast.LENGTH_SHORT).show();
                }
                else{
                    memorizemode=true;
                    Toast.makeText(MainActivity.this,"Memorize mode, tap on characters that you already memorized",Toast.LENGTH_LONG).show();
                }
            }
        });

        ih=new itemHelper(this);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View v, final int position, long id){
                if(memorizemode){
                    ih.updateStatus(position+1,2);
                    ta.refreshStatus(MainActivity.this);
                    Toast.makeText(MainActivity.this, "Added to memorized list", Toast.LENGTH_SHORT).show();
                    ta.notifyDataSetChanged();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(TextAdapter.getTextMessage(position));
                    builder.setPositiveButton("Memorized", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ih.updateStatus(TextAdapter.positions.get(position)+1,2);
                            Toast.makeText(MainActivity.this, "Added to memorized list", Toast.LENGTH_SHORT).show();
                            ta.refreshStatus(MainActivity.this);
                            ta.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    });
                    builder.setNegativeButton("Partial", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ih.updateStatus(TextAdapter.positions.get(position)+1,1);
                            Toast.makeText(MainActivity.this, "Added to partially memorized list", Toast.LENGTH_SHORT).show();
                            ta.refreshStatus(MainActivity.this);
                            ta.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    });
                    builder.setNeutralButton("Unfamiliar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ih.updateStatus(TextAdapter.positions.get(position)+1,0);
                            Toast.makeText(MainActivity.this, "Added to unfamiliar list", Toast.LENGTH_SHORT).show();
                            ta.refreshStatus(MainActivity.this);
                            ta.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_stats) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setMessage("Partially memorized: "+ih.getCountByStatus(1)+"\n"+"\n"+"Fully memorized: "+ih.getCountByStatus(2));
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
            return true;
        }

        else if (id == R.id.action_contact) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setMessage("Feedback & Suggestion:"+"\n"+"madoka300900@gmail.com");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextAdapter.setGridView(parent.getSelectedItemPosition());
        ta.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onResume() {
        super.onResume();
        levelspinner.setSelection(0);
        ta.notifyDataSetChanged();
    }
}
