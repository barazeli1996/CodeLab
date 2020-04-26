package aAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.barazeli.codelab.R;

import java.util.List;

import aFragments.ChatFragment;
import aFragments.FriendsFragment;
import aFragments.StatusFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ChatFragment();
            case 1: return new FriendsFragment();
            case 2: return new StatusFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Chat";
            case 1: return "Friends";
            case 2: return "Status";
            default: return "";
        }
    }
}
