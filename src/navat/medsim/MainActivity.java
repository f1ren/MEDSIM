package navat.medsim;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static String ROOT_PATH = Environment.getExternalStorageDirectory().getPath() + "/medsim";
	private String module = new String();
	private String application = new String();
	
	private FileFilter foldersFileFilter = new FileFilter() {
	    public boolean accept(File file) {
	        return file.isDirectory();
	    }
	};

	private FileFilter pdfFileFilter = new FileFilter() {
	    public boolean accept(File file) {
	        return file.getName().endsWith(".pdf");
	    }
	};
	
	private ArrayList<String> getDir(String path, FileFilter fileFilter) {
		ArrayList<String> result = new ArrayList<String>();
		
		File dir = new File(path);
    	File[] files = dir.listFiles(fileFilter);
    	for (File file : files) {
    		result.add(file.getName());
    	}
    	
    	return result;
	}
	
	private void setListDir(ListView listView, String path, FileFilter fileFilter) {
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, getDir(path, fileFilter));
        listView.setAdapter(adapter);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final ListView lstApplications = (ListView) findViewById(R.id.lstApplications);
        final ListView lstModules = (ListView) findViewById(R.id.lstModules);
        final ListView lstLessons = (ListView) findViewById(R.id.lstLessons);
        lstApplications.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
        setListDir(lstApplications, ROOT_PATH, foldersFileFilter);
        
        lstApplications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
            public void onItemClick(AdapterView<?> parent, final View view,
                int position, long id) {
              final String item = (String) parent.getItemAtPosition(position);
              setListDir(lstModules, ROOT_PATH + "/" + item, foldersFileFilter);
              application = item;
            }
        });

        lstModules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
            public void onItemClick(AdapterView<?> parent, final View view,
                int position, long id) {
              final String item = (String) parent.getItemAtPosition(position);
              setListDir(lstLessons, ROOT_PATH + "/" + application + "/" + item, pdfFileFilter);
              module = item;
            }
        });
        
        lstLessons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
            public void onItemClick(AdapterView<?> parent, final View view,
                int position, long id) {
              final String item = (String) parent.getItemAtPosition(position);
              File targetFile = new File(ROOT_PATH + "/" + application + "/" + module + "/" + item);
              Uri targetUri = Uri.fromFile(targetFile);
              Intent intent = new Intent(Intent.ACTION_VIEW);
            	intent.setDataAndType(targetUri, "application/pdf");
            	PackageManager pm = getPackageManager();
            	List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
            	if (activities.size() > 0) {
            	    startActivity(intent);
            	} else {
            		Toast.makeText(getApplicationContext(), "No PDF reader found", Toast.LENGTH_LONG).show();
            	}
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
