package br.cta.ipev.superlynx.telas;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import br.cta.ipev.superlynx.AppManager;
import br.cta.ipev.superlynx.Index;
import br.cta.ipev.superlynx.databinding.ActivityComandoVooBinding;
import br.cta.isad.Display;
import br.cta.misc.Convertions;

public class Comando_Voo extends AppCompatActivity implements Display {

    private ActivityComandoVooBinding binding;
    private double lastTopValue = -1; // valor inicial impossível

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

                    binding.txtTempo.setText(Convertions.sec2dhms(CVT[Index.TEMPO.ordinal()]));

                    double currentTop = CVT[Index.TOP.ordinal()];
                    if (currentTop != lastTopValue) {
                        lastTopValue = currentTop;
                        appendTopLogToFile(currentTop);
                    }
                    binding.txtTOP.setValue(currentTop);

                    binding.chartDeltas.setDdc(CVT[Index.DDC.ordinal()]);
                    binding.chartDeltas.setDdl(CVT[Index.DDL.ordinal()]);
                    binding.chartDeltas.setDdm(CVT[Index.DDM.ordinal()]);
                    binding.chartDeltas.setDdn(CVT[Index.DDN.ordinal()]);
                    binding.txtDDC.setValue(CVT[Index.DDC.ordinal()]);
                    binding.txtDDL.setValue(CVT[Index.DDL.ordinal()]);
                    binding.txtDDM.setValue(CVT[Index.DDM.ordinal()]);
                    binding.txtDDN.setValue(CVT[Index.DDN.ordinal()]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setLayout() {
        binding = ActivityComandoVooBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void init() {
        AppManager manager = ((AppManager) getApplicationContext());
        manager.addDisplay(this);
    }

    private void appendTopLogToFile(double topValue) {
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy | HH:mm:ss", Locale.getDefault()).format(new Date());
        String line = "TOP: " + topValue + " | Data: " + timeStamp + "\n";

        try {
            File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(downloadDir, "TOP.txt");

            FileWriter writer = new FileWriter(file, true); // true = append
            writer.append(line);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}