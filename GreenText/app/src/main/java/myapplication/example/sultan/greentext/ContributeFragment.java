package myapplication.example.sultan.greentext;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ContributeFragment extends Fragment {

    RecyclerView recyclerView;
    private ContributeRecyclerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.activity_contribute_fragment, container, false);

        LinearLayout mainLayout=(LinearLayout) layout.findViewById(R.id.contributeBottomBar);
        mainLayout.setVisibility(LinearLayout.GONE);

        recyclerView = (RecyclerView) layout.findViewById(R.id.contributeDrawerList);
        adapter = new ContributeRecyclerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return layout;
    }

    public static List<ContributeRecyclerInfo> getData(){
        List<ContributeRecyclerInfo> data = new ArrayList<>();
        int[] icons = {R.mipmap.book_teal, R.mipmap.book_red, R.mipmap.book_orange, R.mipmap.book_green, R.mipmap.book_yellow, R.mipmap.book_blue};
        String[] titles = {"Anon goes to the Circus" , "Anon finds a potato", "The story of Anon", "To Kill A Anon", "Anon Jackson", "Anon Potter"};
        for(int i=0; i < titles.length && i < icons.length; i++)
        {
            ContributeRecyclerInfo current = new ContributeRecyclerInfo();
            current.iconId = icons[i];
            current.title = titles[i];
            data.add(current);
        }

        return data;
    }
}