package org.androidtown.graphics.custom.drawables;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * ���� ���� �� ���� �پ��� �׷��� ��ҵ��� �׸��� ����� ���� �� �� �ֽ��ϴ�.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ���� ���� �並 ȭ�鿡 ����
        CustomViewDrawables myView = new CustomViewDrawables(this);
        setContentView(myView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
