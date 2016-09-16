package ru.bogdanov.poem.Fragments;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import ru.bogdanov.poem.DB.DBHelper;
import ru.bogdanov.poem.R;
import ru.bogdanov.poem.Storage;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {
Button buttonPaste;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        buttonPaste=(Button) getActivity().findViewById(R.id.buttonPastePoem);
        buttonPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasteText();
            }
        });
    }

    private void pasteText(){
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard.hasPrimaryClip()) {
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            String pasteData = item.getText().toString();
            pasteData = pasteData.trim();
            pasteData = pasteData.replaceAll("\n", " \n ");


            DBHelper dbHelper = new DBHelper(getActivity());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            if (dbHelper.getPoemArray(db).contains(pasteData))
                Toast.makeText(getActivity(),"df",Toast.LENGTH_SHORT).show();
            else {
                dbHelper.addToDB(db, pasteData);
                Storage.setPoemText(pasteData);

                TabHost tabHost = (TabHost) getActivity().findViewById(R.id.tabHost);
                tabHost.setCurrentTab(1);

                this.getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContentLayout, new PoemFragment())
                        .commit();
            }
        }else Toast.makeText(getActivity(),getString(R.string.copy_text),Toast.LENGTH_SHORT).show();
    }
}
