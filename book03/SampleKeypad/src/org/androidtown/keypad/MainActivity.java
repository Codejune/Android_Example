package org.androidtown.keypad;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 키패드의 유형을 설정하는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
