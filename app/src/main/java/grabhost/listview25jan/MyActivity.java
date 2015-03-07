package grabhost.listview25jan;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity {
    ListView list;
    String[] memoTitles;
    String[] memoDescriptions;


    int[] images ={R.drawable.memo1, R.drawable.memo2, R.drawable.memo3 , R.drawable.memo4 , R.drawable.memo5 , R.drawable.memo6 , R.drawable.memo7 , R.drawable.memo8 , R.drawable.memo9};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        registerClickCallback();
        Resources res = getResources();
        memoTitles = res.getStringArray(R.array.titles);
        memoDescriptions = res.getStringArray(R.array.descriptions);

        list = (ListView) findViewById(R.id.listView);    //  alt + enter to type cast listView
        AnkeetAdapter adapter = new AnkeetAdapter(this, memoTitles, images, memoDescriptions);
        list.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void registerClickCallback(){
        list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = (TextView) view;
                String message = "you clicked # "+ position
                        +", which is string:" + textView.getText().toString();
                Toast.makeText(MyActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}


class AnkeetAdapter extends ArrayAdapter<String>
{
    Context context;
    int[] images;
    String[] titleArray;
    String[] descriptionArray;
    AnkeetAdapter(Context c ,String[] titles, int imgs[], String[] desc)                 // defining constructor   default constructor
    {
        super(c,R.layout.single_row,R.id.textView,titles);
        this.context = c;
        this.images = imgs;
        this.titleArray = titles;     // title array contains our variable memo1, memo2.....9
        this.descriptionArray = desc;
    }
    class MyViewHolder{           //
        ImageView myImage;
        TextView myTitle;
        TextView myDescription;

        MyViewHolder(View v){

            myImage = (ImageView) v.findViewById(R.id.imageView);
            myTitle = (TextView) v.findViewById(R.id.textView);
            myDescription = (TextView) v.findViewById(R.id.textView2);

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {         // This app uses too much heap memory use object or view recycling by optimization

        View row = convertView;
        MyViewHolder holder = null;
        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // this  LayoutInflater  is the class that converts single_row from xml base object into a java based view object
             row = inflater.inflate(R.layout.single_row, parent, false);    // row is the object
            holder = new MyViewHolder(row);
            row.setTag(holder);
            Log.d("Ankeet","Creating a new row");

        }
        else {
                holder = (MyViewHolder) row.getTag();    // reusing the holder
        }

       // ImageView myImage = (ImageView) row.findViewById(R.id.imageView);    //  alt+enter for casting necessary for ImageView
       // TextView myTitle = (TextView) row.findViewById(R.id.textView);
       //TextView myDescription = (TextView) row.findViewById(R.id.textView2);

        holder.myImage.setImageResource(images[position]);
        holder.myTitle.setText(titleArray[position]);
        holder.myDescription.setText(descriptionArray[position]);

        return row;
    }
}