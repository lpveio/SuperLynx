package br.cta.ipev.h135;

import android.content.res.Resources;

import br.cta.ipev.commom.acquisition.iAlert;
import br.cta.ipev.h135.Conversion;
import br.cta.isad.iCounts2UE;
import br.cta.isad.EV;

public class CoefsSAD1 extends CoefsSAD implements iCounts2UE {
    public static final String TAG = "CoefsSAD1";
    public static final int TOP_HI = 134 - OFFSET_IENA;
    public static final int TOP_LO = 135 - OFFSET_IENA;

    public static final int TCG102C_0_J3_HI_TI = 147 - OFFSET_IENA;
    public static final int TCG102C_0_J3_LO_TI = 148 - OFFSET_IENA;
    public static final int TCG102C_0_J3_MI_TI=  149 - OFFSET_IENA;

    public static final int N1_dh = 86 - OFFSET_IENA;
    public static final int N1_dl = 87 - OFFSET_IENA;
    public static final int N2_dh = 88  - OFFSET_IENA;
    public static final int N2_dl = 89 - OFFSET_IENA;

    public static final int TOT1_dh = 70 - OFFSET_IENA;

    public static final int TOT1_dl = 70 - OFFSET_IENA;
    public static final int TOT2_dh = 71 - OFFSET_IENA;
    public static final int TOT2_dl = 72 - OFFSET_IENA;
    public static final int TRQ1_dl = 73 - OFFSET_IENA;
    public static final int TRQ1_dh = 74 - OFFSET_IENA;
    public static final int TRQ2_dl = 75  - OFFSET_IENA;
    public static final int TQR2_dh = 76 - OFFSET_IENA;
    public static final int FLI_dl = 77 - OFFSET_IENA;

    public static final int FLI_dh = 78 - OFFSET_IENA;
    public static final int RADALT_dl = 79 - OFFSET_IENA;
    public static final int RADALT_dh = 80 - OFFSET_IENA;
    public static final int PSI_dl = 81 - OFFSET_IENA;
    public static final int PSI_dh = 82 - OFFSET_IENA;
    public static final int DDM_dl = 83  - OFFSET_IENA;
    public static final int DDM_dh = 84 - OFFSET_IENA;
    public static final int DDL_dl = 85 - OFFSET_IENA;

    public static final int DDL_dh = 31 - OFFSET_IENA;
    public static final int DDN_dl = 32 - OFFSET_IENA;
    public static final int DDN_dh = 33 - OFFSET_IENA;
    public static final int DDC_dl = 34 - OFFSET_IENA;
    public static final int DDC_dh = 35 - OFFSET_IENA;
    public static final int ZP_dl = 36 - OFFSET_IENA;
    public static final int ZP_dh = 37 - OFFSET_IENA;
    public static final int VZ_dl = 38 - OFFSET_IENA;
    public static final int VZ_dh = 39 - OFFSET_IENA;

    public static final int OAT_dh = 37 - OFFSET_IENA;

    public static final int OAT_dl = 37 - OFFSET_IENA;

    public static final int GS_dh = 37 - OFFSET_IENA;

    public static final int GS_dl = 37 - OFFSET_IENA;

    public static final int THETA_dh = 37 - OFFSET_IENA;

    public static final int THETA_dl = 37 - OFFSET_IENA;

    public static final int PHI_dh = 37 - OFFSET_IENA;

    public static final int PHI_dl = 37 - OFFSET_IENA;



    public static final int AHRU_P_dl = 38 - OFFSET_IENA;
    public static final int AHRU_Q_dh = 43 - OFFSET_IENA;
    public static final int AHRU_Q_dl = 44 - OFFSET_IENA;
    public static final int AHRU_R_dh = 45 - OFFSET_IENA;
    public static final int AHRU_R_dl = 46 - OFFSET_IENA;




    public static final int RSA1A = 121 - OFFSET_IENA;
    public static final int RSA1B = 122 - OFFSET_IENA;
    public static final int RSA1C = 123 - OFFSET_IENA;

    public static final int RSA2A = 124 - OFFSET_IENA;
    public static final int RSA2B = 125 - OFFSET_IENA;
    public static final int RSA2C = 126 - OFFSET_IENA;

    public static final int RHA1 = 119 - OFFSET_IENA;
    public static final int RHA2 = 120 - OFFSET_IENA;

    public static final int RH2P1 = 57 - OFFSET_IENA;
    public static final int RH2P2 = 56 - OFFSET_IENA;

    public static final int AILERON_CMD_POS = 55 - OFFSET_IENA;
    public static final int AILERON_DIR_POS = 117 - OFFSET_IENA;
    public static final int AILERON_ESQ_POS = 118 - OFFSET_IENA;

    public static final int TI = 133 - OFFSET_IENA;

    private int bitSinal = 1;

    @Override
    public double[] convert(char[] counts) {
        double[] result = new double[Index.values().length];
        double[] CV;
        int arincC105 = 0;
        int numBits = 0;

        //TEMPO
        result[Index.TEMPO.ordinal()] = EV.sadtime2secs(0xffff & counts[TCG102C_0_J3_HI_TI], 0xffff & counts[TCG102C_0_J3_LO_TI], 0xffff & counts[TCG102C_0_J3_MI_TI]);

        // TOP
        result[Index.TOP.ordinal()] = counts[TOP_LO];



        //RSA1 [RSA1A, RSA1B, RSA1C]
        CV = new double[]{-7.268936E-14, 6.665517E-09, 6.119901E-01, -2.065888E04};
        result[Index.RSA1A.ordinal()] = EV.polyval(CV,counts[RSA1A]);

        CV = new double[]{-6.413501E-14, 6.012330E-09, 6.062781E-01, -2.046630E04};
        result[Index.RSA1B.ordinal()] = EV.polyval(CV,counts[RSA1B]);

        CV = new double[]{-5.987127E-14, 5.666792E-09, 6.119705E-01, -2.065849E04};
        result[Index.RSA1C.ordinal()] = EV.polyval(CV,counts[RSA1C]);

        result[Index.RSA1.ordinal()] = EV.sigmaVM(E, v,
                result[Index.RSA1A.ordinal()],
                result[Index.RSA1B.ordinal()],
                result[Index.RSA1C.ordinal()]);

        //RSA2 [RSA2A, RSA2B, RSA2C]
        CV = new double[]{0.612154, -20660.39};
        result[Index.RSA2A.ordinal()] = EV.polyval(CV,counts[RSA2A]);

        CV = new double[]{0.606334, -20466.15};
        result[Index.RSA2B.ordinal()] = EV.polyval(CV,counts[RSA2B]) ;

        CV = new double[]{0.612079, -20662.46};
        result[Index.RSA2C.ordinal()] = EV.polyval(CV,counts[RSA2C]) ;

        result[Index.RSA2.ordinal()] = EV.sigmaVM(E, v,
                result[Index.RSA2A.ordinal()],
                result[Index.RSA2B.ordinal()],
                result[Index.RSA2C.ordinal()]);

        //RHA1
        CV = new double[]{-1.439713E-13, 1.359134E-08, 6.249419E-01, -2.109423E04};
        result[Index.RHA1A.ordinal()] = EV.polyval(CV,counts[RHA1]);
        result[Index.RHA1.ordinal()] = EV.sigmaVM(E, result[Index.RHA1A.ordinal()]);


        //RHA2
        CV = new double[]{-5.415286E-14, 4.623532E-09, 6.251784E-01, -2.109411E04};
        result[Index.RHA2A.ordinal()] = EV.polyval(CV,counts[RHA2]);
        result[Index.RHA2.ordinal()] = EV.sigmaVM(E,result[Index.RHA2A.ordinal()]);

        //RH2P1
        CV = new double[]{8.64363740786847e-16, -2.27824210444690e-10, 2.40124945139026e-05, -1.26508786233342, 33315.9440841980, -350851772.582865};
        result[Index.RH2P1.ordinal()] = EV.polyval(CV,counts[RH2P1]);

        //RH2P2
        CV = new double[]{-1.431987E-13, 1.438919E-08, 6.548887E-01, -2.209222E04};
        result[Index.RH2P2A.ordinal()] = EV.polyval(CV,counts[RH2P2]) ;
        result[Index.RH2P2.ordinal()] = EV.sigmaVM(E, result[Index.RH2P2A.ordinal()]);



        arincC105 = Conversion.extrairArinc429First(mergeWords(counts[AHRU_Q_dh], counts[AHRU_R_dh]));

        //Q
        arincC105 = EV.extrairArincC105(mergeWords(counts[AHRU_Q_dh], counts[AHRU_Q_dl]));
        arincC105 = arincC105 >> (EV.ARINCC105DataFieldSize - numBits);
        result[Index.Q.ordinal()] = EV.TwosComplement(arincC105,numBits) * 0.015625;

        //R
        arincC105 = EV.extrairArincC105(mergeWords(counts[AHRU_R_dh], counts[AHRU_R_dl]));
        arincC105 = arincC105 >> (EV.ARINCC105DataFieldSize - numBits);
        result[Index.R.ordinal()] = EV.TwosComplement(arincC105,numBits) * 0.015625;

        //TE
        CV = new double[]{0.0193786621, -270};
        result[Index.TE.ordinal()] = 0;

        //TD
        result[Index.TD.ordinal()] = 0;

        //DDL
        CV = new double[]{-7.172171E-17, 5.535307E-12, -1.267426E-07, -6.009795E-03, 1.315509E02};
        result[Index.DDL.ordinal()] = EV.polyval(CV,counts[AILERON_CMD_POS]);

        //DL_1
        CV = new double[]{-7.425171E-18, 9.288115E-13, -3.169400E-08, 3.839718E-03, -1.120723E02};
        result[Index.DL_1.ordinal()] = EV.polyval(CV,counts[AILERON_ESQ_POS]);

        //DL_2
        CV = new double[]{-4.121487E-18, 4.958417E-13, -1.569907E-08, -3.576116E-03, 1.104174E02};
        result[Index.DL_2.ordinal()] = EV.polyval(CV,counts[AILERON_DIR_POS]);

        //TI
        CV = new double[]{2.337699E-18, -3.546667E-13, 1.935331E-08, 1.072171E-03, -4.639768E01};
        result[Index.TI.ordinal()] = EV.polyval(CV,counts[TI]);

        this._currentCVT = result;
        return this._currentCVT;
    }



}
