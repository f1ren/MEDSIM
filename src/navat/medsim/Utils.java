package navat.medsim;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.os.Environment;
import android.widget.AbsListView;

public class Utils {
	static String ROOT_PATH = Environment.getExternalStorageDirectory().getPath() + "/medsim";
	
	static private ArrayList<String> getDir(String path, FileFilter fileFilter) {
		ArrayList<String> result = new ArrayList<String>();
		
		File dir = new File(path);
    	File[] files = dir.listFiles(fileFilter);
    	Arrays.sort(files);
    	for (File file : files) {
    		result.add(file.getName());
    	}
    	
    	return result;
	}
	
	static public void setListDir(Context context, AbsListView listView, String path, FileFilter fileFilter,
			IntHolder position, int icon, int selectedRes, int notSelecetedRes) {
        final StableArrayAdapter adapter = new StableArrayAdapter(context,
                android.R.layout.simple_list_item_1, getDir(path, fileFilter),
                position, icon, selectedRes, notSelecetedRes);
        listView.setAdapter(adapter);
	}

}
