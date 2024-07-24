package br.cta.ipev.h135.telas;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.cta.ipev.h135.AppManager;
import br.cta.ipev.h135.Index;
import br.cta.ipev.h135.databinding.ActivityTelaTresBinding;
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

                    binding.txtJX.setValue(CVT[Index.JX.ordinal()]);
                    binding.txtJY.setValue(CVT[Index.JY.ordinal()]);
                    binding.txtJZ.setValue(CVT[Index.JZ.ordinal()]);

                    binding.txtP.setValue(CVT[Index.P.ordinal()]);
                    binding.txtQ.setValue(CVT[Index.Q.ordinal()]);
                    binding.txtR.setValue(CVT[Index.R.ordinal()]);

                    binding.txtTheta.setValue(CVT[Index.THETA.ordinal()]);
                    binding.txtPHI.setValue(CVT[Index.PHI.ordinal()]);
                    binding.txtPSI.setValue(CVT[Index.PSI.ordinal()]);

                    binding.txtHeading.setValue(CVT[Index.HEADING.ordinal()]);
                    binding.txtGama.setValue(CVT[Index.GAMA.ordinal()]);

                    binding.txtTI.setValue(CVT[Index.TI.ordinal()]);
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
