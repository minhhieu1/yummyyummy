package com.minhhieu.yummyyummy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minhhieu.yummyyummy.Interface.ItemClickListener;
import com.minhhieu.yummyyummy.ViewHolder.MenuViewHolder;
import com.minhhieu.yummyyummy.model.Catelogy;
import com.minhhieu.yummyyummy.model.Common;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database;
    DatabaseReference catelogy;
    TextView txtTen;

    RecyclerView recyclerMenu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Catelogy, MenuViewHolder> adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        database=FirebaseDatabase.getInstance();
        catelogy=database.getReference("Catelogy");




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView=navigationView.getHeaderView(0);
        txtTen=headerView.findViewById(R.id.txtTen);
        txtTen.setText(Common.currentUser.getName());

        //load menu
        recyclerMenu=findViewById(R.id.recycleMenu);
        recyclerMenu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerMenu.setLayoutManager(layoutManager);




    }

    @Override
    protected void onStart() {
        super.onStart();
        loadMenu();
        if (adapter != null)  {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null)  {
            adapter.stopListening();
        }
    }

    private void loadMenu() {
        FirebaseRecyclerOptions<Catelogy> options =
                new FirebaseRecyclerOptions.Builder<Catelogy>()
                        .setQuery(catelogy, Catelogy.class)
                        .build();
        adapter=new FirebaseRecyclerAdapter<Catelogy, MenuViewHolder>(options) {

            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_menu, viewGroup, false);

                return new MenuViewHolder(view);

            }
            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull Catelogy model) {
                holder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(holder.imageView);
                final Catelogy clickItem=model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent=new Intent(HomeActivity.this, FoodListActivity.class);
                        intent.putExtra("CatelogyID", adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });



            }
        };
        recyclerMenu.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id=item.getItemId();
        if(id==R.id.nav_menu){
            Intent intent=new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intent);
        }else if(id==R.id.nav_cart){
            Intent intent=new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);

        }else if(id==R.id.nav_order){
            Intent intent=new Intent(HomeActivity.this, OrderStatusActivity.class);
            startActivity(intent);

        }else if(id==R.id.nav_log_out){
            Intent intent=new Intent(HomeActivity.this, Signin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
