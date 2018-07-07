package com.made.lianda.kamus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.made.lianda.kamus.model.Indonesia;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailIndonesiaActivity extends AppCompatActivity {


    public static final String DETAIL_KATA = "kata";
    private Indonesia indonesia;

    @BindView(R.id.tv_detail_kata)
    TextView tvDetailKata;
    @BindView(R.id.tv_detail_terjemah)
    TextView tvDetailTerjemah;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_indonesia);
        ButterKnife.bind(this);
        setTitle(getString(R.string.terjemah));

        indonesia = getIntent().getParcelableExtra(DETAIL_KATA);

        tvDetailKata.setText(indonesia.getKata());
        tvDetailTerjemah.setText(indonesia.getTerjemah());

    }
}
