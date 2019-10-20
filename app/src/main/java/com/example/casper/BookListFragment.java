package com.example.casper;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookListFragment extends Fragment {

    ListViewMainActivity.BookAdapter bookAdapter;

    public BookListFragment(ListViewMainActivity.BookAdapter bookAdapter) {
        this.bookAdapter = bookAdapter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        ListView listViewBooks = view.findViewById(R.id.list_view_books);
        listViewBooks.setAdapter(bookAdapter);

        this.registerForContextMenu(listViewBooks);
        return view;
    }

}
