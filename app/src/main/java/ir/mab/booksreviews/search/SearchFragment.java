package ir.mab.booksreviews.search;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.search.Adapter.SearchAdapter;
import ir.mab.booksreviews.search.model.Book;
import ir.mab.booksreviews.search.model.SearchResponse;
import ir.mab.booksreviews.search.model.Title;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements  SearchContract.View{

    private SearchContract.Presenter presenter;
    private SearchContract.FetchRemoteData fetchRemoteData;
    private SearchPresenter searchPresenter;
    private List<Book> books = new ArrayList<>();

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private EditText searchBox;
    private TextView searchCount;
    private ImageView searchBg;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        final SearchPresenter searchPresenter = new SearchPresenter(this);

        searchAdapter = new SearchAdapter(books,getContext());
        initRecyclerView();

        searchCount.setText("0");

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 3) {
                    progressBar.setVisibility(View.VISIBLE);
                    searchBg.setVisibility(View.GONE);
                    Title title = new Title();
                    title.setTitle(s.toString());
                    fetchRemoteData = new SearchFetchRemoteData(title);
                    searchPresenter.setFetchRemoteData(fetchRemoteData);
                    presenter = searchPresenter;
                    presenter.requestDataFromServer();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.booksList);
        searchBox = view.findViewById(R.id.searchBox);
        searchCount = view.findViewById(R.id.searchCount);
        searchBg = view.findViewById(R.id.searchBg);
        progressBar = view.findViewById(R.id.progress);
    }


    @Override
    public void setData(SearchResponse searchResponse) {
        this.books.clear();
        this.books = searchResponse.getBooks();

        searchAdapter.setBooks(this.books);
        searchAdapter.notifyDataSetChanged();

        if (books.size() > 0) {
            searchBg.setVisibility(View.GONE);
            progressBar.setVisibility(View.INVISIBLE);
            searchCount.setText(String.valueOf(books.size()));
        }
        else {
            searchBg.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            searchCount.setText("0");
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(searchAdapter);
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
