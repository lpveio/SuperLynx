package br.cta.ipev.h135;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TabHost;

import java.util.List;

import br.cta.ipev.commom.screen.Tab;
import br.cta.isad.IenaPacketReceiver;
import br.cta.isad.UDPConnector;

public class DataViewActivity  extends ActivityGroup{

    public static final String TAG = "DataViewActivity";
    private AppManager missionManager;
    private TabHost tabHost;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);

        this.isTablet = true;
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        missionManager = (AppManager) getApplicationContext();
        createMission();
        createTabs();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        missionManager.stop();
    }

    private void createMission(){

        IenaPacketReceiver ienaPacketReceiver = new IenaPacketReceiver(getBaseContext());
        ienaPacketReceiver.setConverter(new CoefsSAD1());
        missionManager.setUdpConnector(new UDPConnector(1024),ienaPacketReceiver);
        missionManager.start();
    }


    private void createTabs(){
        this.tabHost = (findViewById(android.R.id.tabhost));
        this.tabHost.setup(this.getLocalActivityManager());

        TabHost.TabSpec spec = tabHost.newTabSpec("Config");
        Setup setup = new Setup();
        List<Tab> screenTabs;
        screenTabs = setup.getScreenTabs(isTablet);

        for(Tab screenTab:screenTabs){
            spec = tabHost.newTabSpec(screenTab.Tag);
            spec.setContent(new Intent().setClass(this,screenTab.Class));
            spec.setIndicator(screenTab.Indicator);
            tabHost.addTab(spec);
        }

        for(int i=tabHost.getTabWidget().getTabCount()-1;i>=0;i--){
            tabHost.setCurrentTab(i);
        }



    }
}
