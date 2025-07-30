package br.cta.ipev.superlynx.telas;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.cta.ipev.superlynx.AppManager;
import br.cta.ipev.superlynx.R;
import br.cta.ipev.superlynx.databinding.ActivityTelaDoisBinding;
import br.cta.isad.Display;

public class TelaDois extends AppCompatActivity implements Display {
    private ActivityTelaDoisBinding binding;
    private boolean isTablet;


    @Override
    public void update(double[] CVT) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {


                } catch (Exception e) {
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

        TextWatcher autoCalcWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calcularTudo();  // dispara sempre que algum campo muda
            }
        };

        binding.textEditPeso.addTextChangedListener(autoCalcWatcher);
        binding.textEditFuel.addTextChangedListener(autoCalcWatcher);
        binding.textEditAtt.addTextChangedListener(autoCalcWatcher);
        binding.textEditTemp.addTextChangedListener(autoCalcWatcher);
        binding.textEditAlvo.addTextChangedListener(autoCalcWatcher);

    }

    private void calcularTudo() {
        try {
            // 1. Soma Peso e Fuel
            double peso = parseDouble(binding.textEditPeso.getText().toString());
            double fuel = parseDouble(binding.textEditFuel.getText().toString());
            double somaPeso = peso + fuel;

            // 2. Cálculo do Pa a partir da altitude
            double altitude = parseDouble(binding.textEditAtt.getText().toString());
            double paValor = calcularPa(altitude);

            // 3. Cálculo do K a partir da temperatura
            double temp = parseDouble(binding.textEditTemp.getText().toString());
            double kValor = calcularK(temp);

            // 4. Cálculo de Rho (fórmula temporária)
            double R = 287.053;
            double rho = calcularRho(paValor, kValor, R);

            // 5. Cálculo de Sigma (fórmula temporária)
            double sigma = calcularSigma(rho);
            binding.textEditSigma.setText(String.format(Locale.US, "%.6f", sigma));

            // 6. Cálculo de peso corrigido
            double pesoCorrigido = somaPeso / sigma;
            binding.textEditPesoCorrigido.setText(String.format(Locale.US, "%.0f", pesoCorrigido));

            // 7. Habilitar gráfico (exemplo simples de atualização)
            double pesoAlvo = parseDouble(binding.textEditAlvo.getText().toString());
            adicionarPontoAoGrafico(pesoAlvo, pesoCorrigido);

        } catch (Exception e) {
            Toast.makeText(this, "Erro nos dados de entrada.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void adicionarPontoAoGrafico(double pesoAlvo_, double pesoCorrigido_) {
        ArrayList<Entry> entradasA = new ArrayList<>();
        ArrayList<Entry> entradasB = new ArrayList<>();
        ArrayList<Entry> entradaLimiteSuperior1 = new ArrayList<>();
        ArrayList<Entry> entradaLimiteInferior1 = new ArrayList<>();

        ArrayList<Entry> entradaLimiteSuperior2 = new ArrayList<>();
        ArrayList<Entry> entradaLimiteInferior2 = new ArrayList<>();

        float pesoAlvo = (float) pesoAlvo_;
        float pesoCorrigido = (float) pesoCorrigido_;

        // Linha fixa no ponto (0, 5300)
        entradasA.add(new Entry(-1f, pesoAlvo));
        entradasA.add(new Entry(0f, pesoAlvo));
        entradasA.add(new Entry(1f, pesoAlvo));

        // Ponto dinâmico (0, valor B)
        entradasB.add(new Entry(0f, pesoCorrigido));

        float limiteSuperior1 = pesoAlvo * 1.01f;
        float limiteInferior1 = pesoAlvo * 0.99f;

        float limiteSuperior2 = pesoAlvo * 1.02f;
        float limiteInferior2 = pesoAlvo * 0.98f;


        //Limite de 1%
        entradaLimiteSuperior1.add(new Entry(-1f, limiteSuperior1));
        entradaLimiteSuperior1.add(new Entry(1f, limiteSuperior1));

        entradaLimiteInferior1.add(new Entry(-1f, limiteInferior1));
        entradaLimiteInferior1.add(new Entry(1f, limiteInferior1));

        //Limite de 2%
        entradaLimiteSuperior2.add(new Entry(-1f, limiteSuperior2));
        entradaLimiteSuperior2.add(new Entry(1f, limiteSuperior2));

        entradaLimiteInferior2.add(new Entry(-1f, limiteInferior2));
        entradaLimiteInferior2.add(new Entry(1f, limiteInferior2));

        // DataSet da linha A
        LineDataSet dataSetA = new LineDataSet(entradasA, "Peso Alvo");
        dataSetA.setColor(Color.GREEN);
        dataSetA.setLineWidth(2f);
        dataSetA.setDrawCircles(false);
        dataSetA.setDrawValues(false);

        // DataSet da linha B
        LineDataSet dataSetB = new LineDataSet(entradasB, "Peso Corrigido");
        dataSetB.setColor(Color.BLUE);
        dataSetB.setCircleColor(Color.BLUE);
        dataSetB.setCircleRadius(7f);
        dataSetB.setDrawValues(true);
        dataSetB.setLineWidth(1f); // sem linha ligando

        // DataSet da linha de limite superior de 1%
        LineDataSet dataSetLimiteSup1 = new LineDataSet(entradaLimiteSuperior1, "+1% Limite");
        dataSetLimiteSup1.setColor(Color.MAGENTA);
        dataSetLimiteSup1.setLineWidth(1.5f);
        dataSetLimiteSup1.setDrawCircles(false);
        dataSetLimiteSup1.setDrawValues(true);
        dataSetLimiteSup1.enableDashedLine(10f, 10f, 0f);
        dataSetLimiteSup1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 10f}, 0));

        // DataSet da linha de limite inferior de 1%
        LineDataSet dataSetLimiteInf1 = new LineDataSet(entradaLimiteInferior1, "-1% Limite");
        dataSetLimiteInf1.setColor(Color.MAGENTA);
        dataSetLimiteInf1.setLineWidth(1.5f);
        dataSetLimiteInf1.setDrawCircles(false);
        dataSetLimiteInf1.setDrawValues(true);

        dataSetLimiteInf1.enableDashedLine(10f, 10f, 0f);
        dataSetLimiteInf1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 10f}, 0));


        // DataSet da linha de limite superior de 2%
        LineDataSet dataSetLimiteSup2 = new LineDataSet(entradaLimiteSuperior2, "+2% Limite");
        dataSetLimiteSup2.setColor(Color.RED);
        dataSetLimiteSup2.setLineWidth(1.5f);
        dataSetLimiteSup2.setDrawCircles(false);
        dataSetLimiteSup2.setDrawValues(true);
        dataSetLimiteSup2.enableDashedLine(10f, 10f, 0f);
        dataSetLimiteSup2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 10f}, 0));

        // DataSet da linha de limite inferior de 2%
        LineDataSet dataSetLimiteInf2 = new LineDataSet(entradaLimiteInferior2, "-2% Limite");
        dataSetLimiteInf2.setColor(Color.RED);
        dataSetLimiteInf2.setLineWidth(1.5f);
        dataSetLimiteInf2.setDrawCircles(false);
        dataSetLimiteInf2.setDrawValues(true);

        dataSetLimiteInf2.enableDashedLine(10f, 10f, 0f);
        dataSetLimiteInf2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 10f}, 0));

        LineData lineData = new LineData(dataSetA, dataSetB, dataSetLimiteSup1, dataSetLimiteInf1, dataSetLimiteSup2, dataSetLimiteInf2);
        binding.lineChart.setData(lineData);
        binding.lineChart.getDescription().setEnabled(false);

        // Eixo X fixo no ponto 0
        XAxis xAxis = binding.lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(-1f);
        xAxis.setAxisMaximum(1f);
        xAxis.setDrawLabels(false);

        // Eixo Y com limite de +-200 em relação a A
        YAxis yAxis = binding.lineChart.getAxisLeft();
        yAxis.setAxisMinimum(pesoAlvo - 150);
        yAxis.setAxisMaximum(pesoAlvo + 150);
        binding.lineChart.getAxisRight().setEnabled(false);
        binding.lineChart.setTouchEnabled(false);
        binding.lineChart.animateY(1000);
        binding.lineChart.invalidate(); // Atualiza
    }

    private double parseDouble(String text) {
        if (text == null || text.trim().isEmpty()) return 0.0;
        return Double.parseDouble(text.trim());
    }

    // 2. Fórmula de Pa (substitua conforme necessário)
    private double calcularPa(double altitudeFt) {
        double base = 1 - 6.87559e-6 * altitudeFt;
        return 1013.25 * Math.pow(base, 5.25588);
    }

    // 3. Fórmula de K (substitua conforme necessário)
    private double calcularK(double tempCelsius) {
        // Exemplo: K = temperatura em Kelvin
        return tempCelsius + 273.15;
    }

    private double calcularRho(double pa , double tempKelvin, double r) {
        return pa * 100 / (r * tempKelvin);

    }

    // 4. Fórmula de Sigma (exemplo: Pa / K)
    private double calcularSigma(double rho) {
        return rho / 1.225;
    }

    private void setLayout(){
        binding = ActivityTelaDoisBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void init(){
        ((AppManager)getApplicationContext()).addDisplay(this);
        this.isTablet = getResources().getBoolean(br.cta.ipev.commom.R.bool.isTablet);
    }
}
