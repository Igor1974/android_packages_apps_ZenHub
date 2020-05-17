/*
 * Copyright (C) 2020 Zenx-OS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zenx.zen.hub.fragments.screenanimation.tabs;

import android.app.Activity;
import android.content.Context;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import androidx.preference.*;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import android.app.WallpaperManager;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.nano.MetricsProto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class AudioPanel extends SettingsPreferenceFragment  implements Preference.OnPreferenceChangeListener {

   private ListPreference mVolumePanelTheme;
    private static final String SYNTHOS_VOLUME_PANEL_THEME = "synthos_volume_panel_theme";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.audio_panel);
        final PreferenceScreen prefScreen = getPreferenceScreen();
        mContext = getActivity();

        ContentResolver resolver = getActivity().getContentResolver();
        final PreferenceScreen prefScreen = getPreferenceScreen();
        Resources resources = getResources();

        // set volume panel theme
        mVolumePanelTheme = (ListPreference) findPreference(SYNTHOS_VOLUME_PANEL_THEME);
        int style = Settings.System.getInt(resolver,
                Settings.System.SYNTHOS_VOLUME_PANEL_THEME, 0);
        mVolumePanelTheme.setValue(String.valueOf(style));
        mVolumePanelTheme.setSummary(mVolumePanelTheme.getEntry());
        mVolumePanelTheme.setOnPreferenceChangeListener(this);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
         if (preference == mVolumePanelTheme) {
            int style = Integer.valueOf((String) newValue);
            int index = mVolumePanelTheme.findIndexOfValue((String) newValue);
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.SYNTHOS_VOLUME_PANEL_THEME, style);
            mVolumePanelTheme.setSummary(mVolumePanelTheme.getEntries()[index]);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.ZENX_SETTINGS;
    }
}