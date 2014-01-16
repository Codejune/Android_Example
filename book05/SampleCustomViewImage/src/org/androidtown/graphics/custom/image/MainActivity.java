package org.androidtown.graphics.custom.image;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 직접 만든 뷰 위에 이미지를 그리는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 직접 만든 뷰를 화면에 설정
        CustomViewImage myView = new CustomViewImage(this);
        setContentView(myView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
