package com.made.lianda.kamus;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.made.lianda.kamus.adapter.IndonesiaAdapter;
import com.made.lianda.kamus.db.KamusHelper;
import com.made.lianda.kamus.model.Indonesia;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.made.lianda.kamus.DetailIndonesiaActivity.DETAIL_KATA;
import static com.made.lianda.kamus.db.DatabaseContract.IndonesiaColumns.KATA;
import static com.made.lianda.kamus.db.DatabaseContract.IndonesiaColumns.TERJEMAH;


/**
 * A simple {@link Fragment} subclass.
 */
public class InEnFragment extends Fragment implements IndonesiaAdapter.OnKataClickCallback {

    RecyclerView recyclerView;
    IndonesiaAdapter adapter;
    KamusHelper kamusHelper;
    ArrayList<Indonesia> kamusData;


    public InEnFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_in_en, container, false);

        recyclerView = view.findViewById(R.id.rv_in_en);
        kamusHelper = new KamusHelper(getActivity());

        kamusHelper.open();

        kamusData = kamusHelper.getAllIndonesiaData();

        kamusHelper.close();

        adapter = new IndonesiaAdapter(getActivity(), kamusData);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.setOnKataClickCallback(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.bar_menu, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getSearchResult(s);
                return false;
            }
        });
    }

    @Override
    public void onKataClicked(Indonesia indonesia) {
        Intent intent = new Intent(getActivity(), DetailIndonesiaActivity.class);
        intent.putExtra(DETAIL_KATA, indonesia);
        startActivity(intent);
    }

    private void getSearchResult(String text){
        kamusData.clear();

        kamusHelper = new KamusHelper(getActivity());

        kamusHelper.open();

        Indonesia indonesia = null;

        Cursor cursor = kamusHelper.searchResultIndonesia(text);

        while (cursor.moveToNext()){

            indonesia = new Indonesia();
            indonesia.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
            indonesia.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
            indonesia.setTerjemah(cursor.getString(cursor.getColumnIndexOrThrow(TERJEMAH)));

            kamusData.add(indonesia);
        }

        kamusHelper.close();

        recyclerView.setAdapter(adapter);
    }
}
