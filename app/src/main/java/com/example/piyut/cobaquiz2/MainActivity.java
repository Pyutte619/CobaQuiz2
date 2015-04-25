package com.example.piyut.cobaquiz2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Pertanyaan");
    ParseObject quizRecords=new ParseObject("QuizRecords");
    ArrayList<String> mStringList;
    String[] mStringArray;
    TextView textView;
    ListView pilihan;
    int top1=0,top2=0,top3=0;
    String[] pilihanArray;
    String[] kategori={"Linguistik","Natural","Intrapersonal","Sosial","Musik","Kinestetik","Spasial","Logis"};
    int value;int i=0;int[] jumlah={0,0,0,0,0,0,0,0};
    Button buttonNext;
    String top1String="",top2String="",top3String="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pilihanArray=getResources().getStringArray(R.array.Pilihan);
        textView= (TextView) findViewById(R.id.textView);
        pilihan= (ListView) findViewById(R.id.listView);
        buttonNext= (Button) findViewById(R.id.button);
        buttonNext.setOnClickListener(this);
        mStringList= new ArrayList<String>();

        mStringArray = new String[mStringList.size()];
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,pilihanArray);
        pilihan.setAdapter(adapter);
        setListViewHeightBasedOnChildren(pilihan);
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "lQhzDi2BFCvxHdQKz803CVDqqMwTcZzDV2SfJl08", "Rkf8vzsbmzBVLirYhqEcxesojgHOg3Rh6NUcwgx8");

        //TODO: action of choosing item on listview
        pilihan.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                value=5;
                    Toast.makeText(MainActivity.this,"Pilihanmu sangat sesuai",Toast.LENGTH_SHORT).show();
                }else if(position==1){
                    value=4;
                    Toast.makeText(MainActivity.this,"Pilihanmu sesuai",Toast.LENGTH_SHORT).show();

                }else if(position==2){
                    value=3;
                    Toast.makeText(MainActivity.this,"Pilihanmu agak sesuai",Toast.LENGTH_SHORT).show();

                }else if(position==3){
                    value=2;
                    Toast.makeText(MainActivity.this,"Pilihanmu tidak sesuai",Toast.LENGTH_SHORT).show();

                }else if(position==4){
                    value=1;
                    Toast.makeText(MainActivity.this,"Pilihanmu sangat tidak sesuai",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this,"Kamu belum memilih jawaban",Toast.LENGTH_SHORT).show();

                }
            }
        });

//TODO: pertanyaan display

        query.orderByAscending("numb");
        query.findInBackground(new FindCallback<ParseObject>(){
            @Override
            public void done(List<ParseObject> l, com.parse.ParseException e){
                if(e == null){
                    for(int i = 0; i <l.size();i++){

                        mStringList.add(l.get(i).getString("pertanyaan"));

                    }
                    mStringArray = mStringList.toArray(mStringArray);
                    for(int j = 0; j < mStringArray.length ; j++){
                        Log.d("string is "+j, (mStringArray[j]));

                    }textView.setText(mStringArray[0]);

                } else{//handle the error}
           //         AlertDialog
                }

            }
        });
            }
//TODO: method for recording values to parse.com
    public void recordValue(int j){
if (j%8==0){
    jumlah[0]=jumlah[0]+value;
    Log.d("jumlah"+1,""+jumlah[0]);
}else if (j%8==1){
    jumlah[1]=jumlah[1]+value;
    Log.d("jumlah"+2,""+jumlah[1]);
}else if (j%8==2){
    jumlah[2]=jumlah[2]+value;
    Log.d("jumlah"+3,""+jumlah[2]);
}else if (j%8==3){
    jumlah[3]=jumlah[3]+value;
    Log.d("jumlah"+4,""+jumlah[3]);
}else if (j%8==4){
    jumlah[4]=jumlah[4]+value;
    Log.d("jumlah"+5,""+jumlah[4]);
}else if (j%8==5){
    jumlah[5]=jumlah[5]+value;
    Log.d("jumlah"+6,""+jumlah[5]);
}else if (j%8==6){
    jumlah[6]=jumlah[6]+value;
    Log.d("jumlah"+7,""+jumlah[6]);
}else if (j%8==7){
    jumlah[7]=jumlah[7]+value;
    Log.d("jumlah"+8,""+jumlah[7]);
}else{

}
    }
//TODO: Save records results to parse.com
    public void saveToParse(String[] array){
        for(int i=0;i<array.length;i++){
    quizRecords.put(array[i], jumlah[i]);
    quizRecords.saveInBackground();
}
    }

    //TODO: get the top 3
    public void getTop3(){
        /*int max=0;
        for(int i=0;i<jumlah.length;i++){
            max=Math.max(max,i);
        }
        Integer max1 = null, max2 = null;
        for(int i=0;i<jumlah.length;i++) {
            if(max2 == null || max1 == null || max1 <= jumlah[i]) {
                max2 = max;
                max1 = jumlah[i];
            }}
        Log.d("max1", String.valueOf(max1));

        Log.d("max1", String.valueOf(max2));
        */ArrayList<Integer> results=new ArrayList();

        for(int i=0;i<jumlah.length;i++) {
            results.add(jumlah[i]);
        }/*Arrays.asList(new QuizResult(kategori[0], jumlah[0]),
                new QuizResult(kategori[1], jumlah[1]),
                new QuizResult(kategori[2], jumlah[2]),
                new QuizResult(kategori[3], jumlah[3]),
                new QuizResult(kategori[4], jumlah[4]),
                new QuizResult(kategori[5], jumlah[5]),
                new QuizResult(kategori[6], jumlah[6]),
                new QuizResult(kategori[7], jumlah[7]));
*/
        Collections.sort(results);
        top1=results.get(7);
        top2=results.get(6);
        top3=results.get(5);

        Log.d("top1", String.valueOf(top1));
        Log.d("top2", String.valueOf(top2));
        Log.d("top3", String.valueOf(top3));

        Records[] records= new Records[8];
        for(int i=0;i<jumlah.length;i++) {
            records[i]=new Records(jumlah[i],kategori[i]);
Log.d("record",records[i].getVariabel());
            Log.d("record", String.valueOf(records[i].getJumlah()));         }

        String[][] data = new String[][] {
                new String[] { String.valueOf(jumlah[0]), kategori[0] },
                new String[] { String.valueOf(jumlah[1]), kategori[1] },
                new String[] { String.valueOf(jumlah[2]), kategori[2] },
                new String[] { String.valueOf(jumlah[3]), kategori[3] },
                new String[] { String.valueOf(jumlah[4]), kategori[4] },
                new String[] { String.valueOf(jumlah[5]), kategori[5] },
                new String[] { String.valueOf(jumlah[6]), kategori[6] },
                new String[] { String.valueOf(jumlah[7]), kategori[7] }};

        Arrays.sort(data, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                final String jumlah1 = entry1[0];
                final String jumlah2 = entry2[0];
                return jumlah1.compareTo(jumlah2);
            }
        });
Records[] r=new Records[8];
        for (final String[] s : data) {if(i<8){
            Log.d("testing",""+s[0] + " " + s[1]);r[i]=new Records(Integer.valueOf(s[0]),s[1]);Log.d("testing rec", String.valueOf(r[i].getJumlah()));Log.d("testing rec",r[i].getVariabel());}i++;
        }


        for(int i=0;i<jumlah.length;i++) {
            if(top1==records[i].getJumlah()){
                top1String=records[i].getVariabel();
            }
            else if(top2==records[i].getJumlah()){
                top2String=records[i].getVariabel();
                }
            else if(top3==records[i].getJumlah()){
                    top3String=records[i].getVariabel();

            }
        }
        Log.d("tops1",top1String);
        Log.d("tops2",top2String);
        Log.d("tops3",top3String);
        quizRecords.put("top1",top1String);
        quizRecords.put("top2",top2String);
        quizRecords.put("top3",top3String);
        quizRecords.saveInBackground();
        }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
if(i<39){
    textView.setText(mStringArray[i+1]);
    recordValue(i);
}
else if(i==39){
buttonNext.setText("Submit");
    recordValue(i);
    saveToParse(kategori);
        }else if(i>39){
    getTop3();
    Intent intent=new Intent(MainActivity.this,RecordResult.class);
    intent.putExtra("top1",top1String);
    intent.putExtra("top2",top2String);
    intent.putExtra("top3",top3String);

    intent.putExtra("top1Int",top1);
    intent.putExtra("top2Int",top2);
    intent.putExtra("top3Int",top3);

    MainActivity.this.startActivity(intent);
        }
        i++;
    }


}
class QuizResult implements Comparable
{
    final int t;
    String string;
    QuizResult(String string, int t)
    {
        this.string=string;
        this.t = t;
    }

    @Override
    public int compareTo(Object o)
    {
        return new Integer(t).compareTo((Integer)((QuizResult)o).t);
    }

    @Override
    public String toString()
    {
        return string;
    }
}

class Records{
    int jumlah;
    String variabel;
public  Records(int j, String s){
    jumlah=j;
    variabel=s;
}
    public String getVariabel(){

        return variabel;
}
    public int getJumlah(){
        return jumlah;
    }

}

