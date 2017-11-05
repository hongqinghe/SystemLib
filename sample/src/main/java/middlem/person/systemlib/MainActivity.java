package middlem.person.systemlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import middlem.person.systemmodule.SystemInputFullActivity;

public class MainActivity extends AppCompatActivity {
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toSystem(View view) {
        Intent intent=new Intent(this,SystemInputFullActivity.class);
        startActivity(intent);
    }
}
