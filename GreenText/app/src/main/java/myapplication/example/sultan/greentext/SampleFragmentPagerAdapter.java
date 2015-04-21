package myapplication.example.sultan.greentext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

/**
 * Created by Sultan
 */

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    private int[] imageResId = {
            R.mipmap.discovery_red,
            R.mipmap.writing_green,
            R.mipmap.profile_icon_teal
    };

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Discover", "Contribute", "Profile" };
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DiscoverFragment();
            case 1:
                return new ContributeFragment();
            case 2:
                return new ProfileFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, 50, 50);
        // Replace blank spaces with image icon
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}
