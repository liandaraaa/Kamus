package com.made.lianda.kamus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.made.lianda.kamus.model.English;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailEnglishActivity extends AppCompatActivity {


    @BindView(R.id.tv_detail_word)
    TextView tvDetailWord;
    @BindView(R.id.tv_detail_translate)
    TextView tvDetailTranslate;

    public static final String DETAIL_WORD = "word";
    private English english;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_english);
        ButterKnife.bind(this);
        setTitle(getString(R.string.translate));

        english = getIntent().getParcelableExtra(DETAIL_WORD);

        tvDetailWord.setText(english.getWord());
        tvDetailTranslate.setText(english.getTranslate());
    }
}
