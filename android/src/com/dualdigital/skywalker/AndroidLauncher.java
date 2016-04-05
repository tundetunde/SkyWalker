package com.dualdigital.skywalker;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.dualdigital.skywalker.SkyWalker;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new SkyWalker(), config);
	}

	private void initializeAppodeal(){
		String appKey = "fee50c333ff3825fd6ad6d38cff78154de3025546d47a84f";
	}

	@Override
	protected void onPause() {
		super.onPause();
		//Appodeal.onResume(this, Appodeal.BANNER);
	}
}
