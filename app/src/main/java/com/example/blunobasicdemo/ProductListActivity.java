package com.example.blunobasicdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductListActivity extends BlunoLibrary {

    ArrayList<Product> prodList;
    ListView lvMain;
    Button btnScan;
    boolean transmitOnce = false;

    @Override
    public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
        switch (theConnectionState) {											//Four connection state
            case isConnected:
                btnScan.setText("Connected");
                break;
            case isConnecting:
                btnScan.setText("Connecting");
                break;
            case isToScan:
                btnScan.setText("Scan");
                break;
            case isScanning:
                btnScan.setText("Scanning");
                break;
            case isDisconnecting:
                btnScan.setText("isDisconnecting");
                break;
            default:
                break;
        }
    }

    @Override
    public void onSerialReceived(String idTag) {
        Log.d("onMessageReceive", idTag);
        idTag = idTag.replaceAll("(\\r|\\n)", "");
            switch (idTag) {
                case "310719104":
                    prodList.add(new Product("Safeguard", 200.0f, "Bathing Soap", R.drawable.ic_launcher));
                    break;
                case "1000891694":
                    prodList.add(new Product("Minola", 160.0f, "Cooking Oil", R.drawable.ic_launcher));
                    break;
                case "3378924846":
                    prodList.add(new Product("Clear", 100, "Anti-Dundruff Shampoo", R.drawable.ic_launcher));
                    break;
            }
            refreshList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_list);
        onCreateProcess();
        serialBegin(115200);
        btnScan = (Button) findViewById(R.id.connect);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonScanOnClickProcess();
            }
        });
        prodList = new ArrayList<Product>();
        //populate data
        //prodList.add(new Product(1232,"Human Heart Shampoo",200.0f,"Organic Shampoo", R.drawable.ic_launcher));
        //prodList.add(new Product(12312,"JollyCow",160.0f,"Milk", R.drawable.ic_launcher));
        //prodList.add(new Product(1232132,"Eggs",100,"", R.drawable.ic_launcher));

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

    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();														//onPause Process by BlunoLibrary
    }

    protected void onStop() {
        super.onStop();
        onStopProcess();														//onStop Process by BlunoLibrary
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }

    protected void onResume(){
        super.onResume();
        System.out.println("BlUNOActivity onResume");
        onResumeProcess();														//onResume Process by BlunoLibrary
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary
        super.onActivityResult(requestCode, resultCode, data);
    }
}