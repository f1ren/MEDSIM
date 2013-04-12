package navat.medsim;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StableArrayAdapter extends ArrayAdapter {
    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    private final Context context;
    private int icon;
    public int selectedItem = -1;
    private IntHolder selectedPosition;

    public StableArrayAdapter(Context context, int textViewResourceId,
        List<String> objects, IntHolder position, int listIcon) {
      super(context, textViewResourceId, objects);
      this.context = context;
      this.icon = listIcon;
      this.selectedPosition = position;
      for (int i = 0; i < objects.size(); ++i) {
        mIdMap.put(objects.get(i), i);
      }
    }

    @Override
    public long getItemId(int position) {
      String item = (String)getItem(position);
      return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }
    
    private String cleanString(String str) {
    	return str.replace(".pdf", "").replace(";","\n");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View rowView = inflater.inflate(R.layout.row, parent, false);
      TextView textView = (TextView) rowView.findViewById(R.id.label);
      //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
      textView.setText(cleanString((String)getItem(position)));
      //imageView.setImageResource(this.icon);
      if (position == this.selectedPosition.value) {
    	  //rowView.setBackgroundColor(0xA0D5EFFD);
    	  rowView.setBackgroundResource(R.drawable.rounded);
      }
      return rowView;
    }
}