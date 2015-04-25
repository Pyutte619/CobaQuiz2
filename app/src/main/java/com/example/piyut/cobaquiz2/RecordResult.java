package com.example.piyut.cobaquiz2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piyut on 19/04/2015.
 */
public class RecordResult extends Activity {
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Kesimpulan");
    ArrayList<String> mStringList;
    ArrayList<String> mStringList2;
    String[] mStringArray;
    String[] contents={"","",""};
    int top1Int,top2Int,top3Int;

    TempResultsContents[] resultsContentses=new TempResultsContents[8];
    TempToprecords[] toprecordses=new TempToprecords[3];

    String[] mStringArray2;
    ArrayList<String> tes=new ArrayList<>();
    ArrayList<Integer> tes2=new ArrayList<>();
    String top1="",top2="",top3="";
    TextView textViewResultTitle1, textViewResultContent1,
            textViewResultTitle2, textViewResultContent2,
            textViewResultTitle3, textViewResultContent3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        top1=getIntent().getStringExtra("top1");
        top2=getIntent().getStringExtra("top2");
        top3=getIntent().getStringExtra("top3");

        top1Int=getIntent().getIntExtra("top1Int",0);
        top2Int=getIntent().getIntExtra("top2Int",0);
        top3Int=getIntent().getIntExtra("top3Int",0);

        tes.add(top1);
        tes.add(top2);
        tes.add(top3);
String[] topString={top1,top2,top3};
        int[] topInt= new int[]{top1Int, top2Int, top3Int};
        //tes2.add(top1Int);
        //tes2.add(top2Int);
        //tes2.add(top3Int);

        Log.d("tes intent1",top1);
        Log.d("tes intent2",top2);
        Log.d("tes intent3",top3);
        Log.d("tes intent", String.valueOf(top1Int));
        Log.d("tes intent", String.valueOf(top2Int));
        Log.d("tes intent", String.valueOf(top3Int));

        for(int i=0;i<3;i++){
            try {
                toprecordses[i]=new TempToprecords(topInt[i],topString[i]);

                Log.d("class records score", String.valueOf(toprecordses[i].getScore()));
                Log.d("class records cat", String.valueOf(toprecordses[i].getCategory()));

            }catch (Exception e){

            }
        }

        mStringList= new ArrayList<String>();
        mStringList2= new ArrayList<String>();
        mStringArray = new String[mStringList.size()];
        mStringArray2 = new String[mStringList.size()];

        textViewResultTitle1 = (TextView) findViewById(R.id.textViewResult);
        textViewResultContent1 = (TextView) findViewById(R.id.textViewResultontent);

        textViewResultTitle2 = (TextView) findViewById(R.id.textViewResult2);
        textViewResultContent2 = (TextView) findViewById(R.id.textViewResultontent2);

        textViewResultTitle3 = (TextView) findViewById(R.id.textViewResult3);
        textViewResultContent3 = (TextView) findViewById(R.id.textViewResultontent3);

        textViewResultTitle1.setText(top1);
        textViewResultTitle2.setText(top2);
        textViewResultTitle3.setText(top3);


        query.orderByAscending("no");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e==null){

                    for(int i=0;i<parseObjects.size();i++){
                        query.whereEqualTo("kategori",top1);
                            //mStringList.add(parseObjects.get(i).getString("keterangan"));
                        //mStringList2.add(parseObjects.get(i).getString("kategori"));

                        resultsContentses[i]=new TempResultsContents(parseObjects.get(i).getString("kategori"),
                                parseObjects.get(i).getString("keterangan"));
                        Log.d("class result kat",resultsContentses[i].getKategori());
                        Log.d("class result ket",resultsContentses[i].getKeterangan());

//                        Log.d("tes ket", mStringList.get(i));
  //                      Log.d("tes kat", mStringList2.get(i));

                    } //Log.d("tes cari", mStringList.get(2));
    //                mStringArray = mStringList.toArray(mStringArray);
      //              mStringArray2 = mStringList2.toArray(mStringArray2);
        //            for (int i=0;i<3;i++){
          //              resultsContentses[i]=new TempResultsContents(mStringArray2[i],mStringArray[i]);
                    //    Log.d("class result kat",resultsContentses[i].getKategori());
                      //  Log.d("class result ket",resultsContentses[i].getKeterangan());

                       matchItem();

                    Log.d("match1",contents[0]);
                    Log.d("match2",contents[1]);
                    Log.d("match3",contents[2]);

                    for(int j = 0; j < mStringArray.length ; j++){

                        Log.d("the string is "+j, (mStringArray[j]));

                    }
                    textViewResultContent1.setText(contents[0]);
                    textViewResultContent2.setText(contents[1]);
                    textViewResultContent3.setText(contents[2]);
                }else{
                    //handle error
                }
            }
        });
    }

    public void matchItem(){
        for(int i=0;i<8;i++){
try {
    if (resultsContentses[i].getKategori().equals( toprecordses[0].getCategory())) {
        contents[0] = resultsContentses[i].getKeterangan();
        //Log.d("coba",resultsContentses[i].getKeterangan());
    }else if (resultsContentses[i].getKategori().equals( toprecordses[1].getCategory()) ) {
        contents[1] = resultsContentses[i].getKeterangan();
    }else if (resultsContentses[i].getKategori().equals(toprecordses[2].getCategory()) ) {
        contents[2] = resultsContentses[i].getKeterangan();
    }else{

    }
}catch (Exception e){
}
            //error
        }
    }
}
class TempToprecords{
    int score=0;
    String category="";

    TempToprecords(int i,String s){
        this.score=i;
        this.category=s;
    }

    public int getScore(){
        return score;
    }
    public String getCategory(){
        return category;
    }
}
class TempResultsContents{
    String kategori="",keterangan="";

    TempResultsContents(String kat,String ket){
        this.kategori=kat;
        this.keterangan=ket;
    }

    public String getKategori(){
        return kategori;
    }

    public String getKeterangan(){
        return keterangan;
    }
}