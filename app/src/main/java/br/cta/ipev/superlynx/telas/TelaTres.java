package br.cta.ipev.superlynx.telas;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.cta.ipev.superlynx.AppManager;
import br.cta.ipev.superlynx.Index;
import br.cta.ipev.superlynx.databinding.ActivityTelaTresBinding;
import br.cta.isad.Display;
import br.cta.misc.Convertions;

public class TelaTres extends AppCompatActivity implements Display {
    private ActivityTelaTresBinding binding;

    @Override
    public void update(double[] CVT) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    binding.txtTempo.setText(Convertions.sec2dhms(CVT[Index.TEMPO.ordinal()]));
                    binding.txtTOP.setValue(CVT[Index.TOP.ordinal()]);

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        init();
    }

    private void setLayout(){
        binding = ActivityTelaTresBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
    }

    private void init(){
        ((AppManager)getApplicationContext()).addDisplay(this);
    }


}
