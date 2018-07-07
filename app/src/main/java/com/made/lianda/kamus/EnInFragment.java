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

import com.made.lianda.kamus.adapter.EnglishAdapter;
import com.made.lianda.kamus.db.KamusHelper;
import com.made.lianda.kamus.model.English;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.made.lianda.kamus.db.DatabaseContract.EnglishColumns.TRANSLATE;
import static com.made.lianda.kamus.db.DatabaseContract.EnglishColumns.WORD;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnInFragment extends Fragment implements EnglishAdapter.OnEnglishClikcCallback {

    RecyclerView recyclerView;
    EnglishAdapter adapter;
    KamusHelper kamusHelper;
    ArrayList<English> kamusData;

    public EnInFragment() {
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
        View view = inflater.inflate(R.layout.fragment_en_in, container, false);

        recyclerView = view.findViewById(R.id.rv_en_in);
        kamusHelper = new KamusHelper(getActivity());

        kamusHelper.open();

        kamusData = kamusHelper.getAllEnglishData();

        kamusHelper.close();

        adapter = new EnglishAdapter(getActivity(), kamusData);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.setOnEnglishClikcCallback(this);

        return view;
    }

    @Override
    public void onEnglishClicked(English english) {
        Intent intent = new Intent(getActivity(), DetailEnglishActivity.class);
        intent.putExtra(DetailEnglishActivity.DETAIL_WORD, english);
        startActivity(intent);
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

    private void getSearchResult(String text){
        kamusData.clear();

        kamusHelper = new KamusHelper(getActivity());

        kamusHelper.open();

        English english = null;

        Cursor cursor = kamusHelper.searchResultEnglish(text);

        while (cursor.moveToNext()){

            english = new English();
            english.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
            english.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
            english.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(TRANSLATE)));

            kamusData.add(english);
        }

        kamusHelper.close();

        recyclerView.setAdapter(adapter);
    }

}
