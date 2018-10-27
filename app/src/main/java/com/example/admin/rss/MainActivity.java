package com.example.admin.rss;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView lvTieude;
ArrayAdapter adapter;
ArrayList<String> arrayTitle,arrayLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTieude=(ListView )findViewById(R.id.lv_tieude) ;
        arrayTitle =new ArrayList<>();
        arrayLink= new ArrayList<>();
        adapter= new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,arrayTitle);
        lvTieude.setAdapter(adapter);
        new ReadRSS().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
        lvTieude.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Toast.makeText(MainActivity.this,arrayLink.get(i),Toast.LENGTH_LONG).show();
                Intent intent= new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("linktintuc",arrayLink.get(i));
                startActivity(intent);
            }
        });
    }
    private class ReadRSS extends AsyncTask<String, Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content= new StringBuilder();
            try {
                URL url= new URL(strings[0]);
                InputStreamReader inputStreamReader= new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader= new BufferedReader(inputStreamReader);
                String line="";
                while((line = bufferedReader.readLine())!=null){
                    content.append(line);
                }
                bufferedReader.close();
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch ( IOException e){
                e.printStackTrace();
            }


            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser= new XMLDOMParser();
            Document document= parser.getDocument(s);
            NodeList nodeList= document.getElementsByTagName("item");
            String tieude="";
            for(int i=0;i<nodeList.getLength();i++){
                Element element = (Element) nodeList.item(i);
                tieude =parser.getValue(element,"title");
               arrayTitle.add(tieude);
                arrayLink.add(parser.getValue(element,"link"));
            }
            adapter.notifyDataSetChanged();
            //Toast.makeText(MainActivity.this, tieude, Toast.LENGTH_LONG).show();
        }
    }
}
