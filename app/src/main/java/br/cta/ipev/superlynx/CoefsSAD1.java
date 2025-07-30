package br.cta.ipev.superlynx;

import br.cta.isad.iCounts2UE;
import br.cta.isad.EV;

public class CoefsSAD1 extends CoefsSAD implements iCounts2UE {

    public static final int TOP_HI = 27 - OFFSET_IENA;
    public static final int TOP_LO = 28 - OFFSET_IENA;

    public static final int TCG102C_0_J3_HI_TI = 147 - OFFSET_IENA;
    public static final int TCG102C_0_J3_LO_TI = 148 - OFFSET_IENA;
    public static final int TCG102C_0_J3_MI_TI=  149 - OFFSET_IENA;

    public static final int DDC = 12 - OFFSET_IENA;

    public static final int DDM = 14 - OFFSET_IENA;


    public static final int DDL = 16 - OFFSET_IENA;

    public static final int DDN = 18 - OFFSET_IENA;


    @Override
    public double[] convert(char[] counts) {
        double[] result = new double[Index.values().length];
        double[] CV;

        //TEMPO
        result[Index.TEMPO.ordinal()] = EV.sadtime2secs(0xffff & counts[TCG102C_0_J3_HI_TI], 0xffff & counts[TCG102C_0_J3_LO_TI], 0xffff & counts[TCG102C_0_J3_MI_TI]);
        // TOP
        result[Index.TOP.ordinal()] = counts[TOP_LO];

        //DDL
        CV = new double[]{2.337699E-18, -3.546667E-13, 1.935331E-08, 1.072171E-03, -4.639768E01};
        result[Index.DDL.ordinal()] = EV.polyval(CV,counts[DDL]);

        //DDM
        CV = new double[]{2.337699E-18, -3.546667E-13, 1.935331E-08, 1.072171E-03, -4.639768E01};
        result[Index.DDM.ordinal()] = EV.polyval(CV,counts[DDM]);


        //DDN
        CV = new double[]{2.337699E-18, -3.546667E-13, 1.935331E-08, 1.072171E-03, -4.639768E01};
        result[Index.DDN.ordinal()] = EV.polyval(CV,counts[DDN]);


        //DDC
        CV = new double[]{2.337699E-18, -3.546667E-13, 1.935331E-08, 1.072171E-03, -4.639768E01};
        result[Index.DDC.ordinal()] = EV.polyval(CV,counts[DDC]);

        this._currentCVT = result;
        return this._currentCVT;
    }



}
