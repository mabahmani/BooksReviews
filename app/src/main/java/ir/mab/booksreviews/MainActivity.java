package ir.mab.booksreviews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ir.mab.booksreviews.history.HistoryFragment;
import ir.mab.booksreviews.scanner.ScannerFragment;
import ir.mab.booksreviews.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private Fragment scannerFrag = new ScannerFragment();
    private Fragment historyFag = new HistoryFragment();
    private Fragment settingsFrag = new SettingsFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_history:
                    loadFragment(historyFag);
                    return true;
                case R.id.navigation_scan:{
                    loadFragment(scannerFrag);
                    return true;
                }
                case R.id.navigation_settings:
                    loadFragment(settingsFrag);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.frame_container,fragment);
        fragmentTransaction1.addToBackStack(null);
        fragmentTransaction1.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_scan);

    }

}
