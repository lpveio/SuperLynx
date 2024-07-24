package br.cta.ipev.h135.telas;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.cta.ipev.h135.AppManager;
import br.cta.ipev.h135.Index;
import br.cta.ipev.h135.databinding.ActivityTelaDoisBinding;
import br.cta.isad.Display;
import br.cta.misc.Convertions;

public class TelaDois extends AppCompatActivity implements Display {
    private ActivityTelaDoisBinding binding;
    private boolean isTablet;
    @Override
    public void update(double[] CVT) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    binding.txtTempo.setText(Convertions.sec2dhms(CVT[Index.TEMPO.ordinal()]));
                    binding.txtTOP.setValue(CVT[Index.TOP.ordinal()]);

                    if(isTablet) {
                        binding.txtTQ1.setValue(CVT[Index.TQ1.ordinal()]);
                        binding.txtTQ2.setValue(CVT[Index.TQ2.ordinal()]);

                        binding.txtNP1.setValue(CVT[Index.NP1.ordinal()]);
                        binding.txtNP2.setValue(CVT[Index.NP2.ordinal()]);

                        binding.txtRAW1.setValue(CVT[Index.RAW1.ordinal()]);
                        binding.txtRAW2.setValue(CVT[Index.RAW2.ordinal()]);

                        binding.txtTRM1.setValue(CVT[Index.TRM1.ordinal()]);
                        binding.txtTRM2.setValue(CVT[Index.TRM2.ordinal()]);

                        binding.txtRFF.setValue(CVT[Index.RFF.ordinal()]);
                        binding.txtLFF.setValue(CVT[Index.LFF.ordinal()]);

                    }

                }
                catch (Exception e){
                    Log.e("TAG", "run: ", e);
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
        binding = ActivityTelaDoisBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
    }

    private void init(){
        ((AppManager)getApplicationContext()).addDisplay(this);
        this.isTablet = getResources().getBoolean(br.cta.ipev.commom.R.bool.isTablet);
    }
}
