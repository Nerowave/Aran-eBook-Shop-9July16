package aran.appsomnee.aranebookshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by aransomnee on 7/10/16 AD.
 */
public class MyAdapter extends BaseAdapter { //alt enter หลังการประกาศ extends

    //Explicit
    private Context context;
    private  String[] bookStrings, priceStrings, iconStrings; //กด Command + N เลือกหมด

    public MyAdapter(String[] bookStrings, Context context,
                     String[] iconStrings,
                     String[] priceStrings) {
        this.bookStrings = bookStrings;
        this.context = context;
        this.iconStrings = iconStrings;
        this.priceStrings = priceStrings;
    }

    @Override
    public int getCount() {
        return bookStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//เปิดเซอร์วิส
        View view1 = layoutInflater.inflate(R.layout.my_listview, viewGroup, false);

        //Bind Widget
        TextView bookTextView = (TextView) view1.findViewById(R.id.textView12);
        TextView priceTextView = (TextView) view1.findViewById(R.id.textView13);//TextView price (control + space) =view1.findV
        ImageView iconImageView = (ImageView) view1.findViewById(R.id.imageView2);

        bookTextView.setText(bookStrings[i]);
        priceTextView.setText(priceStrings[i]);
        Picasso.with(context).load(iconStrings[i]).resize(150,180).into(iconImageView);

        return view1;
    }
} //Main Class

