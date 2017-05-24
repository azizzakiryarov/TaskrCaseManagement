package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import fragment.FragmentDone;
import fragment.FragmentMyTask;
import fragment.FragmentStarted;
import fragment.FragmentUnstarted;


public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentUnstarted();
            case 1:
                return new FragmentStarted();
            case 2:
                return new FragmentDone();
            case 3:
                return new FragmentMyTask();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
