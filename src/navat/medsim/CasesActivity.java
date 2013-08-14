package navat.medsim;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class CasesActivity extends Activity {
	private IntHolder lessonIndex = new IntHolder(-1);
	private String path;

	private FileFilter pdfFileFilter = new FileFilter() {
	    public boolean accept(File file) {
	        return file.getName().endsWith(".pdf");
	    }
	};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cases);
        
        final GridView grdLessons = (GridView) findViewById(R.id.grdLessons);
        lessonIndex.value = -1;
        
        Intent intent = getIntent();
        path = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        
        Utils.setListDir(this, grdLessons, path, pdfFileFilter, lessonIndex,
        		R.drawable.lesson, R.drawable.selected_lesson, R.drawable.rounded_lesson);
        
        grdLessons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
            public void onItemClick(AdapterView<?> parent, final View view,
                int position, long id) {
              final String item = (String) parent.getItemAtPosition(position);
              File targetFile = new File(path + "/" + item);
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
            	lessonIndex.value = position;
                ((ArrayAdapter)grdLessons.getAdapter()).notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cases, menu);
        return true;
    }
}
