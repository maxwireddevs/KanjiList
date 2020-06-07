package com.wireddevs.kanjilist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.wireddevs.kanjilist.database.itemHelper;
import java.text.Normalizer;
import java.util.ArrayList;

public class TextAdapter extends BaseAdapter {
    private Context mContext;
    private static String[] allkanji;
    private static String[] alllatin;
    private static String[] allhiragana;
    private static String[] allmeaning;
    public static ArrayList<Integer> positions=new ArrayList<>();
    private static int numofchars;
    private Integer[] status=new Integer[2136];

    // Constructor
    public TextAdapter(Context c) {
        mContext = c;
        allkanji =c.getResources().getStringArray(R.array.kanji);
        alllatin=c.getResources().getStringArray(R.array.latin);
        allhiragana =c.getResources().getStringArray(R.array.hiragana);
        allmeaning=c.getResources().getStringArray(R.array.meaning);
        itemHelper ih=new itemHelper(c);
        Cursor res=ih.getAllData();
        while(res.moveToNext()){
            status[res.getPosition()]=res.getInt(1);
        }
    }

    public int getCount() {
        return positions.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        TextView chara;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_item, null);
            chara = (TextView) grid.findViewById(R.id.hanziitem);
        }
        else{
            grid = (View) convertView;
            chara = (TextView) grid.findViewById(R.id.hanziitem);
        }

        chara.setText(allkanji[positions.get(position)]);

        if(status[position]==1){
            chara.setAlpha((float)0.6);
        }
        else if(status[position]==2){
            chara.setAlpha((float)0.2);
        }
        else{
            chara.setAlpha(1);
        }

        return grid;
    }



    static void setGridView(int level){
        positions.clear();
        switch(level) {
            case 0:
                numofchars = 80;
                break;
            case 1:
                numofchars = 240;
                break;
            case 2:
                numofchars = 440;
                break;
            case 3:
                numofchars = 640;
                break;
            case 4:
                numofchars = 825;
                break;
            case 5:
                numofchars = 1006;
                break;
            case 6:
                numofchars = 2136;
                break;
        }
        for(int i=0;i<numofchars;i++){
            positions.add(i);
        }
    }
    static String getTextMessage(int position){
        return allkanji[positions.get(position)]+"\n"+ allhiragana[positions.get(position)]+"\n"+ alllatin[positions.get(position)]+"\n"+"meaning: "+allmeaning[positions.get(position)];
    }


    public void refreshStatus(Context c){
        itemHelper ih=new itemHelper(c);
        Cursor res=ih.getAllData();
        while(res.moveToNext()){
            status[res.getPosition()]=res.getInt(1);
        }
    }
}