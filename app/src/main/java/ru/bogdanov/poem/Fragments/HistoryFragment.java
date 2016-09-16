package ru.bogdanov.poem.Fragments;


import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

import ru.bogdanov.poem.DB.DBHelper;
import ru.bogdanov.poem.R;
import ru.bogdanov.poem.Storage;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
ArrayList<String> poemList;
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    ListView listView;
    ArrayAdapter<String> adapter;
    Button buttonAddPoem;
    LinearLayout linearLayoutAttention;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        dbHelper=new DBHelper(getActivity());
        sqLiteDatabase=dbHelper.getReadableDatabase();
        buttonAddPoem=(Button) getActivity().findViewById(R.id.buttonAddPoem);
        buttonAddPoem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContentLayout,new WelcomeFragment())
                        .commit();
                TabHost tabHost=(TabHost) getActivity().findViewById(R.id.tabHost);
                tabHost.setCurrentTab(2);
            }
        });

        fiilListView();
    }

    private void fiilListView() {
        poemList=new ArrayList<>(dbHelper.getPoemArray(sqLiteDatabase));
        linearLayoutAttention=(LinearLayout) getActivity().findViewById(R.id.linearLayoutAttention);
        if (poemList.size()!=0){
            linearLayoutAttention.setVisibility(View.INVISIBLE);
        listView=(ListView) getActivity().findViewById(R.id.listView);
        adapter=new ArrayAdapter<String>(getActivity(),R.layout.item_poem,R.id.textViewItem,poemList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView=(TextView) view.findViewById(R.id.textViewItem);
                String poemText=textView.getText().toString();
                Storage.setPoemText(poemText);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContentLayout,new PoemFragment())
                        .commit();
                TabHost tabHost=(TabHost) getActivity().findViewById(R.id.tabHost);
                tabHost.setCurrentTab(1);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView=(TextView) view.findViewById(R.id.textViewItem);
                final String poemText=textView.getText().toString();
                AlertDialog alertDialog=new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.delete_poem)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            dbHelper.deletePoem(sqLiteDatabase,poemText);
                                fiilListView();
                            }
                        })
                        .setNegativeButton(R.string.back,null)
                        .show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.blackText));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.blackText));
                return true;
            }
        });
    }else linearLayoutAttention.setVisibility(View.VISIBLE);
    }
}
