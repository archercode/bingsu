package com.example.blunobasicdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductListActivity extends Activity {

    ArrayList<Product> prodList;
    ListView lvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_list);

        prodList = new ArrayList<Product>();
        //populate data
        prodList.add(new Product("Human Heart Shampoo",200.0f,"Organic Shampoo", R.drawable.ic_launcher));
        prodList.add(new Product("JollyCow",160.0f,"Milk", R.drawable.ic_launcher));
        prodList.add(new Product("Eggs",100,"", R.drawable.ic_launcher));

        lvMain = (ListView) findViewById(R.id.lv_main);
        refreshList();
    }

    public class ProductListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return prodList.size();
        }

        @Override
        public Object getItem(int position) {
            return prodList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();

            View view = inflater.inflate(R.layout.listitem_product, null);
            TextView tvName = (TextView) view.findViewById(R.id.tv_product);
            TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
            TextView tvDesc = (TextView) view.findViewById(R.id.tv_desc);

            tvName.setText(prodList.get(position).getName());
            tvPrice.setText("P " + String.format("%.2f", prodList.get(position).getPrice()));
            tvDesc.setText(prodList.get(position).getDescription());

            return view;
        }
    }

    private void refreshList(){
        lvMain.setAdapter(new ProductListAdapter());

    }


}