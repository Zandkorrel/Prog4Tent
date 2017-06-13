package nl.code7.prog4tent_android.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import nl.code7.prog4tent_android.R;

public class FilmActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        super.onCreateDrawer(toolbar, this);
    }
}
