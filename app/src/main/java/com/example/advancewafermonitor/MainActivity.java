package com.example.advancewafermonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.advancewafermonitor.pojo.Fruit;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Fruit[] fruits=new Fruit[]{
        new Fruit("apple",R.drawable.apple),
        new Fruit("banana",R.drawable.banana),
        new Fruit("cherry",R.drawable.cherry),
        new Fruit("lemon",R.drawable.lemon),
        new Fruit("pear",R.drawable.pear),
        new Fruit("mango",R.drawable.mango),
        new Fruit("pineapple",R.drawable.pienapple),
        new Fruit("strawberry",R.drawable.strawberry),
        new Fruit("watermelon",R.drawable.watermalon),
    };

    private List<Fruit> fruitList=new LinkedList<>();
    private FruitAdapter fruitAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();
        if (null!=actionBar) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,"Data restored",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
        initFruits();
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        GridLayoutManager manager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);
        fruitAdapter =new FruitAdapter(fruitList);
        recyclerView.setAdapter(fruitAdapter);

        swipeRefreshLayout=findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });

    }
    public void initFruits(){
        fruitList.clear();
        for (int i = 0; i < 21; i++) {
            Random random=new Random();
            int index=random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
               break;
           case R.id.backup:
               Toast.makeText(this,"backup",Toast.LENGTH_SHORT).show();
               break;
           case R.id.delete:
               Toast.makeText(this,"delete",Toast.LENGTH_SHORT).show();
               break;
           case R.id.setting:
               Toast.makeText(this,"setting",Toast.LENGTH_SHORT).show();
               break;
           default:
       }
       return true;
    }

    public void refreshFruits(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        fruitAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
