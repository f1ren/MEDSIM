package navat.medsim;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
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
    private int selectedRes;
    private int notSelectedRes;

    public StableArrayAdapter(Context context, int textViewResourceId,
        List<String> objects, IntHolder position, int listIcon, int selectedRes,
        int notSelectedRes) {
      super(context, textViewResourceId, objects);
      this.context = context;
      this.icon = listIcon;
      this.selectedPosition = position;
      this.selectedRes = selectedRes;
      this.notSelectedRes = notSelectedRes;
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
    	if (str.indexOf(';', str.indexOf(';', 0) + 1) != -1) {
    		str = str.substring(str.indexOf(';') + 1);
    	}
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
    	  rowView.setBackgroundResource(selectedRes);
      } else {
    	  rowView.setBackgroundResource(notSelectedRes);
      }
      return rowView;
    }
}