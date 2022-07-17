package sg.edu.rp.c346.id21036147.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class SongListActivity extends AppCompatActivity {


    ArrayList<Songs> al;
    ArrayList<Songs> alYear;
    ArrayList<Songs> alYearString;
    ArrayAdapter<Songs> aa;
    Spinner year;
    ListView lv;
    ToggleButton tbStar;

    @Override
    protected void onResume() {
        super.onResume();

        DBHelper dbh = new DBHelper(SongListActivity.this);
        al.clear();
        al.addAll(dbh.getAllSongs());

        aa.notifyDataSetChanged();

        year = findViewById(R.id.yearFilter);


        ArrayAdapter aaYear = new ArrayAdapter(this,android.R.layout.simple_spinner_item,dbh.getAllYears());
        aaYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(aaYear);
        aaYear.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        lv = findViewById(R.id.lv);
        year = findViewById(R.id.yearFilter);
        tbStar = findViewById(R.id.tbFiveStars);

        al = new ArrayList<Songs>();
        aa = new ArrayAdapter<Songs>(this,
                android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Songs data = al.get(position);
                Intent i = new Intent(SongListActivity.this,
                        EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String filterText = year.getSelectedItem().toString();

                al.clear();
                DBHelper dbh = new DBHelper(SongListActivity.this);
                al.addAll(dbh.getAllSongs(filterText));

                aa.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                al.clear();
                DBHelper dbh = new DBHelper(SongListActivity.this);
                al.addAll(dbh.getAllSongs());

                aa.notifyDataSetChanged();
            }
        });

        tbStar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                String filterText = "5";

                DBHelper dbh = new DBHelper(SongListActivity.this);

                al.clear();
                if(checked){
                    al.addAll(dbh.getAllFiveStar(filterText));
                }else{
                    al.addAll(dbh.getAllSongs());
                }

                aa.notifyDataSetChanged();
            }
        });
    }
}