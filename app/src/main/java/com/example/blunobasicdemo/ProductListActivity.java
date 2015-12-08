package com.example.blunobasicdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductListActivity extends BlunoLibrary {

    ArrayList<Product> prodList;
    ListView lvMain;
    Button btnScan;
    boolean transmitOnce = false;
    TextView tvTotal;
    ProductListAdapter adapter;

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
                    prodList.add(new Product("Safeguard", 200.0f, "Bathing Soap", R.drawable.ic_launcher, 1));
                    break;
                case "1000891694":
                    prodList.add(new Product("Minola", 160.0f, "Cooking Oil", R.drawable.ic_launcher,1));
                    break;
                case "3378924846":
                    prodList.add(new Product("Clear", 100, "Anti-Dundruff Shampoo", R.drawable.ic_launcher,1));
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
        /*prodList.add(new Product("Safeguard", 200.0f, "Bathing Soap", R.drawable.ic_launcher, 1));
*/
        lvMain = (ListView) findViewById(R.id.lv_main);
        adapter = new ProductListAdapter(prodList);
        lvMain.setAdapter(adapter);
        tvTotal = (TextView) findViewById(R.id.tv_total);
        tvTotal.setText("P 0");
        refreshList();
    }


    float total;

    public class ProductListAdapter extends BaseAdapter {



        ArrayList<Product> prodList;
        public ProductListAdapter(
                ArrayList<Product> prodList){
            total = 0;
            this.prodList = prodList;
        }

        @Override
        public int getCount() {
            return prodList.size() ;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();

            View view = inflater.inflate(R.layout.listitem_product, null);

            TextView tvName = (TextView) view.findViewById(R.id.tv_product);
            TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
            TextView tvDesc = (TextView) view.findViewById(R.id.tv_desc);

            final EditText etQuantity = (EditText) view.findViewById(R.id.et_quantity);

            etQuantity.setText(""+prodList.get(position).getQuantity());
            tvName.setText(prodList.get(position).getName());
            tvPrice.setText("P " + String.format("%.2f", prodList.get(position).getPrice()));
            tvDesc.setText(prodList.get(position).getDescription());

            etQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().isEmpty()) {
                        prodList.get(position).setQuantity(Integer.parseInt(s.toString()));
                        refreshList();
                        hideKeyboard();

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
/*
            etQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                }
            });
            etQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        hideKeyboard();
                        return true;
                    }
                    return false;
                }
            });*/


            return view;
        }
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void refreshList(){

       /* lvMain.setAdapter(new ProductListAdapter());*/
        adapter.prodList = prodList;
        adapter.notifyDataSetChanged();

        total = 0;

        for(Product prod: prodList){
            total += (prod.getQuantity() * prod.getPrice());
        }

        tvTotal.setText("P " + String.format("%.2f", total));

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