package org.androidtown.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * ù��° ���� ���� ���� ȭ���Դϴ�.
 * 
 * @author Mike
 */
public class NewActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);
        
        // ��ư ��ü ����
        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
		        Toast.makeText(getApplicationContext(), "���ư��� ��ư�� ���Ⱦ��.", Toast.LENGTH_LONG).show();
		        finish();
	        }
        });
        
    }
}