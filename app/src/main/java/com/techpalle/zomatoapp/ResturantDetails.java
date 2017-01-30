package com.techpalle.zomatoapp;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResturantDetails extends Fragment {
    RecyclerView recyclerView;
    MyDatabase myDatabase;
    MyrecyclerViewAdapter myRecyclerViewAdapter;
    MyTask myTask;
    EditText et2;
    Button b2;
    Cursor cursor;
    int pos;
    double curlat,curlong;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDatabase=new MyDatabase(getActivity());
        myDatabase.open();
    }

    @Override
    public void onDestroy() {
        myDatabase.close();
        super.onDestroy();
    }

//    public void showPopup(View v){
//        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
//        MenuInflater menuInflater = popupMenu.getMenuInflater();
//        menuInflater.inflate(R.menu.overflowmenu, popupMenu.getMenu());
//
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.map:
////                        Intent intent = new Intent(getActivity(), MapsActivity.class);
////                        String lat = myDatabase.queryLatitude();
////                        intent.putExtra("latitude",);
////                        intent.putExtra("longitude",);
////                        intent.putExtra("name",);
////                        startActivity(intent);
//                        break;
//                    case R.id.web:
//                }
//                return false;
//            }
//        });
//
//        popupMenu.show();
 //}

    public class MyTask extends AsyncTask<String,Void,String>{
        URL myURL;
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String line;
        StringBuilder result;

        @Override
        protected String doInBackground(String... strings) {
            //20   7 step process
            try {
                myURL = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) myURL.openConnection();
                httpURLConnection.setRequestProperty("Accept","application/json");
                httpURLConnection.setRequestProperty("user-key","bfc610fbd2a40a922dc01b84c2610d4f");
                httpURLConnection.connect();

                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
                line = bufferedReader.readLine();
                result = new StringBuilder();
                while (line!=null)
                {
                    result.append(line);
                    line = bufferedReader.readLine();
                }
                return result.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "some thing went worng";
        }


        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject a = new JSONObject(s);
                JSONArray j = a.getJSONArray("nearby_restaurants");
                for (int i=0;i<j.length();i++)
                {
                    JSONObject k = j.getJSONObject(i);
                    JSONObject p = k.getJSONObject("restaurant");
                    String name = p.getString("name");
                    String thumb = p.getString("thumb");
                    JSONObject m = p.getJSONObject("location");
                    String locality = m.getString("locality");
                    String address = m.getString("address");
                    String latitude = m.getString("latitude");
                    String longitude = m.getString("longitude");

                myDatabase.insertResturant(name,locality,address,thumb,latitude,longitude);
                }
                myRecyclerViewAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("B-34","JSON PARSING ERROR");
            }
            super.onPostExecute(s);
        }
    }

        public class MyrecyclerViewAdapter extends RecyclerView.Adapter<MyrecyclerViewAdapter.ViewHolder>{

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v=getActivity().getLayoutInflater().inflate(R.layout.row,parent,false);
                ViewHolder viewHolder=new ViewHolder(v);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
            cursor.moveToPosition(position);
                String name=cursor.getString(1);
                String locality=cursor.getString(2);
                String imageurl=cursor.getString(3);
                String address=cursor.getString(4);
                String longitude=cursor.getString(5);
                String latitude=cursor.getString(6);

                holder.tv1.setText(name);
                holder.tv2.setText(locality);
                holder.tv3.setText(address);
                holder.overflow.setTag(position);// new code
                Glide.with(getActivity()).load(imageurl).placeholder(R.mipmap.ic_launcher).crossFade().into(holder.thumb);
                holder.overflow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView imageView = (ImageView) v;
                        pos = (int)imageView.getTag();
//                        showPopup(v);

                    }
                });
            }


            @Override
            public int getItemCount() {
                return cursor.getCount();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                public TextView tv1,tv2,tv3;
                public ImageView thumb,overflow;
                public ViewHolder(View itemView)
                {
                    super(itemView);
                    tv1 = (TextView) itemView.findViewById(R.id.textView);
                    tv2 = (TextView) itemView.findViewById(R.id.textView2);
                    tv3 = (TextView) itemView.findViewById(R.id.textView3);
                    thumb = (ImageView) itemView.findViewById(R.id.imageView);
                    overflow = (ImageView) itemView.findViewById(R.id.imageView1);

                }
            }
        }
    public ResturantDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_resturant_details, container, false);
        et2= (EditText) v.findViewById(R.id.edittext2);
        b2= (Button) v.findViewById(R.id.button1);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Homeactivity homeactivity= (Homeactivity) getActivity();
                if (homeactivity.checkInternet()) {
                    myTask.execute("https://developers.zomato.com/api/v2.1/geocode?lat=" + curlat + "&lon=" + curlong);
                }else {
                    Toast.makeText(homeactivity, "check internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView= (RecyclerView) v.findViewById(R.id.recyclearview2);
        cursor=myDatabase.queryResturant();
        myRecyclerViewAdapter= new MyrecyclerViewAdapter();
        myTask=new MyTask();
        LinearLayoutManager linearlayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearlayoutManager);
        recyclerView.setAdapter(myRecyclerViewAdapter);
        return v;
    }

}
