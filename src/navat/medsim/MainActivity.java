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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private String module = new String();
	private String application = new String();
	private IntHolder applicationIndex = new IntHolder(-1);
	private IntHolder moduleIndex = new IntHolder(-1);
	public final static String EXTRA_MESSAGE = "navat.medsim.MESSAGE";
	
	private FileFilter foldersFileFilter = new FileFilter() {
	    public boolean accept(File file) {
	        return file.isDirectory();
	    }
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        
        /*final Button button = (Button) findViewById(R.id.btnExit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Intent.ACTION_MAIN);
            	intent.addCategory(Intent.CATEGORY_HOME);
            	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	startActivity(intent);
            }
        });*/
        
        final GridView grdApplications = (GridView) findViewById(R.id.grdApplications);
        final GridView grdModules = (GridView) findViewById(R.id.grdModules);
        
        Utils.setListDir(this, grdApplications, Utils.ROOT_PATH, foldersFileFilter, applicationIndex, 
        		R.drawable.application, R.drawable.selected_application, R.drawable.rounded_application);
        
        grdApplications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
            public void onItemClick(AdapterView<?> parent, final View view,
                int position, long id) {
              final String item = (String) parent.getItemAtPosition(position);
              Utils.setListDir(MainActivity.this, grdModules, Utils.ROOT_PATH + "/" + item,
            		  foldersFileFilter, moduleIndex, R.drawable.module, R.drawable.selected_module,
            		  R.drawable.rounded_module);
              //grdLessons.setAdapter(null);
              application = item;
              applicationIndex.value = position;
              moduleIndex.value = -1;
              ((ArrayAdapter)grdApplications.getAdapter()).notifyDataSetChanged();
            }
        });

        grdModules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
            public void onItemClick(AdapterView<?> parent, final View view,
                int position, long id) {
              final String item = (String) parent.getItemAtPosition(position);
              
              Intent intent = new Intent(MainActivity.this, CasesActivity.class);
              intent.putExtra(EXTRA_MESSAGE, Utils.ROOT_PATH + "/" + application + "/" + item);
              startActivity(intent);

              module = item;
              moduleIndex.value = position;
              ((ArrayAdapter)grdModules.getAdapter()).notifyDataSetChanged();
            }
        });
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public void onAttachedToWindow() { //disable home button in this activity
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
        super.onAttachedToWindow();
    }
    
    @Override
    public void onBackPressed() { //disable back button in this activity
        return;
    }*/
}
