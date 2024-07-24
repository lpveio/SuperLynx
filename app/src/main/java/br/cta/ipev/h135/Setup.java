package br.cta.ipev.h135;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import br.cta.ipev.h135.Alertas;
import br.cta.ipev.commom.acquisition.iAlert;
import br.cta.ipev.commom.screen.BaseSetup;
import br.cta.ipev.commom.screen.Tab;
import br.cta.ipev.h135.telas.Comando_Voo;
import br.cta.ipev.h135.telas.TelaDois;
import br.cta.ipev.h135.telas.TelaTres;

public class Setup extends BaseSetup{

    @Override
    public List<Tab> getScreenTabs(boolean forTablets) {
        List<Tab>screenTabs = new ArrayList<Tab>();
        screenTabs.add(0,new Tab("1b","1", Comando_Voo.class,true,false));
        screenTabs.add(1,new Tab("5","2", TelaDois.class,true,true));
        screenTabs.add(2,new Tab("6","3", TelaTres.class,true,false));
        return (super.getScreenForTablets(screenTabs,forTablets));

    }

    @Override
    public void setAlerts(Context context) {

    }


}
