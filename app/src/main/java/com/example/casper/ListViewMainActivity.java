package com.example.casper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.casper.data.BookFragmentAdapter;
import com.example.casper.data.BookSaver;
import com.example.casper.data.model.Book;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ListViewMainActivity extends AppCompatActivity {

    public static final int CONTEXT_MENU_DELETE = 1;
    public static final int CONTEXT_MENU_NEW = CONTEXT_MENU_DELETE + 1;
    public static final int CONTEXT_MENU_UPDATE = CONTEXT_MENU_NEW + 1;
    public static final int CONTEXT_MENU_ABOUT = CONTEXT_MENU_UPDATE + 1;
    public static final int REQUEST_CODE_NEW_BOOK = 901;
    public static final int REQUEST_CODE_UPDATE_BOOK = 902;

    private List<Book> listBooks = new ArrayList<>();
    BookSaver bookSaver;
    BookAdapter bookAdapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookSaver.save();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_main);

        bookSaver = new BookSaver(this);
        listBooks = bookSaver.load();
        if (listBooks.size() == 0)
            init();
        bookAdapter = new BookAdapter(ListViewMainActivity.this, R.layout.list_view_item_book, getListBooks());

        BookFragmentAdapter myPageAdapter = new BookFragmentAdapter(getSupportFragmentManager());

        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new BookListFragment(bookAdapter));
        datas.add(new WebViewFragment());
        myPageAdapter.setData(datas);

        ArrayList<String> titles = new ArrayList<String>();
        titles.add("图书");
        titles.add("新闻");
        myPageAdapter.setTitles(titles);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(myPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == findViewById(R.id.list_view_books)) {
            //获取适配器
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //设置标题
            menu.setHeaderTitle(listBooks.get(info.position).getTitle());
            //设置内容 参数1为分组，参数2对应条目的id，参数3是指排列顺序，默认排列即可
            menu.add(0, CONTEXT_MENU_DELETE, 0, "删除");
            menu.add(0, CONTEXT_MENU_NEW, 0, "添加");
            menu.add(0, CONTEXT_MENU_UPDATE, 0, "修改");
            menu.add(0, CONTEXT_MENU_ABOUT, 0, "关于...");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_NEW_BOOK:
                if (resultCode == RESULT_OK) {
                    String title = data.getStringExtra("title");
                    int insertPosition = data.getIntExtra("insert_position", 0);
                    double price = data.getDoubleExtra("price", 0);
                    getListBooks().add(insertPosition, new Book(title, price, R.drawable.book_no_name));
                    bookAdapter.notifyDataSetChanged();
                }
                break;
            case REQUEST_CODE_UPDATE_BOOK:
                if (resultCode == RESULT_OK) {
                    int insertPosition = data.getIntExtra("insert_position", 0);
                    Book bookAtPosition = getListBooks().get(insertPosition);

                    bookAtPosition.setTitle(data.getStringExtra("title"));
                    bookAtPosition.setPrice(data.getDoubleExtra("price", 0));
                    bookAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case CONTEXT_MENU_DELETE:
                final int removePosition = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                Dialog dialog = new AlertDialog.Builder(this)
                        .setTitle("删除图书？")
                        .setMessage("您确定要删除这条图书吗？")
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                listBooks.remove(removePosition);
                                bookAdapter.notifyDataSetChanged();
                                Toast.makeText(ListViewMainActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();
                dialog.show();
                break;
            case CONTEXT_MENU_NEW:
                Intent intent = new Intent(this, NewBookActivity.class);
                intent.putExtra("title", "无名书籍");
                intent.putExtra("price", 1);
                intent.putExtra("insert_position", ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
                startActivityForResult(intent, REQUEST_CODE_NEW_BOOK);
                break;
            case CONTEXT_MENU_UPDATE: {
                int position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                Intent intent2 = new Intent(this, NewBookActivity.class);
                intent2.putExtra("title", listBooks.get(position).getTitle());
                intent2.putExtra("price", listBooks.get(position).getPrice());
                intent2.putExtra("insert_position", position);
                startActivityForResult(intent2, REQUEST_CODE_UPDATE_BOOK);
            }
            break;
            case CONTEXT_MENU_ABOUT:
                Toast.makeText(ListViewMainActivity.this, "图书列表v1.0,coded by casper", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void init() {
        getListBooks().add(new Book("软件项目管理案例教程（第4版）", 0, R.drawable.book_2));
        getListBooks().add(new Book("创新工程实践", 0, R.drawable.book_no_name));
        getListBooks().add(new Book("信息安全数学基础（第2版）", 0, R.drawable.book_1));
    }

    public List<Book> getListBooks() {
        return listBooks;
    }

    class BookAdapter extends ArrayAdapter<Book> {

        private int resourceId;

        BookAdapter(Context context, int resource, List<Book> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Book book = getItem(position);//获取当前项的实例
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ((ImageView) view.findViewById(R.id.image_view_book_cover)).setImageResource(book.getCoverResourceId());
            ((TextView) view.findViewById(R.id.text_view_book_title)).setText(book.getTitle() + "," + book.getPrice() + "元");
            return view;
        }
    }
}
