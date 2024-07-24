package br.cta.ipev.h135.telas;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.cta.ipev.commom.compat.instruments.SingleDisplay;
import br.cta.ipev.h135.Alerta;
import br.cta.ipev.h135.AppManager;

import br.cta.ipev.h135.Index;
import br.cta.ipev.h135.R;
import br.cta.ipev.h135.databinding.ActivityComandoVooBinding;

import br.cta.isad.Display;
import br.cta.misc.Convertions;

public class Comando_Voo extends AppCompatActivity implements Display {

    private ActivityComandoVooBinding binding;
    private Alerta alertas;

    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout();
        init();
    }

    @Override
    public void update(double[] CVT) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {


                    //binding.odoRSA1.setValue(CVT[Index.RSA1.ordinal()]);
                    //binding.odoDDL.setValue(CVT[Index.DDL.ordinal()]);
                    binding.chartDeltas.setDdc(10);
                    binding.chartDeltas.setDdl(30);
                    binding.chartDeltas.setDdm(5);
                    binding.chartDeltas.setDdn(60);


                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void setLayout(){
        binding = ActivityComandoVooBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
    }

    private void init(){
        AppManager manager =  ((AppManager)getApplicationContext());
        manager.addDisplay(this);
        alertas = new Alerta(getApplicationContext());



        binding.seekBar.setMax(1000);
        binding.seekBar.setMin(800);
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                binding.txtALERTA.setText(String.valueOf(i));
                binding.txtALERTA.setAlertTOT(i);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.chartDeltas.setDdm(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.chartDeltas.setDdl(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.chartDeltas.setDdn(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }

}
