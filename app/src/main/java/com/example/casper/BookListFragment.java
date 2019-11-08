package com.example.casper;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.casper.data.ShopLoader;
import com.example.casper.data.model.Book;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookListFragment extends Fragment {

    public static final int REQUEST_CODE_SCAN = 333;
    ListViewMainActivity.BookAdapter bookAdapter;

    public BookListFragment(ListViewMainActivity.BookAdapter bookAdapter) {
        this.bookAdapter = bookAdapter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                final String content = data.getStringExtra(Constant.CODED_CONTENT);
                Log.v("test", "扫描结果为：" + content);
                /*判定是图书的isbn*/
                if (content.startsWith("978") && content.length() == 13) {
                    final Handler handler = new Handler() {
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            bookAdapter.notifyDataSetChanged();
                        }
                    };
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ShopLoader shopLoader = new ShopLoader();
                            String jsonText = shopLoader.download("https://douban.uieee.com/v2/book/isbn/" + content);
                            try {
                                //这里的text就是上边获取到的数据，一个String.
                                JSONObject jsonObject = new JSONObject(jsonText);
                                String title = jsonObject.getString("title");
                                double price = jsonObject.getDouble("price");
                                bookAdapter.add(new Book(title, price, R.drawable.book_no_name));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            handler.sendEmptyMessage(1);
                        }
                    }).start();
                }

            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        ListView listViewBooks = view.findViewById(R.id.list_view_books);
        listViewBooks.setAdapter(bookAdapter);

        this.registerForContextMenu(listViewBooks);

        view.findViewById(R.id.floating_action_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
        return view;
    }

}
