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
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
        String pasteData = item.getText().toString();
        pasteData=pasteData.trim();

        Storage.setPoemText(pasteData);
        DBHelper dbHelper=new DBHelper(getActivity());
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        dbHelper.addToDB(db,pasteData);

        this.getFragmentManager().beginTransaction()
                .replace(R.id.fragmentContentLayout,new PoemFragment())
                .addToBackStack(null)
                .commit();
    }
}
